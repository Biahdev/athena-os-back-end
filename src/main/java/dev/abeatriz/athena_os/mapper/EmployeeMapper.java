package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.employee.EmployeeUserDetailDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserCreateDTO;
import dev.abeatriz.athena_os.entity.Employee;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

    @Mapping(target = "employeeId", ignore = true)
    Employee toEntity(EmployeeUserCreateDTO employee);

    Employee toEntity(EmployeeUserDetailDTO employee);

    List<Employee> toEntity(List<EmployeeUserDetailDTO> employee);

    List<EmployeeUserDetailDTO> toDTO(List<Employee> employeeEntity);

    @Mapping(source = "employeeId", target = "employeeId")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "position", target = "position")
    @Mapping(source = "note", target = "note")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.status", target = "userStatus")
    @Mapping(source = "user.role", target = "userRole")
    EmployeeUserDetailDTO toDTO(Employee employeeEntity);

    String toString(EmployeeStatus status);

    EmployeeStatus toEnumStatus(String status);

    String toString(EmployeePosition status);

    EmployeePosition toEnumPosition(String status);

}
