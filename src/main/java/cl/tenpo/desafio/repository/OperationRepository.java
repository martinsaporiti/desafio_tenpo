package cl.tenpo.desafio.repository;

import cl.tenpo.desafio.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by martin.saporiti
 * on 25/12/2020
 * Github: https://github.com/martinsaporiti
 */
public interface OperationRepository extends JpaRepository<Operation, Long> {

}
