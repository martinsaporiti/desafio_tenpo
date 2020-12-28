package cl.tenpo.desafio.web.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by martin.saporiti
 * on 26/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Data
@AllArgsConstructor
@Builder
public class ResultOperationDto {
    private int result;
}
