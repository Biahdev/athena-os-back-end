package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.client.ClientCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.entity.Client;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-30T18:34:05-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class ClientMapperImpl implements ClientMapper {

    @Override
    public Client toEntity(ClientCreateUpdateDTO client) {
        if ( client == null ) {
            return null;
        }

        Client client1 = new Client();

        client1.setName( client.name() );
        client1.setStatus( toEnumStatus( client.status() ) );
        client1.setAddress( client.address() );
        client1.setPhone( client.phone() );
        client1.setWhatsapp( client.whatsapp() );
        client1.setInstagram( client.instagram() );

        return client1;
    }

    @Override
    public ClientDetailDTO toDTO(Client clientEntity) {
        if ( clientEntity == null ) {
            return null;
        }

        Long clientId = null;
        String name = null;
        ClientStatus status = null;
        String address = null;
        String phone = null;
        Boolean whatsapp = null;
        String instagram = null;

        clientId = clientEntity.getClientId();
        name = clientEntity.getName();
        status = clientEntity.getStatus();
        address = clientEntity.getAddress();
        phone = clientEntity.getPhone();
        whatsapp = clientEntity.getWhatsapp();
        instagram = clientEntity.getInstagram();

        ClientDetailDTO clientDetailDTO = new ClientDetailDTO( clientId, name, status, address, phone, whatsapp, instagram );

        return clientDetailDTO;
    }

    @Override
    public List<ClientDetailDTO> toDTO(List<Client> clientEntity) {
        if ( clientEntity == null ) {
            return null;
        }

        List<ClientDetailDTO> list = new ArrayList<ClientDetailDTO>( clientEntity.size() );
        for ( Client client : clientEntity ) {
            list.add( toDTO( client ) );
        }

        return list;
    }

    @Override
    public String toString(ClientStatus status) {
        if ( status == null ) {
            return null;
        }

        String string;

        switch ( status ) {
            case REGULAR: string = "REGULAR";
            break;
            case INADIMPLENTE: string = "INADIMPLENTE";
            break;
            case INATIVO: string = "INATIVO";
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return string;
    }

    @Override
    public ClientStatus toEnumStatus(String status) {
        if ( status == null ) {
            return null;
        }

        ClientStatus clientStatus;

        switch ( status ) {
            case "REGULAR": clientStatus = ClientStatus.REGULAR;
            break;
            case "INADIMPLENTE": clientStatus = ClientStatus.INADIMPLENTE;
            break;
            case "INATIVO": clientStatus = ClientStatus.INATIVO;
            break;
            default: throw new IllegalArgumentException( "Unexpected enum constant: " + status );
        }

        return clientStatus;
    }
}
