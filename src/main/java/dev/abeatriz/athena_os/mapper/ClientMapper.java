package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.client.ClientCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.entity.Client;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    @Mapping(target = "clientId", ignore = true)
    Client toEntity(ClientCreateUpdateDTO client);

    ClientDetailDTO toDTO(Client clientEntity);

    List<ClientDetailDTO> toDTO(List<Client> clientEntity);

    String toString(ClientStatus status);

    ClientStatus toEnumStatus(String status);


}
