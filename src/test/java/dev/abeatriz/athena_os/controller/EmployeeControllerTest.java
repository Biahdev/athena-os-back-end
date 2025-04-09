package dev.abeatriz.athena_os.controller;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

//    @MockitoBean
//    private EmployeeService service;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper ObjMapper;
//
//    private EmployeeMapper mapper = EmployeeMapper.INSTANCE;
//
//    private Employee employeeEntity;
//    private EmployeeUserCreateDTO employeeCreateDTO;
//    private EmployeeUserDetailDTO employeeDetailDTO;
//
//    private Employee employeeEntity2;
//    private EmployeeUserCreateDTO employeeCreateDTO2;
//    private EmployeeUserDetailDTO employeeDetailDTO2;
//
//    private List<EmployeeUserDetailDTO> employees;
//
//    @BeforeEach
//    void setup() {
//        employeeCreateDTO = new EmployeeUserCreateDTO("Ana", mapper.toString(EmployeeStatus.ATIVO), mapper.toString(EmployeePosition.VENDEDOR), "oi oi oi");
//        employeeEntity = mapper.toEntity(employeeCreateDTO);
//        employeeEntity.setEmployeeId(1L);
//        employeeDetailDTO = mapper.toDTO(employeeEntity);
//
//        employeeCreateDTO2 = new EmployeeUserCreateDTO("Julia", mapper.toString(EmployeeStatus.INATIVO), mapper.toString(EmployeePosition.PROPRIETARIO), "oi oi oi");
//        employeeEntity2 = mapper.toEntity(employeeCreateDTO2);
//        employeeEntity2.setEmployeeId(1L);
//        employeeDetailDTO2 = mapper.toDTO(employeeEntity2);
//
//        employees = new ArrayList<>(Arrays.asList(employeeDetailDTO, employeeDetailDTO2));
//    }
//
//    @AfterEach
//    void tearDown() {
//        reset(service);
//    }
//
//
//    @Test
//    @DisplayName("Create Success")
//    void givenEmployee_whenCreate_thenReturnDetailEmployee() throws Exception {
//        //Given
//        var url = "/employees";
//        given(service.create(any(EmployeeUserCreateDTO.class))).willReturn(employeeDetailDTO);
//
//        // When
//        var response = mockMvc.perform(post(url)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(ObjMapper.writeValueAsString(employeeCreateDTO)));
//
//        //Then
//        response
//            .andDo(print())
//            .andExpect(status().isCreated())
//            .andExpect(jsonPath("$.name", is(employeeDetailDTO.name())))
//            .andExpect(jsonPath("$.status", is(employeeDetailDTO.status().name())))
//            .andExpect(jsonPath("$.position", is(employeeDetailDTO.position().name())))
//            .andExpect(jsonPath("$.note", is(employeeDetailDTO.note())));
//    }
//
//    @Test
//    @DisplayName("ListAll Success")
//    void givenEmployees_whenListAll_thenReturnDetailEmployeeList() throws Exception {
//        //Given
//        var url = "/employees";
//        given(service.listAll()).willReturn(employees);
//
//        // When
//        var response = mockMvc.perform(get(url));
//
//
//        //Then
//        response
//            .andExpect(status().isOk())
//            .andDo(print())
//            .andExpect(jsonPath("$.size()", is(employees.size())));
//
//    }
//
//    @Test
//    @DisplayName("FindById Success")
//    void givenEmployeeId_whenListById_thenReturnDetailEmployee() throws Exception {
//        //Given
//        var id = 1L;
//        var url = "/employees/{id}";
//        given(service.listById(id)).willReturn(employeeDetailDTO);
//
//        // When
//        var response = mockMvc.perform(get(url, id));
//
//        //Then
//        response
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.name", is(employeeDetailDTO.name())))
//            .andExpect(jsonPath("$.status", is(employeeDetailDTO.status().name())))
//            .andExpect(jsonPath("$.position", is(employeeDetailDTO.position().name())))
//            .andExpect(jsonPath("$.note", is(employeeDetailDTO.note())));
//
//    }
//
//    @Test
//    @DisplayName("Update Success")
//    void givenEmployeeAndEmployeeId_whenUpdate_thenReturnDetailEmployeeUpdated() throws Exception {
//        //Given
//        var id = 1L;
//        var url = "/employees/{id}";
//        employeeEntity2.setEmployeeId(id);
//        employeeDetailDTO2 = mapper.toDTO(employeeEntity2);
//        given(service.update(employeeCreateDTO, id)).willReturn(employeeDetailDTO2);
//
//        // When
//        var response = mockMvc.perform(put(url, id)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(ObjMapper.writeValueAsString(employeeCreateDTO)));
//
//        //Then
//        response
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.employeeId", is((int) id)))
//            .andExpect(jsonPath("$.name", is(employeeDetailDTO2.name())))
//            .andExpect(jsonPath("$.status", is(employeeDetailDTO2.status().name())))
//            .andExpect(jsonPath("$.position", is(employeeDetailDTO2.position().name())))
//            .andExpect(jsonPath("$.note", is(employeeDetailDTO2.note())));
//    }
//
//
//    @Test
//    @DisplayName("Disable Success")
//    void givenEmployeeId_whenDisable_thenReturnDetailEmployeeDisabled() throws Exception {
//        //Given
//        var id = 1L;
//        var url = "/employees/{id}/disable";
//        given(service.disable(id)).willReturn(employeeDetailDTO2);
//
//        // When
//        var response = mockMvc.perform(put(url, id));
//
//        // Then
//        response
//            .andExpect(status().isOk())
//            .andDo(print())
//            .andExpect(jsonPath("$.employeeId", is((int) id)))
//            .andExpect(jsonPath("$.status", is(EmployeeStatus.INATIVO.name())));
//    }
//
//
//    @Test
//    @DisplayName("Delete Success")
//    void givenEmployee_whenDelete_thenEmployeeIsDeleted() throws Exception {
//        //Given
//        var id = 1L;
//        var url = "/employees/{id}";
//        willDoNothing().given(service).delete(id);
//
//        // When
//        var response = mockMvc.perform(delete(url, id));
//
//        // Then
//        response.andDo(print()).andExpect(status().isNoContent());
//    }
//
//
//    @Test
//    @DisplayName("Missing Required Fields Return ErrorMessage")
//    void givenMissingRequiredFields_whenCreateEmployee_thenReturnBadRequestWithErrorMessage() throws Exception {
//        //Given -> Name, EmployeeStatus, WhatsApp
//        var url = "/employees";
//        var json = """
//            {
//                "name": "",
//                "status": "",
//                "position": "",
//                "note": ""
//            }""";
//
//        // When
//        var response = mockMvc.perform(post(url)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(json));
//
//        //Then
//        response
//            .andDo(print())
//            .andExpect(status().isBadRequest())
//                   .andExpect(jsonPath("$.message", is("Valores inválidos")))
//            .andExpect(jsonPath("$.fields.name").exists())
//            .andExpect(jsonPath("$.fields.status").exists())
//            .andExpect(jsonPath("$.fields.position").exists())
//            .andExpect(jsonPath("$.fields.note").exists())
//            .andExpect(jsonPath("$.status", is(400)));
//    }
//
//    @Test
//    @DisplayName("Invalid input sizes return ErrorMessage")
//    void givenInvalidInputSizes_whenCreateEmployee_thenReturnBadRequestWithErrorMessage() throws Exception {
//        //Given -> Name, EmployeeStatus, WhatsApp
//        var url = "/employees";
//        var json = """
//            {
//                "name": "",
//                "status": "",
//                "position": "",
//                "note": ""
//            }""";
//
//        // When
//        var response = mockMvc.perform(post(url)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(json));
//
//        //Then
//        response
//            .andDo(print())
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.message", is("Valores inválidos")))
//            .andExpect(jsonPath("$.fields.name").exists())
//            .andExpect(jsonPath("$.fields.status").exists())
//            .andExpect(jsonPath("$.fields.position").exists())
//            .andExpect(jsonPath("$.fields.note").exists())
//            .andExpect(jsonPath("$.status", is(400)));
//    }
//
//    @Test
//    @DisplayName("Invalid Enum value return ErrorMessage")
//    void givenInvalidEnumValue_whenCreateEmployee_thenReturnBadRequestWithErrorMessage() throws Exception {
//        // Given
//        var url = "/employees";
//        var json = """
//            {
//                "name": "",
//                "status": "",
//                "position": "",
//                "note": ""
//            }""";
//        // When
//        var response = mockMvc.perform(post(url)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(json));
//
//        //Then
//        response
//            .andDo(print())
//            .andExpect(status().isBadRequest())
//                   .andExpect(jsonPath("$.message", is("Valores inválidos")))
//            .andExpect(jsonPath("$.fields.status").exists())
//            .andExpect(jsonPath("$.status", is(400)));
//    }
//
//    @Test
//    @DisplayName("Invalid JSON return ErrorMessage")
//    void givenInvalidJson_whenCreateEmployee_thenReturnBadRequestWithErrorMessage() throws Exception {
//        // Given
//        var url = "/employees";
//        var json = """
//            {
//                "name": ""
//                "status": "",
//                "position": "",
//                "note": ""
//            }""";
//        // When
//        var response = mockMvc.perform(post(url)
//            .contentType(MediaType.APPLICATION_JSON)
//            .content(json));
//
//        //Then
//        response
//            .andDo(print())
//            .andExpect(status().isBadRequest())
//            .andExpect(jsonPath("$.status", is(400)))
//            .andExpect(jsonPath("$.message", containsString("JSON")));
//    }
//
//    // TODO: Passar testes de entidade não encontrada para a service
//    @Test
//    @DisplayName("Non existence EmployeeId return ErrorMessage")
//    void givenNonExistenceEmployeeId_whenListById_thenReturnNotFoundWithErrorMessage() throws Exception {
//        //Given
//        var invalidId = 20L;
//        var url = "/employees/{id}";
//        given(service.listById(invalidId)).willThrow(new EntityNotFoundException());
//
//        // When
//        var response = mockMvc.perform(get(url, invalidId));
//
//        //Then
//        response
//            .andDo(print())
//            .andExpect(status().isNotFound())
//            .andExpect(jsonPath("$.message", is("Entidade não foi encontrada")))
//            .andExpect(jsonPath("$.timestamp").exists())
//            .andExpect(jsonPath("$.status", is(404)));
//
//    }
//
//
//    @Test
//    @DisplayName("Invalid resource return ErrorMessage ")
//    void givenInvalidResource_whenRequest_thenReturnNotFoundWithErrorMessage() throws Exception {
//        //TODO: Buscar uma forma de melhorar isso, não faz sentido ser testada em todos os endpoints/entidades
//        //Given
//        var url = "/invalid_resource";
//
//        // When
//        var response = mockMvc.perform(get(url));
//
//        //Then
//        response
//            .andDo(print())
//            .andExpect(status().isNotFound())
//            .andExpect(jsonPath("$.message", is("Recurso não encontrado")))
//            .andExpect(jsonPath("$.timestamp").exists())
//            .andExpect(jsonPath("$.status", is(404)));
//
//    }
//
//    @Test
//    @DisplayName("Invalid HTTP Method")
//    void givenUnsupportedHttpMethod_whenRequest_thenReturnsMethodNotAllowed() throws Exception {
//        // Given
//        var url = "/employees";
//
//        // When
//        var response = mockMvc.perform(put(url));
//
//        // Then
//        response
//            .andDo(print())
//            .andExpect(status().isMethodNotAllowed())
//            .andExpect(jsonPath("$.status", is(405)))
//            .andExpect(jsonPath("$.message").exists())
//            .andExpect(jsonPath("$.timestamp").exists());
//    }


}
