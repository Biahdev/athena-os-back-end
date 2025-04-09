package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.user.UserCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.user.UserDetailDTO;
import dev.abeatriz.athena_os.entity.User;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.entity.enums.UserStatus;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-30T18:34:04-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toEntity(UserCreateUpdateDTO user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setEmail( user.email() );
        if ( user.role() != null ) {
            user1.setRole( Enum.valueOf( UserRole.class, user.role() ) );
        }

        return user1;
    }

    @Override
    public User toEntity(UserDetailDTO user) {
        if ( user == null ) {
            return null;
        }

        User user1 = new User();

        user1.setEmail( user.email() );
        user1.setStatus( user.status() );
        user1.setRole( user.role() );

        return user1;
    }

    @Override
    public UserDetailDTO toDTO(User user) {
        if ( user == null ) {
            return null;
        }

        String email = null;
        UserStatus status = null;
        UserRole role = null;

        email = user.getEmail();
        status = user.getStatus();
        role = user.getRole();

        UserDetailDTO userDetailDTO = new UserDetailDTO( email, status, role );

        return userDetailDTO;
    }

    @Override
    public List<UserDetailDTO> toDTO(List<User> user) {
        if ( user == null ) {
            return null;
        }

        List<UserDetailDTO> list = new ArrayList<UserDetailDTO>( user.size() );
        for ( User user1 : user ) {
            list.add( toDTO( user1 ) );
        }

        return list;
    }

    @Override
    public String toString(UserRole userRole) {
        if ( userRole == null ) {
            return null;
        }

        String string;

        switch ( userRole ) {
            case ADMIN: string = "ADMIN";
            break;
            case BASIC: string = "BASIC";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + userRole );
        }

        return string;
    }
}
