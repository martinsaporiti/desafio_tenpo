package cl.tenpo.desafio.services;

import cl.tenpo.desafio.domain.User;
import cl.tenpo.desafio.exception.BadCredentialsException;
import cl.tenpo.desafio.exception.DuplicateUsernameException;
import cl.tenpo.desafio.exception.UserNotFoundException;
import cl.tenpo.desafio.repository.UserRepository;
import cl.tenpo.desafio.security.jwt.JwtTokenUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
@AllArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private JwtTokenUtil jwtTokenUtil;

    private final static String ERROR_DUPLICATE_USERNAME = "El nombre de usuario está siendo usado. Debe ingresar otro nombre de usuario";
    private final static String ERROR_USER_NOT_FOUND = "El usuario %s no existe en el sistema";
    private final static String ERROR_BAD_CREDENTIALS = "Error en las credenciales ingresadas";
    @Override
    @Transactional
    public User saveNewUser(User user) throws DuplicateUsernameException {
        log.debug("Saving User:" + user);

        if(!userRepository.existsByUsername(user.getUsername())){
            PasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            user.setValidToken(true);
            return this.userRepository.save(user);
        } else {
            throw new DuplicateUsernameException(ERROR_DUPLICATE_USERNAME);
        }
    }

    @Override
    @Transactional
    public String authenticateUser(String username, String password) throws BadCredentialsException, UserNotFoundException {
        try {
            User user = this.userRepository.findByUsername(username);
            if(user != null) {
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
                user.setValidToken(true);
                userRepository.save(user);

                // Se obtiene el UserDetail para generar el token.
                UserDetails userDetails = loadUserByUsername(username);

                // Se genera el token.
                final String token = jwtTokenUtil.generateToken(userDetails);

                return token;
            } else {
                throw new UserNotFoundException(String.format(ERROR_USER_NOT_FOUND, username));
            }

        } catch (AuthenticationException ex) {
            throw new BadCredentialsException(ERROR_BAD_CREDENTIALS);
        }
    }

    @Override
    public Boolean isTokenValid(String username) {
        User user = this.userRepository.findByUsername(username);
        return user.getValidToken();
    }

    @Override
    public void logOut(String username) {
       User user = this.userRepository.findByUsername(username);
       user.setValidToken(false);
       userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        cl.tenpo.desafio.domain.User dbUSer = this.userRepository.findByUsername(username);
        if(dbUSer == null){
            String message = String.format(ERROR_USER_NOT_FOUND, username);
            throw new UsernameNotFoundException(message);
        }
        return new org.springframework.security.core.userdetails.User(dbUSer.getUsername(),
                dbUSer.getPassword(),
                Collections.emptyList());
    }
}
