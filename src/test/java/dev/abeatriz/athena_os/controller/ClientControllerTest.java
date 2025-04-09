package dev.abeatriz.athena_os.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abeatriz.athena_os.dto.client.ClientCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.entity.Client;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import dev.abeatriz.athena_os.mapper.ClientMapper;
import dev.abeatriz.athena_os.service.ClientService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(ClientController.class)
public class ClientControllerTest {

    @MockitoBean
    private ClientService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper ObjMapper;

    private ClientMapper mapper = ClientMapper.INSTANCE;


    private Client clientEntity;
    private ClientDetailDTO clientDetail;
    private ClientCreateUpdateDTO clientCreate;

    private Client clientEntity2;
    private ClientDetailDTO clientDetail2;
    private ClientCreateUpdateDTO clientCreate2;

    private List<ClientDetailDTO> clients;


    @BeforeEach
    void setup() {
        clientCreate = new ClientCreateUpdateDTO("Ana Beatriz", mapper.toString(ClientStatus.REGULAR), "123 Test St", "1234556786611", true, "@userA");
        clientEntity = mapper.toEntity(clientCreate);
        clientEntity.setClientId(1L);
        clientDetail = mapper.toDTO(clientEntity);

        clientCreate2 = new ClientCreateUpdateDTO("Cecilia", mapper.toString(ClientStatus.INATIVO), "Rua 123", "99999999", false, "@userA");
        clientEntity2 = mapper.toEntity(clientCreate2);
        clientEntity2.setClientId(2L);
        clientDetail2 = mapper.toDTO(clientEntity2);

        clients = new ArrayList<>(Arrays.asList(clientDetail, clientDetail2));
    }

    @AfterEach
    void tearDown() {
        reset(service);
    }

