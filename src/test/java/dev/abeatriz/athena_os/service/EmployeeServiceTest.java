package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.employee.EmployeeUserCreateDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserDetailDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserUpdateDTO;
import dev.abeatriz.athena_os.dto.user.UserCreateUpdateDTO;
import dev.abeatriz.athena_os.entity.Employee;
import dev.abeatriz.athena_os.entity.User;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.entity.enums.UserStatus;
import dev.abeatriz.athena_os.mapper.EmployeeMapper;
import dev.abeatriz.athena_os.mapper.UserMapper;
import dev.abeatriz.athena_os.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private UserService userService;

    @Mock
    private EmployeeMapper employeeMapperMock;
    private EmployeeMapper employeeMapper = EmployeeMapper.INSTANCE;
    private UserMapper userMapper = UserMapper.INSTANCE;

    @InjectMocks
    private EmployeeService employeeService;

    private User userEntity;

    private Employee employeeEntity;

    private EmployeeUserCreateDTO employeeUserCreateDTO;

    private EmployeeUserDetailDTO employeeUserDetailDTO;


    private User userEntity2;

    private Employee employeeEntity2;

    private EmployeeUserCreateDTO employeeUserCreateDTO2;

    private EmployeeUserDetailDTO employeeUserDetailDTO2;


    @BeforeEach
    void setUp() {
        employeeUserCreateDTO = new EmployeeUserCreateDTO("Ana", "ana@email.com.br", userMapper.toString(UserRole.ADMIN), employeeMapper.toString(EmployeePosition.VENDEDOR), "oi oi oi");
        userEntity = new User();
        userEntity.setEmail(employeeUserCreateDTO.email());
        userEntity.setPassword("encodedPassword");
        userEntity.setRole(UserRole.valueOf(employeeUserCreateDTO.userRole()));
        userEntity.setStatus(UserStatus.ATIVO);
        userEntity.setUserId(1L);

        employeeEntity = new Employee();
        employeeEntity.setName(employeeUserCreateDTO.name());
        employeeEntity.setPosition(EmployeePosition.valueOf(employeeUserCreateDTO.position()));
        employeeEntity.setNote(employeeUserCreateDTO.note());
        employeeEntity.setEmployeeId(1L);
        employeeEntity.setUser(userEntity);
        userEntity.setEmployee(employeeEntity);

        employeeUserDetailDTO = new EmployeeUserDetailDTO(
                employeeEntity.getEmployeeId(),
                employeeEntity.getName(),
                EmployeeStatus.ATIVO,
                EmployeePosition.valueOf(employeeUserCreateDTO.position()),
                employeeEntity.getNote(),
                userEntity.getEmail(),
                userEntity.getStatus(),
                userEntity.getRole()
        );


        employeeUserCreateDTO2 = new EmployeeUserCreateDTO("Liriel", "liriel@email.com.br", userMapper.toString(UserRole.ADMIN), employeeMapper.toString(EmployeePosition.VENDEDOR), "oi oi oi");
        userEntity2 = new User();
        userEntity2.setEmail(employeeUserCreateDTO2.email());
        userEntity2.setPassword("encodedPassword");
        userEntity2.setRole(UserRole.valueOf(employeeUserCreateDTO2.userRole()));
        userEntity2.setStatus(UserStatus.ATIVO);
        userEntity2.setUserId(1L);

        employeeEntity2 = new Employee();
        employeeEntity2.setName(employeeUserCreateDTO2.name());
        employeeEntity2.setPosition(EmployeePosition.valueOf(employeeUserCreateDTO2.position()));
        employeeEntity2.setNote(employeeUserCreateDTO2.note());
        employeeEntity2.setEmployeeId(1L);
        employeeEntity2.setUser(userEntity2);
        userEntity2.setEmployee(employeeEntity2);

        employeeUserDetailDTO2 = new EmployeeUserDetailDTO(
                employeeEntity2.getEmployeeId(),
                employeeEntity2.getName(),
                EmployeeStatus.ATIVO,
                EmployeePosition.valueOf(employeeUserCreateDTO2.position()),
                employeeEntity2.getNote(),
                userEntity2.getEmail(),
                userEntity2.getStatus(),
                userEntity2.getRole()
        );
    }

    @AfterEach
    void tearDown() {
        reset(employeeRepository, employeeMapperMock);
    }

    @Test
    @DisplayName("Create Success")
    void givenEmployee_whenCreate_thenReturnDetailEmployee() {
        //Given
        given(userService.create(any(UserCreateUpdateDTO.class))).willReturn(userEntity);
        given(employeeRepository.save(any(Employee.class))).willReturn(employeeEntity);
        given(employeeMapperMock.toDTO(employeeEntity)).willReturn(employeeUserDetailDTO);

        //When
        var newEmployee = employeeService.create(employeeUserCreateDTO);

        //Then
        assertAll("Verificando o resultado do create()",
                () -> assertNotNull(newEmployee),
                () -> assertEquals(newEmployee, employeeUserDetailDTO),
                () -> assertEquals(1L, newEmployee.employeeId()),
                () -> assertEquals(newEmployee.name(), employeeUserDetailDTO.name()),
                () -> assertEquals(newEmployee.status(), employeeUserDetailDTO.status()),
                () -> assertEquals(newEmployee.position(), employeeUserDetailDTO.position()),
                () -> assertEquals(newEmployee.note(), employeeUserDetailDTO.note()),
                () -> assertEquals(newEmployee.email(), employeeUserDetailDTO.email()),
                () -> assertEquals(newEmployee.userStatus(), employeeUserDetailDTO.userStatus()),
                () -> assertEquals(newEmployee.userRole(), employeeUserDetailDTO.userRole())
        );

        assertAll("Verificação das interações com os mocks",
                () -> then(employeeRepository).should(times(1)).save(any(Employee.class)),
                () -> then(userService).should(times(1)).create(any(UserCreateUpdateDTO.class)),
                () -> then(employeeMapperMock).should(times(1)).toDTO(employeeEntity)
        );
    }


    @Test
    @DisplayName("ListById Success")
    void givenEmployeeId_whenListById_thenReturnDetailEmployee() {
        //Given
        given(employeeRepository.findById(1L)).willReturn(Optional.ofNullable(employeeEntity));
        given(employeeMapperMock.toDTO(employeeEntity)).willReturn(employeeUserDetailDTO);

        //When
        var employeeById = employeeService.listById(1L);

        //Then
        assertAll("Verificando o resultado do listById()",
                () -> assertNotNull(employeeById),
                () -> assertEquals(employeeById, employeeUserDetailDTO),
                () -> assertEquals(1L, employeeById.employeeId()),
                () -> assertEquals(employeeById.name(), employeeUserDetailDTO.name()),
                () -> assertEquals(employeeById.status(), employeeUserDetailDTO.status()),
                () -> assertEquals(employeeById.position(), employeeUserDetailDTO.position()),
                () -> assertEquals(employeeById.note(), employeeUserDetailDTO.note()),
                () -> assertEquals(employeeById.email(), employeeUserDetailDTO.email()),
                () -> assertEquals(employeeById.userStatus(), employeeUserDetailDTO.userStatus()),
                () -> assertEquals(employeeById.userRole(), employeeUserDetailDTO.userRole())
        );

        assertAll("Verificação das interações com os mocks",
                () -> then(employeeRepository).should(times(1)).findById(1L),
                () -> then(employeeMapperMock).should(times(1)).toDTO(employeeEntity)
        );
    }

    @Test
    @DisplayName("ListAll Success")
    void givenEmployees_whenListAll_thenReturnDetailEmployeeList() {
        //Given
        var listEmployeeEntity = List.of(employeeEntity, employeeEntity2);
        var listEmployeeDetailDTO = List.of(employeeUserDetailDTO, employeeUserDetailDTO2);

        given(employeeRepository.findAll()).willReturn(listEmployeeEntity);
        given(employeeMapperMock.toDTO(listEmployeeEntity)).willReturn(listEmployeeDetailDTO);

        //When
        var newEmployeeList = employeeService.listAll();

        //Then
        assertAll("Verificando o resultado do listById()",
                () -> assertNotNull(newEmployeeList),
                () -> assertEquals(newEmployeeList.size(), listEmployeeDetailDTO.size()),
                () -> assertEquals(newEmployeeList, listEmployeeDetailDTO),
                () -> assertEquals(newEmployeeList.getFirst().name(), employeeUserDetailDTO.name()),
                () -> assertEquals(newEmployeeList.getFirst().status(), employeeUserDetailDTO.status()),
                () -> assertEquals(newEmployeeList.getFirst().position(), employeeUserDetailDTO.position()),
                () -> assertEquals(newEmployeeList.getFirst().note(), employeeUserDetailDTO.note()),
                () -> assertEquals(newEmployeeList.getFirst().email(), employeeUserDetailDTO.email()),
                () -> assertEquals(newEmployeeList.getFirst().userStatus(), employeeUserDetailDTO.userStatus()),
                () -> assertEquals(newEmployeeList.getFirst().userRole(), employeeUserDetailDTO.userRole()),
                () -> newEmployeeList.forEach(employee -> assertInstanceOf(EmployeeUserDetailDTO.class, employee))
        );

        assertAll("Verificação das interações com os mocks",
                () -> then(employeeRepository).should(times(1)).findAll(),
                () -> then(employeeMapperMock).should(times(1)).toDTO(listEmployeeEntity)
        );
    }

    /*
    @Test
    @DisplayName("Update Success")
    void givenEmployeeId_whenUpdate_thenReturnDetailEmployeeUpdated() {
        //Given
        var employeeUpdateDTO = new EmployeeUserUpdateDTO(employeeUserCreateDTO.name(),employeeUserCreateDTO.email(),employeeUserCreateDTO.userRole(),employeeUserCreateDTO.position(), employeeUserCreateDTO.note());
        given(employeeRepository.findById(1L)).willReturn(Optional.ofNullable(employeeEntity));
        given(employeeMapperMock.toDTO(employeeEntity)).willReturn(employeeUserDetailDTO);

        //When
        var employeeUpdate = employeeService.update(employeeCreateDTO, 1L);

        //Then
        assertNotNull(employeeUpdate);
        assertEquals(employeeUpdate.name(), employeeDetailDTO.name());
        assertEquals(employeeUpdate.status(), employeeDetailDTO.status());
        assertEquals(employeeUpdate.position(), employeeDetailDTO.position());
        assertEquals(employeeUpdate.note(), employeeDetailDTO.note());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(employeeEntity);
    }



    @Test
    @DisplayName("Disable Success")
    void givenEmployeeId_whenDisable_thenReturnDetailEmployeeDisabled() {
        //Given
        employeeEntity.setStatus(EmployeeStatus.INATIVO);
        var employeeDetailDisable = mapper.toDTO(employeeEntity);

        given(repository.findById(1L)).willReturn(Optional.ofNullable(employeeEntity));
        given(mapperMock.toDTO(employeeEntity)).willReturn(employeeDetailDisable);

        //When
        var newEmployeeDisable = service.disable(1L);

        //Then
        assertNotNull(newEmployeeDisable);
        assertEquals(newEmployeeDisable, employeeDetailDisable);
        assertEquals(EmployeeStatus.INATIVO, newEmployeeDisable.status());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(employeeEntity);
    }


    @Test
    @DisplayName("Delete Success")
    void givenEmployeeId_whenDelete_thenReturnDetailEmployeeDeleted() {
        //Given
        given(repository.findById(1L)).willReturn(Optional.ofNullable(employeeEntity));
        willDoNothing().given(repository).delete(employeeEntity);

        //When
        service.delete(1L);

        //Then
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(employeeEntity);
    }
    */

}
