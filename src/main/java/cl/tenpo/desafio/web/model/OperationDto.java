package cl.tenpo.desafio.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Data
@AllArgsConstructor
@Builder
public class OperationDto {

    @ApiModelProperty(hidden = true)
    private Long id;

    @NotNull(message = "el campo num1 es obligatorio")
    @Min(value = 0L, message = "el campo num1 debe ser positivo.")
    private Integer num1;

    @NotNull(message = "el campo num2 es obligatorio")
    @Min(value = 0L, message = "el campo num1 debe ser positivo.")
    private Integer num2;

    @ApiModelProperty(hidden = true)
    private Timestamp createdDate;

}
