package cl.tenpo.desafio.services;

import cl.tenpo.desafio.domain.Operation;
import cl.tenpo.desafio.repository.OperationRepository;
import cl.tenpo.desafio.web.mapper.OperationMapper;
import cl.tenpo.desafio.web.model.OperationPagedListDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

/**
 * Este servicio implementa la lógica de operaciones.
 *
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository addOperationRepository;
    private final OperationMapper operationMapper;
    private final Integer pageSize;

    public OperationServiceImpl(OperationRepository addOperationRepository,
                                OperationMapper operationMapper,
                                @Value("${page.size}")Integer pageSize) {
        this.addOperationRepository = addOperationRepository;
        this.operationMapper = operationMapper;
        this.pageSize = pageSize;
    }

    @Override
    public OperationPagedListDto listOperations(int pageNumber) {

        Pageable pageable = PageRequest.of(pageNumber,
                Integer.valueOf(pageSize),
                Sort.by("createdDate").descending());

        Page<Operation> addOperationPage =
                addOperationRepository.findAll(pageable);

        return new OperationPagedListDto(addOperationPage
                .stream()
                .map(operationMapper::operationToOperationDto)
                .collect(Collectors.toList()), PageRequest.of(
                addOperationPage.getPageable().getPageNumber(),
                addOperationPage.getPageable().getPageSize()),
                addOperationPage.getTotalElements());
    }


    @Transactional
    @Override
    public int plus(Operation operation) {
        addOperationRepository.save(operation);
        return operation.getNum1() + operation.getNum2();
    }

}
