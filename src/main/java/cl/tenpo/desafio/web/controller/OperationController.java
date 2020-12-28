package cl.tenpo.desafio.web.controller;

import cl.tenpo.desafio.services.OperationService;
import cl.tenpo.desafio.web.mapper.OperationMapper;
import cl.tenpo.desafio.web.model.OperationDto;
import cl.tenpo.desafio.web.model.OperationPagedListDto;
import cl.tenpo.desafio.web.model.ResultOperationDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
@RestController
@RequestMapping("/api/v1/operation")
@Slf4j
@CrossOrigin
@Api(tags = "/api/v1/operation")
@AllArgsConstructor
public class OperationController {

    private final OperationService operationService;
    private final OperationMapper operationMapper;
    private static final Integer DEFAULT_PAGE_NUMBER = 0;

    /**
     *
     * @param pageNumber
     * @return OperationPagedList
     */
    @GetMapping("/")
    @ApiOperation(
            httpMethod = "GET",
            value = "Enpoint para obtener el historial de operaciones",
            response = OperationPagedListDto.class,
            nickname = "list"
    )
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo no funcionó correctamente"),
            @ApiResponse(code = 401, message = "Acceso Denegado - Debe estar logueado para acceder a este recurso")
    })//
    public OperationPagedListDto list(@RequestParam(value = "pageNumber", required = false) Integer pageNumber) {

        if (pageNumber == null || pageNumber < 0){
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        return operationService.listOperations(pageNumber);
    }


    /**
     * Endpoint que retorna la suma de los dos valores que recibe como parámetro dentro del objeto
     * OperationDto.
     * @param operationDto
     * @return ResponseEntity
     */
    @PostMapping("/plus")
    @ApiOperation(
            httpMethod = "POST",
            value = "Endpoint para sumar dos valores enteros",
            response = ResponseEntity.class
            )
    @ApiResponses(value = {//
            @ApiResponse(code = 400, message = "Algo no funcionó correctamente"),
            @ApiResponse(code = 401, message = "Acceso Denegado - Debe estar logueado para acceder a este recurso")
    })//
    public ResponseEntity plus(@Valid @RequestBody OperationDto operationDto) {
        int result = this.operationService.plus(operationMapper.operationDtoToOperation(operationDto));
        return new ResponseEntity(ResultOperationDto.builder().result(result).build(), HttpStatus.OK);
    }

}