    @Test
    @DisplayName("Create Success")
    void givenClient_whenCreate_thenReturnDetailClient() throws Exception {
        //Given
        var url = "/clients";
        given(service.create(any(ClientCreateUpdateDTO.class))).willReturn(clientDetail);

        // When
        var response = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjMapper.writeValueAsString(clientCreate)));

        //Then
        response
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(clientDetail.name())))
            .andExpect(jsonPath("$.status", is(clientDetail.status().name())))
            .andExpect(jsonPath("$.address", is(clientDetail.address())))
            .andExpect(jsonPath("$.phone", is(clientDetail.phone())))
            .andExpect(jsonPath("$.whatsapp", is(clientDetail.whatsapp())))
            .andExpect(jsonPath("$.instagram", is(clientDetail.instagram())));

    }

    @Test
    @DisplayName("ListAll Success")
    void givenClients_whenListAll_thenReturnDetailClientList() throws Exception {
        //Given
        var url = "/clients";
        given(service.listAll()).willReturn(clients);

        // When
        var response = mockMvc.perform(get(url));


        //Then
        response
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.size()", is(clients.size())));

    }

    @Test
    @DisplayName("FindById Success")
    void givenClientId_whenListById_thenReturnDetailClient() throws Exception {
        //Given
        var id = 1L;
        var url = "/clients/{id}";
        given(service.listById(id)).willReturn(clientDetail);

        // When
        var response = mockMvc.perform(get(url, id));

        //Then
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(clientDetail.name())))
            .andExpect(jsonPath("$.status", is(clientDetail.status().name())))
            .andExpect(jsonPath("$.address", is(clientDetail.address())))
            .andExpect(jsonPath("$.phone", is(clientDetail.phone())))
            .andExpect(jsonPath("$.whatsapp", is(clientDetail.whatsapp())))
            .andExpect(jsonPath("$.instagram", is(clientDetail.instagram())));

    }

    @Test
    @DisplayName("Update Success")
    void givenClientAndClientId_whenUpdate_thenReturnDetailClientUpdated() throws Exception {
        //Given
        var id = 1L;
        var url = "/clients/{id}";
        clientEntity2.setClientId(id);
        clientDetail2 = mapper.toDTO(clientEntity2);
        given(service.update(clientCreate, id)).willReturn(clientDetail2);

        // When
        var response = mockMvc.perform(put(url, id)
            .contentType(MediaType.APPLICATION_JSON)
            .content(ObjMapper.writeValueAsString(clientCreate)));

        //Then
        response
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.clientId", is((int) id)))
            .andExpect(jsonPath("$.name", is(clientDetail2.name())))
            .andExpect(jsonPath("$.status", is(clientDetail2.status().name())))
            .andExpect(jsonPath("$.address", is(clientDetail2.address())))
            .andExpect(jsonPath("$.phone", is(clientDetail2.phone())))
            .andExpect(jsonPath("$.whatsapp", is(clientDetail2.whatsapp())))
            .andExpect(jsonPath("$.instagram", is(clientDetail2.instagram())));
    }


    @Test
    @DisplayName("Disable Success")
    void givenClientId_whenDisable_thenReturnDetailClientDisabled() throws Exception {
        //Given
        var id = 2L;
        var url = "/clients/{id}/disable";
        given(service.disable(id)).willReturn(clientDetail2);

        // When
        var response = mockMvc.perform(put(url, id));

        // Then
        response
            .andExpect(status().isOk())
            .andDo(print())
            .andExpect(jsonPath("$.clientId", is((int) id)))
            .andExpect(jsonPath("$.status", is(ClientStatus.INATIVO.name())));
    }


    @Test
    @DisplayName("Delete Success")
    void givenClient_whenDelete_thenClientIsDeleted() throws Exception {
        //Given
        var id = 1L;
        var url = "/clients/{id}";
        willDoNothing().given(service).delete(id);

        // When
        var response = mockMvc.perform(delete(url, id));

        // Then
        response.andDo(print()).andExpect(status().isNoContent());
    }


    @Test
    @DisplayName("Missing Required Fields Return ErrorMessage")
    void givenMissingRequiredFields_whenCreateClient_thenReturnBadRequestWithErrorMessage() throws Exception {
        //Given -> Name, ClientStatus, WhatsApp
        var url = "/clients";
        var json = """
            {
                "name": "",
                "status": "",
                "address": "",
                "phone": "",
                "whatsapp": "",
                "instagram": ""
            }""";

        // When
        var response = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Valores inválidos")))
            .andExpect(jsonPath("$.fields.name").exists())
            .andExpect(jsonPath("$.fields.whatsapp").exists())
            .andExpect(jsonPath("$.fields.status").exists())
            .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid input sizes return ErrorMessage")
    void givenInvalidInputSizes_whenCreateClient_thenReturnBadRequestWithErrorMessage() throws Exception {
        //Given -> Name, ClientStatus, WhatsApp
        var url = "/clients";
        var json = """
            {
                "name": "aa",
                "status": "REGULAR",
                "address": "aa",
                "phone": "12345",
                "whatsapp": true,
                "instagram": "aa"
            }""";

        // When
        var response = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Valores inválidos")))
            .andExpect(jsonPath("$.fields.name").exists())
            .andExpect(jsonPath("$.fields.address").exists())
            .andExpect(jsonPath("$.fields.phone").exists())
            .andExpect(jsonPath("$.fields.instagram").exists())
            .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid Enum value return ErrorMessage")
    void givenInvalidEnumValue_whenCreateClient_thenReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        var url = "/clients";
        var json = """
            {
                "name": "Ana Beatriz",
                "status": "INVALIDO_ENUM",
                "address": "Rua deputado aaa",
                "phone": "123456789011",
                "whatsapp": true,
                "instagram": "dasdasdas"
            }""";
        // When
        var response = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.message", is("Valores inválidos")))
            .andExpect(jsonPath("$.fields.status").exists())
            .andExpect(jsonPath("$.status", is(400)));
    }

    @Test
    @DisplayName("Invalid JSON return ErrorMessage")
    void givenInvalidJson_whenCreateClient_thenReturnBadRequestWithErrorMessage() throws Exception {
        // Given
        var url = "/clients";
        var json = """
            {
                "name": "Ana Beatriz"
                "status: "INVALIDO",
                "address": "Rua deputado aaa",
                "phone": "123456789011",
                "whatsapp": true,
                "instagram": "dasdasdas"
            }""";
        // When
        var response = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json));

        //Then
        response
            .andDo(print())
            .andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.status", is(400)))
            .andExpect(jsonPath("$.message", containsString("JSON")));
    }

    @Test
    @DisplayName("Non existence ClientId return ErrorMessage")
    void givenNonExistenceClientId_whenListById_thenReturnNotFoundWithErrorMessage() throws Exception {
        //Given
        var invalidId = 20L;
        var url = "/clients/{id}";
        given(service.listById(invalidId)).willThrow(new EntityNotFoundException());

        // When
        var response = mockMvc.perform(get(url, invalidId));

        //Then
        response
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("Entidade não foi encontrada")))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status", is(404)));

    }


    @Test
    @DisplayName("Invalid resource return ErrorMessage ")
    void givenInvalidResource_whenRequest_thenReturnNotFoundWithErrorMessage() throws Exception {
        //Given
        var url = "/invalid_resource";

        // When
        var response = mockMvc.perform(get(url));

        //Then
        response
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.message", is("Recurso não encontrado")))
            .andExpect(jsonPath("$.timestamp").exists())
            .andExpect(jsonPath("$.status", is(404)));

    }

    @Test
    @DisplayName("Invalid HTTP Method")
    void givenUnsupportedHttpMethod_whenRequest_thenReturnsMethodNotAllowed() throws Exception {
        // Given
        var url = "/clients";

        // When
        var response = mockMvc.perform(put(url));

        // Then
        response
            .andDo(print())
            .andExpect(status().isMethodNotAllowed())
            .andExpect(jsonPath("$.status", is(405)))
            .andExpect(jsonPath("$.message").exists())
            .andExpect(jsonPath("$.timestamp").exists());
    }


}
