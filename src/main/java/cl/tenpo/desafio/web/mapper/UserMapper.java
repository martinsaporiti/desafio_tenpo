package cl.tenpo.desafio.web.mapper;

import cl.tenpo.desafio.domain.User;
import cl.tenpo.desafio.web.model.UserDto;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
public interface UserMapper {

    /**
     *
     * @param dto
     * @return UserDto
     */
    User userDtoToUser(UserDto dto);

    /**
     *
     * @param user
     * @return UserDto
     */
    UserDto userToUserDto(User user);
}
