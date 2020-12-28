package cl.tenpo.desafio.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
@AllArgsConstructor
@Getter
public class CredentialsDto {

    @NotBlank(message = "el campo username es obligatorio!")
    private String username;

    @NotBlank(message = "El campo password es obligatorio!")
    private String password;

}
