package cl.tenpo.desafio.services;

import cl.tenpo.desafio.domain.User;
import cl.tenpo.desafio.exception.BadCredentialsException;
import cl.tenpo.desafio.exception.DuplicateUsernameException;
import cl.tenpo.desafio.exception.UserNotFoundException;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
public interface UserService extends UserDetailsService {

    /**
     * Crea un nuevo usuario en el sistema.
     * Si el username del usuario ya existe, levanta una excepción de tipo
     * DuplicateUsernameException.
     *
     * @param user - Usuario a crear.
     * @return User - Usuario creado.
     * @throws DuplicateUsernameException
     */
    User saveNewUser(User user) throws DuplicateUsernameException;

    /**
     * Autentica un usuario en el sistema.
     * @param username
     * @param password
     * @throws BadCredentialsException, UserNotFoundException
     * @return String - Token del usuario autenticado.
     */
    String authenticateUser(String username, String password) throws BadCredentialsException, UserNotFoundException;

    /**
     * Valida que el usuario tenga un token válido.
     * @param username
     * @return Boolean
     */
    Boolean isTokenValid(String username);

    /**
     * Desloguea a un usuario del sistema.
     * @param username
     */
    void logOut(String username);
}
