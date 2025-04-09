package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.user.UserCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.user.UserDetailDTO;
import dev.abeatriz.athena_os.entity.User;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(target = "userId", ignore = true)
    User toEntity(UserCreateUpdateDTO user);

    User toEntity(UserDetailDTO user);

    UserDetailDTO toDTO(User user);

    List<UserDetailDTO> toDTO(List<User> user);

    String toString(UserRole userRole);
}
