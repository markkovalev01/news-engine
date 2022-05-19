package app.model.mapper;

import org.springframework.stereotype.Component;

import app.model.dto.UserDTO;
import app.model.entities.UserEntity;
import app.model.mapper.Mapper;

@Mapper(entity = UserEntity.class, dto = UserDTO.class)
public class UserMapper extends AbstractMapper<UserEntity, UserDTO>{
}
