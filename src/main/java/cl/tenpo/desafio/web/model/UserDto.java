package cl.tenpo.desafio.web.model;

import lombok.*;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
public class UserDto {

    private Long id;
    private String username;

}
