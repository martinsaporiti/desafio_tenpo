package cl.tenpo.desafio.web.controller;

import cl.tenpo.desafio.domain.User;
import cl.tenpo.desafio.exception.BadCredentialsException;
import cl.tenpo.desafio.exception.DuplicateUsernameException;
import cl.tenpo.desafio.exception.UserNotFoundException;
import cl.tenpo.desafio.security.jwt.JwtTokenUtil;
import cl.tenpo.desafio.services.UserService;
import cl.tenpo.desafio.web.mapper.UserMapper;
import cl.tenpo.desafio.web.model.CredentialsDto;
import cl.tenpo.desafio.web.model.JwtResponseDto;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Este controller define los endpoints para realizar operaciones de usuario.
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@AllArgsConstructor
@CrossOrigin
@Api(tags = "/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper mapper;
    private JwtTokenUtil jwtTokenUtil;


    /**
     * Endpoint que permite registrar a un nuevo usuario.
     * @param credentials
     * @return ResponseEntity
     * @throws DuplicateUsernameException
     */
    @PostMapping("/signup")
    @ApiOperation(
            httpMethod = "POST",
            value = "Endpoint para registrarse en el sistema",
            response = ResponseEntity.class
            )
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo no funcionó correctamente"), //
            @ApiResponse(code = 422, message = "Nombre de Usuario ya existe"),
            @ApiResponse(code = 201, message = "Usuario creado exitosamente")})
    public ResponseEntity signUp(@Valid @RequestBody CredentialsDto credentials) throws DuplicateUsernameException {
        User savedUser = this.userService.saveNewUser(
                User.builder()
                        .username(credentials.getUsername())
                        .password(credentials.getPassword())
                        .build());

        return new ResponseEntity(mapper.userToUserDto(savedUser), HttpStatus.CREATED);
    }

    /**
     * Endpoint que autentica a un usuario del sistema.
     * @param credentials
     * @return ResponseEntity
     */
    @PostMapping("/login")
    @ApiOperation(
            httpMethod = "POST",
            value = "Endpoint para autenticarse en el sistema",
            response = ResponseEntity.class
    )
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo no funcionó correctamente"), //
            @ApiResponse(code = 401, message = "Nombre de Usuario o Password Incorrecto")})
    public ResponseEntity login(@ApiParam("credentials") @Valid @RequestBody CredentialsDto credentials)
            throws BadCredentialsException, UserNotFoundException {
        // Se valida username y password. Se obtiene el token.
        final String token = userService.authenticateUser(credentials.getUsername(),
                credentials.getPassword());
        return ResponseEntity.ok(JwtResponseDto.builder()
                .username(credentials.getUsername())
                .token(token).build());
    }


    @GetMapping("/logout")
    @ApiOperation(
            httpMethod = "GET",
            value = "Endpoint para desloguearse de la aplicación.",
            response = ResponseEntity.class
    )
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo no funcionó correctamente"),
            @ApiResponse(code = 401, message = "Acceso Denegado")})//
    public ResponseEntity logout() {
        SecurityContext context = SecurityContextHolder.getContext();
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        userService.logOut(userDetails.getUsername());
        return new ResponseEntity(HttpStatus.OK);
    }

}
