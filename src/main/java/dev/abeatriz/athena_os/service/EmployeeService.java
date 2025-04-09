package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.employee.EmployeeUserDetailDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserCreateDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserUpdateDTO;
import dev.abeatriz.athena_os.dto.user.UserCreateUpdateDTO;
import dev.abeatriz.athena_os.entity.Employee;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import dev.abeatriz.athena_os.mapper.EmployeeMapper;
import dev.abeatriz.athena_os.mapper.UserMapper;
import dev.abeatriz.athena_os.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public EmployeeUserDetailDTO create(EmployeeUserCreateDTO json) {
        var userCreateUpdateDTO = new UserCreateUpdateDTO(json.email(), json.userRole());
        var user = userService.create(userCreateUpdateDTO);

        var employee = new Employee();
        employee.setName(json.name());
        employee.setPosition(EmployeePosition.valueOf(json.position()));
        employee.setStatus(EmployeeStatus.ATIVO);
        employee.setNote(json.note());
        employee.setUser(user);

        var newEmployee = employeeRepository.save(employee);

        return  employeeMapper.toDTO(newEmployee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeUserDetailDTO> listAll() {
        var allEmployeeEntity = employeeRepository.findAll();
        return employeeMapper.toDTO(allEmployeeEntity);
    }

    @Transactional(readOnly = true)
    public EmployeeUserDetailDTO listById(Long id) {
        var employeeEntity = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return employeeMapper.toDTO(employeeEntity);
    }

    @Transactional()
    public EmployeeUserDetailDTO update(EmployeeUserUpdateDTO updateDTO, Long id) {
        var employeeEntity = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        employeeEntity.update(updateDTO);
        return employeeMapper.toDTO(employeeEntity);
    }

    @Transactional()
    public EmployeeUserDetailDTO disable(Long id) {
        var employeeEntity = employeeRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        employeeEntity.disable();
        return employeeMapper.toDTO(employeeEntity);
    }

    @Transactional(readOnly = true)
    public List<String> listEmployeeStatus() {
        return Arrays.stream(EmployeeStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<String> listPositions() {
        return Arrays.stream(EmployeePosition.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
