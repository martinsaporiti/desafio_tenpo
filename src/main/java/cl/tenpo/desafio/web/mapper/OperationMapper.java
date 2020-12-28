package cl.tenpo.desafio.web.mapper;

import cl.tenpo.desafio.domain.Operation;
import cl.tenpo.desafio.web.model.OperationDto;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */

public interface OperationMapper {

    public OperationDto operationToOperationDto(Operation operation);
    public Operation operationDtoToOperation(OperationDto dto);

}
