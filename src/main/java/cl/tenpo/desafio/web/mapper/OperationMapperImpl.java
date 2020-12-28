package cl.tenpo.desafio.web.mapper;

import cl.tenpo.desafio.domain.Operation;
import cl.tenpo.desafio.web.model.OperationDto;
import org.springframework.stereotype.Component;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Component
public class OperationMapperImpl implements OperationMapper{

    @Override
    public OperationDto operationToOperationDto(Operation operation) {
        return OperationDto.builder()
                .id(operation.getId())
                .num1(operation.getNum1())
                .num2(operation.getNum2())
                .createdDate(operation.getCreatedDate())
                .build();
    }

    @Override
    public Operation operationDtoToOperation(OperationDto dto) {
        return Operation.builder()
                .num1(dto.getNum1())
                .num2(dto.getNum2())
                .build();
    }
}
