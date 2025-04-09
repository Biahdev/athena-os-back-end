package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.client.ClientCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.entity.Client;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import dev.abeatriz.athena_os.mapper.ClientMapper;
import dev.abeatriz.athena_os.repository.ClientRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.Random.class)
public class ClientServiceTest {

    @Mock
    private ClientRepository repository;

    @Mock
    private ClientMapper mapperMock;
    private ClientMapper mapper = ClientMapper.INSTANCE;

    @InjectMocks
    private ClientService service;

    private Client clientEntity;
    private ClientDetailDTO clientDetail;
    private ClientCreateUpdateDTO client;

    private Client clientEntity2;
    private ClientDetailDTO clientDetail2;
    private ClientCreateUpdateDTO client2;


    @BeforeEach
    void setUp() {
        client = new ClientCreateUpdateDTO("Ana Beatriz", mapper.toString(ClientStatus.REGULAR), "123 Test St", "123456789", true, "@userA");
        clientEntity = mapper.toEntity(client);
        clientEntity.setClientId(1L);
        clientDetail = mapper.toDTO(clientEntity);

        client2 = new ClientCreateUpdateDTO("Cecilia", mapper.toString(ClientStatus.INATIVO), "Rua 123", "99999999", false, "@userA");
        clientEntity2 = mapper.toEntity(client2);
        clientEntity2.setClientId(2L);
        clientDetail2 = mapper.toDTO(clientEntity2);
    }


    @AfterEach
    void tearDown() {
        reset(repository, mapperMock);
    }

    @Test
    @DisplayName("Create Success")
    void givenClient_whenCreate_thenReturnDetailClient() {
        // Given
        given(mapperMock.toEntity(any(ClientCreateUpdateDTO.class))).willReturn(clientEntity);
        given(repository.save(any(Client.class))).willReturn(clientEntity);
        given(mapperMock.toDTO(any(Client.class))).willReturn(clientDetail);


        // When
        var newClient = service.create(client);

        // Then
        assertNotNull(newClient);
        assertEquals(newClient, clientDetail);
        assertEquals(1, newClient.clientId());
        verify(mapperMock, times(1)).toEntity(any(ClientCreateUpdateDTO.class));
        verify(repository, times(1)).save(clientEntity);
        verify(mapperMock, times(1)).toDTO(clientEntity);
    }


    @Test
    @DisplayName("ListById Success")
    void givenClientId_whenListById_thenReturnDetailClient() {
        // Given
        given(repository.findById(1L)).willReturn(Optional.ofNullable(clientEntity));
        given(mapperMock.toDTO(clientEntity)).willReturn(clientDetail);

        // When
        var clientById = service.listById(1L);

        // Then
        assertNotNull(clientById);
        assertEquals(clientDetail, clientById);
        assertEquals(1, clientById.clientId());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(clientEntity);
    }

    @Test
    @DisplayName("Update Success")
    void givenClientAndClientId_whenUpdate_thenReturnDetailClientUpdated() {
        // Given
        given(repository.findById(1L)).willReturn(Optional.ofNullable(clientEntity));
        given(mapperMock.toDTO(clientEntity)).willReturn(clientDetail);

        // When
        var clientUpdate = service.update(client, 1L);

        // Then
        assertNotNull(clientUpdate);
        assertEquals(clientUpdate.name(), clientDetail.name());
        assertEquals(clientUpdate.whatsapp(), clientDetail.whatsapp());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(clientEntity);
    }

    @Test
    @DisplayName("ListAll Success")
    void givenClients_whenListAll_thenReturnDetailClientList() {
        // Given
        var clientEntityList = List.of(clientEntity, clientEntity2);
        var clientDetailList = List.of(clientDetail, clientDetail2);

        given(repository.findAll()).willReturn(clientEntityList);
        given(mapperMock.toDTO(clientEntityList)).willReturn(clientDetailList);

        // When
        var newClientList = service.listAll();

        // Then
        assertNotNull(newClientList);
        assertEquals(newClientList.size(), clientDetailList.size());
        assertEquals(newClientList, clientDetailList);
        assertAll("clientDetailList",
                () -> newClientList.forEach(client -> assertInstanceOf(ClientDetailDTO.class, client))
        );
        verify(repository, times(1)).findAll();
        verify(mapperMock, times(1)).toDTO(anyList());
    }

    @Test
    @DisplayName("Disable Success")
    void givenClientId_whenDisable_thenReturnDetailClientDisabled() {
        // Given
        clientEntity.setStatus(ClientStatus.INATIVO);
        var clientDetailDisable = mapper.toDTO(clientEntity);

        given(repository.findById(1L)).willReturn(Optional.ofNullable(clientEntity));
        given(mapperMock.toDTO(clientEntity)).willReturn(clientDetailDisable);

        // When
        var newClientDisable = service.disable(1L);

        // Then
        assertNotNull(newClientDisable);
        assertEquals(newClientDisable, clientDetailDisable);
        assertEquals(ClientStatus.INATIVO, newClientDisable.status());
        verify(repository, times(1)).findById(1L);
        verify(mapperMock, times(1)).toDTO(clientEntity);
    }


    @Test
    @DisplayName("Delete Success")
    void givenClientId_whenDelete_thenReturnDetailClientDeleted() {
        // Given
        given(repository.findById(1L)).willReturn(Optional.ofNullable(clientEntity));
        willDoNothing().given(repository).delete(clientEntity);

        // When
        service.delete(1L);

        // Then
        verify(repository, times(1)).findById(1L);
        verify(repository, times(1)).delete(clientEntity);

    }
}