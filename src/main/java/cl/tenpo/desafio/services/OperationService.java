package cl.tenpo.desafio.services;

import cl.tenpo.desafio.domain.Operation;
import cl.tenpo.desafio.web.model.OperationPagedListDto;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */

public interface OperationService {


    /**
     * Retorna un listado paginado de operaciones.
     * @param pageNumber
     * @return OperationPagedListDto
     */
    public OperationPagedListDto listOperations(int pageNumber);

    /**
     * Retorna la suma de los números recibidos dentro del objeto de tipo
     * Operation.
     * @param operation - Objecto con los valores a sumar.
     * @return int - Retorna la suma de los valores de Operation.
     */
    public int plus(Operation operation);


}
