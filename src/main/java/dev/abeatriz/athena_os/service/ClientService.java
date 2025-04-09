package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.client.ClientCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import dev.abeatriz.athena_os.mapper.ClientMapper;
import dev.abeatriz.athena_os.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.persistence.EntityNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientMapper mapper;

    @Transactional()
    public ClientDetailDTO create(ClientCreateUpdateDTO createDTO) {
        var clientEntity = mapper.toEntity(createDTO);
        clientEntity = clientRepository.save(clientEntity);
        return mapper.toDTO(clientEntity);
    }

    @Transactional(readOnly = true)
    public List<ClientDetailDTO> listAll() {
        var allClientEntity = clientRepository.findAll();
        return mapper.toDTO(allClientEntity);
    }

    @Transactional(readOnly = true)
    public ClientDetailDTO listById(Long id) {
        var clientEntity = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.toDTO(clientEntity);
    }

    @Transactional()
    public ClientDetailDTO update(ClientCreateUpdateDTO updateDTO, Long id) {
        var clientEntity = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        clientEntity.update(updateDTO);
        return mapper.toDTO(clientEntity);
    }

    @Transactional()
    public ClientDetailDTO disable(Long id) {
        var clientEntity = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        clientEntity.disable();
        return mapper.toDTO(clientEntity);
    }

    @Transactional()
    public void delete(Long id) {
        var clientEntity = clientRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        clientRepository.delete(clientEntity);
    }

    @Transactional(readOnly = true)
    public List<String> listClientStatus() {
        return Arrays.stream(ClientStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }


}
