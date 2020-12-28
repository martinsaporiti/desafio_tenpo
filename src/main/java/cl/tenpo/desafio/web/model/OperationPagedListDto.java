package cl.tenpo.desafio.web.model;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
public class OperationPagedListDto extends PageImpl<OperationDto> {

    public OperationPagedListDto(List<OperationDto> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

}
