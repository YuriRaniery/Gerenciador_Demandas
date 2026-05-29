package com.feras.Gerenciador_Demandas.service;

import com.feras.Gerenciador_Demandas.exception.UserException;
import com.feras.Gerenciador_Demandas.model.Users;
import com.feras.Gerenciador_Demandas.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public Users listarUsers(Users user) {
        user.getName();
        user.getEmail();
        return user;
    }
    // Spring chama esse método automaticamente no login
    @Override
    public UserDetails loadUserByUsername(String email) throws UserException {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
    }

    // Cadastro de novo usuário
    public Users cadastrar(Users usuario) {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
        return userRepository.save(usuario);
    }

    // Login de usuario
    public Users login(Users login) {
        Users loginUser = userRepository.findByEmail(login.getEmail())
               .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "E-mail ou senha inválidos"));

        if(!passwordEncoder.matches(login.getPassword(), loginUser.getPassword())) {
            throw new UserException(HttpStatus.UNAUTHORIZED, "E-mail ou senha inválidos");
        }

        return loginUser;

    }

    public Users atualizar(String email, Users user) {
        Users atualizarUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        atualizarUser.setName(user.getName());
        atualizarUser.setEmail(user.getEmail());

        return userRepository.save(atualizarUser);
    }

    public Users deletar(String email) {
        Users deletar = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        userRepository.delete(deletar);

        return deletar;
    }

    public Users alterarSenha(String email, Users usuario) {
        Users alterar = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        alterar.setPassword(passwordEncoder.encode(usuario.getPassword()));

        return userRepository.save(alterar);
    }
}