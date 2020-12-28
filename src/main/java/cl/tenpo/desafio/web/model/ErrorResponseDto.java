package cl.tenpo.desafio.web.model;

import lombok.*;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ErrorResponseDto {

    private int status;
    private String message;
    private long timeStamp;

}
