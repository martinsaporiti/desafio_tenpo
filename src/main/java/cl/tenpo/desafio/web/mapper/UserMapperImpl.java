package cl.tenpo.desafio.web.mapper;

import cl.tenpo.desafio.domain.User;
import cl.tenpo.desafio.web.model.UserDto;
import org.springframework.stereotype.Component;

/**
 * Created by martin.saporiti
 * on 24/12/2020
 * Github: https://github.com/martinsaporiti
 */
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User userDtoToUser(UserDto dto) {
        return null;
    }

    @Override
    public UserDto userToUserDto(User user) {
        return UserDto.builder().id(user.getId()).username(user.getUsername()).build();
    }
}
