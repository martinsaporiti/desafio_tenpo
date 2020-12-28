package cl.tenpo.desafio.repository;

import cl.tenpo.desafio.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     *
     * @param username
     * @return List<User>
     */
    User findByUsername(String username);

    /**
     *
     * @param username
     * @return boolean
     */
    boolean existsByUsername(String username);
}
