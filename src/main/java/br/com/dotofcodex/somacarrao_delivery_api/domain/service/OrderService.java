package br.com.dotofcodex.somacarrao_delivery_api.domain.service;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.somacarrao_delivery_api.api.dto.OrderStatusDTO;
import br.com.dotofcodex.somacarrao_delivery_api.api.infra.JwtTokenUtil;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.OrderIllegalStateException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.OrderNotFoundException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.ValidationException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Flavor;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Ingredient;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Order;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.OrderStatus;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.OrderStatusHistory;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Pasta;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Sauce;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.Seasoning;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.User;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.OrderRepository;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.UserRepository;

@Service
public class OrderService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private OrderRepository repository;

	@Autowired
	private PastaService pastaService;

	@Autowired
	private FlavorService flavorService;

	@Autowired
	private SauceService sauceService;

	@Autowired
	private IngredientService ingredientService;

	@Autowired
	private SeasoningService seasoningService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	public List<Order> findAll() {
		return repository.findAll();
	}

	public List<Order> findAllByCreatedAt(OffsetDateTime date) {
		return repository.findAllByCreatedAtAfter(date);
	}

	public List<Order> findAllByCreatedAtToday() {
		return repository.findAllByCreatedAtAfter(OffsetDateTime.now().withHour(0).withMinute(0).withSecond(5));
	}

	public Order create(Order order) {
		// obtém informações sobre o usuário do pedido
		final String email = jwtTokenUtil.getUsernameFromToken(order.getClient().getJwtToken());
		final User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ValidationException("Usuário inválido"));

		// busca informações sobre os itens do pedido
		final Optional<Pasta> pastaSelected = pastaService.findById(order.getPasta().getId());
		final Optional<Flavor> flavorSelected = flavorService.findById(order.getFlavor().getId());
		final List<Sauce> sauces = sauceService
				.findAllById(order.getSauces().stream().map((s) -> s.getId()).collect(Collectors.toList()));
		final List<Ingredient> ingredients = ingredientService
				.findAllById(order.getIngredients().stream().map((i) -> i.getId()).collect(Collectors.toList()));
		final List<Seasoning> seasonings = seasoningService
				.findAllById(order.getSeasonings().stream().map((s) -> s.getId()).collect(Collectors.toList()));

		// valida dados do pedido
		final Pasta pasta = pastaSelected.orElseThrow(() -> new ValidationException("Pasta Selecionada Inválida"));
		final Flavor flavor = flavorSelected.orElseThrow(() -> new ValidationException("Sabor Selecionado Inválida"));

		// calcula o total do prato
		double total = flavor.getPrice();
		final double saucePrice = sauces.stream().max(Comparator.comparingDouble((sauce) -> sauce.getPrice())).get()
				.getPrice();
		total += saucePrice;

		final double totalIngredientPrice = ingredients.stream()
				.collect(Collectors.summingDouble((ingredient) -> ingredient.getPrice()));
		total += totalIngredientPrice;

		final double totalSeasoningPrice = seasonings.stream()
				.collect(Collectors.summingDouble((seasoning) -> seasoning.getPrice()));
		total += totalSeasoningPrice;

		final OffsetDateTime creationDate = OffsetDateTime.now();
		final Order pedido = Order.builder().status(OrderStatus.NEW).createdAt(creationDate).client(user).pasta(pasta)
				.flavor(flavor).sauces(sauces).ingredients(ingredients).seasonings(seasonings).total(total).build();
		pedido.setStatusHistory(Arrays.asList(
				OrderStatusHistory.builder().status(OrderStatus.NEW).alterDate(creationDate).order(pedido).build()));
		return repository.save(pedido);
	}

	public String updateOrderStatus(String jwt, Long id, OrderStatusDTO dto) {
		final Order order = repository.findById(id)
				.orElseThrow(() -> new OrderNotFoundException("Pedido não encontrado"));

		// obtém informações sobre o usuário do pedido
		final String email = jwtTokenUtil.getUsernameFromToken(jwt);
		final User user = userRepository.findByEmail(email)
				.orElseThrow(() -> new ValidationException("Usuário inválido"));

		if (!order.getClient().getEmail().equals(user.getEmail())) {
			throw new ValidationException("Usuario do pedido é diferente do usuário informado");
		}
		
		// processa o status do pedido
		// Novo para Confirmado
		if (order.getStatus().equals(OrderStatus.NEW) && !dto.getStatus().equals(OrderStatus.CONFIRMED)) {
			throw new OrderIllegalStateException("O status informado é ilegal para o pedido no status 'NOVO'");
		} else if (order.getStatus().equals(OrderStatus.NEW) && dto.getStatus().equals(OrderStatus.CONFIRMED)) {
			order.setStatus(OrderStatus.CONFIRMED);
			order.getStatusHistory().add(OrderStatusHistory.builder().status(OrderStatus.CONFIRMED)
					.alterDate(OffsetDateTime.now()).order(order).build());
			repository.save(order);
			return "Status do pedido alterado com sucesso. Novo -> Confirmado.";
		}

		// Confirmado para Fazendo
		if (order.getStatus().equals(OrderStatus.CONFIRMED) && !dto.getStatus().equals(OrderStatus.MAKING)
				&& !dto.getStatus().equals(OrderStatus.CANCELLED)) {
			throw new OrderIllegalStateException("O status informado é ilegal para o pedido no status 'CONFIRMADO'");
		} else if (order.getStatus().equals(OrderStatus.CONFIRMED)
				&& (dto.getStatus().equals(OrderStatus.MAKING) || dto.getStatus().equals(OrderStatus.CANCELLED))) {
			if (dto.getStatus().equals(OrderStatus.MAKING)) {
				order.setStatus(OrderStatus.MAKING);
				order.getStatusHistory().add(OrderStatusHistory.builder().status(OrderStatus.MAKING)
						.alterDate(OffsetDateTime.now()).order(order).build());
				repository.save(order);
				return "Status do pedido alterado com sucesso. Confirmado -> Fazendo.";
			} else if (dto.getStatus().equals(OrderStatus.CANCELLED)) {
				order.setStatus(OrderStatus.CANCELLED);
				order.getStatusHistory().add(OrderStatusHistory.builder().status(OrderStatus.CANCELLED)
						.alterDate(OffsetDateTime.now()).order(order).build());
				repository.save(order);
				return "Status do pedido alterado com sucesso. Confirmado -> Cancelado.";
			}
		}

		// Fazendo para A caminho
		if (order.getStatus().equals(OrderStatus.MAKING) && !dto.getStatus().equals(OrderStatus.DELIVERING)
				&& !dto.getStatus().equals(OrderStatus.CANCELLED)) {
			throw new OrderIllegalStateException("O status informado é ilegal para o pedido no status 'FAZENDO'");
		} else if (order.getStatus().equals(OrderStatus.MAKING)
				&& (dto.getStatus().equals(OrderStatus.DELIVERING) || dto.getStatus().equals(OrderStatus.CANCELLED))) {
			if (dto.getStatus().equals(OrderStatus.DELIVERING)) {
				order.setStatus(OrderStatus.DELIVERING);
				order.getStatusHistory().add(OrderStatusHistory.builder().status(OrderStatus.DELIVERING)
						.alterDate(OffsetDateTime.now()).order(order).build());
				repository.save(order);
				return "Status do pedido alterado com sucesso. Fazendo -> A Caminho.";
			} else if (dto.getStatus().equals(OrderStatus.CANCELLED)) {
				order.setStatus(OrderStatus.CANCELLED);
				order.getStatusHistory().add(OrderStatusHistory.builder().status(OrderStatus.CANCELLED)
						.alterDate(OffsetDateTime.now()).order(order).build());
				repository.save(order);
				return "Status do pedido alterado com sucesso. Fazendo -> Cancelado.";
			}
		}

		// A caminho para Entregue
		if (order.getStatus().equals(OrderStatus.DELIVERING) && !dto.getStatus().equals(OrderStatus.DELIVERED)) {
			throw new OrderIllegalStateException("O status informado é ilegal para o pedido no status 'A Caminho'");
		} else if (order.getStatus().equals(OrderStatus.DELIVERING) && dto.getStatus().equals(OrderStatus.DELIVERED)) {
			order.setStatus(OrderStatus.DELIVERED);
			order.getStatusHistory().add(OrderStatusHistory.builder().status(OrderStatus.DELIVERED)
					.alterDate(OffsetDateTime.now()).order(order).build());
			repository.save(order);
			return "Status do pedido alterado com sucesso. A Caminho -> Entregue.";
		}

		// Verificação de Entregue
		if (order.getStatus().equals(OrderStatus.DELIVERED)) {
			throw new OrderIllegalStateException("O status informado é ilegal para o pedido no status 'Entregue'");
		}

		// Não deveria chegar até essa linha de código em nenhum caso
		throw new OrderIllegalStateException("Pedido em status desconhecido");
	}
}
