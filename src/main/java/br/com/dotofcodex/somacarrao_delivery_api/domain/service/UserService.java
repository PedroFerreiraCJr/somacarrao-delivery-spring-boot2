package br.com.dotofcodex.somacarrao_delivery_api.domain.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.dotofcodex.somacarrao_delivery_api.api.dto.UserDTO;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.UserAlreadyExistsException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.UserNotFoundException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.exception.ValidationException;
import br.com.dotofcodex.somacarrao_delivery_api.domain.model.User;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.RoleRepository;
import br.com.dotofcodex.somacarrao_delivery_api.domain.repository.UserRepository;
import br.com.dotofcodex.somacarrao_delivery_api.util.PasswordValidator;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;

	@Autowired
	private RoleRepository roles;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PasswordValidator passwordValidator;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
	}

	public User create(UserDTO dto) {
		final User result = repository.findByEmail(dto.getEmail()).orElseGet(() -> new User());

		// se usuário já existe, não permite cadastrar novamente
		if (result.getEmail() != null) {
			throw new UserAlreadyExistsException("Usuário já cadastrado");
		}

		// verifica se as senhas fornecidas são iguais
		if (!dto.getPassword().equals(dto.getPasswordConfirmation())) {
			throw new ValidationException("As senhas não conferem");
		}

		if (!passwordValidator.validate(dto.getPassword())) {
			throw new ValidationException("Senha muito fraca");
		}

		// codifica a senha fornecida
		dto.setPassword(passwordEncoder.encode(dto.getPassword()));

		final User newUser = toModel(dto);
		newUser.setRoles(Arrays.asList(roles.findByName("ROLE_USER").get()));

		return repository.save(newUser);
	}

	public User update(Long id, UserDTO dto) {
		final User user = repository.findById(id)
				.orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));

		// altera os valores dos dados anteriores que podem ser alterados
		user.setName(dto.getName());
		user.setTelefone(dto.getTelefone());

		return repository.save(user);
	}

	public List<User> findAll() {
		return this.repository.findAll();
	}

	private User toModel(UserDTO dto) {
		return User.builder().name(dto.getName()).email(dto.getEmail()).password(dto.getPassword())
				.telefone(dto.getTelefone()).enabled(true).build();
	}
}
