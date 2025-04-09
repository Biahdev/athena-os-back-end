package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.employee.EmployeeUserCreateDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserDetailDTO;
import dev.abeatriz.athena_os.entity.Employee;
import dev.abeatriz.athena_os.entity.User;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.entity.enums.UserStatus;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-30T19:54:29-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public Employee toEntity(EmployeeUserCreateDTO employee) {
        if ( employee == null ) {
            return null;
        }

        Employee employee1 = new Employee();

        employee1.setName( employee.name() );
        employee1.setPosition( toEnumPosition( employee.position() ) );
        employee1.setNote( employee.note() );

        return employee1;
    }

    @Override
    public Employee toEntity(EmployeeUserDetailDTO employee) {
        if ( employee == null ) {
            return null;
        }

        Employee employee1 = new Employee();

        employee1.setEmployeeId( employee.employeeId() );
        employee1.setName( employee.name() );
        employee1.setStatus( employee.status() );
        employee1.setPosition( employee.position() );
        employee1.setNote( employee.note() );

        return employee1;
    }

    @Override
    public List<Employee> toEntity(List<EmployeeUserDetailDTO> employee) {
        if ( employee == null ) {
            return null;
        }

        List<Employee> list = new ArrayList<Employee>( employee.size() );
        for ( EmployeeUserDetailDTO employeeUserDetailDTO : employee ) {
            list.add( toEntity( employeeUserDetailDTO ) );
        }

        return list;
    }

    @Override
    public List<EmployeeUserDetailDTO> toDTO(List<Employee> employeeEntity) {
        if ( employeeEntity == null ) {
            return null;
        }

        List<EmployeeUserDetailDTO> list = new ArrayList<EmployeeUserDetailDTO>( employeeEntity.size() );
        for ( Employee employee : employeeEntity ) {
            list.add( toDTO( employee ) );
        }

        return list;
    }

    @Override
    public EmployeeUserDetailDTO toDTO(Employee employeeEntity) {
        if ( employeeEntity == null ) {
            return null;
        }

        Long employeeId = null;
        String name = null;
        EmployeeStatus status = null;
        EmployeePosition position = null;
        String note = null;
        String email = null;
        UserStatus userStatus = null;
        UserRole userRole = null;

        employeeId = employeeEntity.getEmployeeId();
        name = employeeEntity.getName();
        status = employeeEntity.getStatus();
        position = employeeEntity.getPosition();
        note = employeeEntity.getNote();
        email = employeeEntityUserEmail( employeeEntity );
        userStatus = employeeEntityUserStatus( employeeEntity );
        userRole = employeeEntityUserRole( employeeEntity );

        EmployeeUserDetailDTO employeeUserDetailDTO = new EmployeeUserDetailDTO( employeeId, name, status, position, note, email, userStatus, userRole );

        return employeeUserDetailDTO;
    }

    @Override
    public String toString(EmployeeStatus status) {
        if ( status == null ) {
            return null;
        }

        String string;

        switch ( status ) {
            case ATIVO: string = "ATIVO";
            break;
            case INATIVO: string = "INATIVO";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return string;
    }

    @Override
    public EmployeeStatus toEnumStatus(String status) {
        if ( status == null ) {
            return null;
        }

        EmployeeStatus employeeStatus;

        switch ( status ) {
            case "ATIVO": employeeStatus = EmployeeStatus.ATIVO;
            break;
            case "INATIVO": employeeStatus = EmployeeStatus.INATIVO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return employeeStatus;
    }

    @Override
    public String toString(EmployeePosition status) {
        if ( status == null ) {
            return null;
        }

        String string;

        switch ( status ) {
            case PROPRIETARIO: string = "PROPRIETARIO";
            break;
            case VENDEDOR: string = "VENDEDOR";
            break;
            case ASSISTENTE: string = "ASSISTENTE";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return string;
    }

    @Override
    public EmployeePosition toEnumPosition(String status) {
        if ( status == null ) {
            return null;
        }

        EmployeePosition employeePosition;

        switch ( status ) {
            case "PROPRIETARIO": employeePosition = EmployeePosition.PROPRIETARIO;
            break;
            case "VENDEDOR": employeePosition = EmployeePosition.VENDEDOR;
            break;
            case "ASSISTENTE": employeePosition = EmployeePosition.ASSISTENTE;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return employeePosition;
    }

    private String employeeEntityUserEmail(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        User user = employee.getUser();
        if ( user == null ) {
            return null;
        }
        String email = user.getEmail();
        if ( email == null ) {
            return null;
        }
        return email;
    }

    private UserStatus employeeEntityUserStatus(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        User user = employee.getUser();
        if ( user == null ) {
            return null;
        }
        UserStatus status = user.getStatus();
        if ( status == null ) {
            return null;
        }
        return status;
    }

    private UserRole employeeEntityUserRole(Employee employee) {
        if ( employee == null ) {
            return null;
        }
        User user = employee.getUser();
        if ( user == null ) {
            return null;
        }
        UserRole role = user.getRole();
        if ( role == null ) {
            return null;
        }
        return role;
    }
}
