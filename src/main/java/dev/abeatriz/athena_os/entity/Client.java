package dev.abeatriz.athena_os.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import dev.abeatriz.athena_os.dto.client.ClientCreateUpdateDTO;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "clients")
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_id")
    private Long clientId;

    private String name;

    @Enumerated(EnumType.STRING)
    private ClientStatus status = ClientStatus.REGULAR;

    private String address;

    private String phone;

    private Boolean whatsapp = true;

    private String instagram;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "client")
    private List<Order> orders;

    public Client() {}

    public Client(String name, ClientStatus status, String address, String phone, Boolean whatsapp, String instagram) {
        this.name = name;
        this.status = status;
        this.address = address;
        this.phone = phone;
        this.whatsapp = whatsapp;
        this.instagram = instagram;
    }

    public void update(ClientCreateUpdateDTO json){
        this.name = json.name() != null ? json.name() : this.name;
        this.status = json.status() != null ? ClientStatus.valueOf(json.status()) : this.status;
        this.address = json.address() != null ? json.address() : this.address;
        this.phone = json.phone() != null ? json.phone() : this.phone;
        this.whatsapp = json.whatsapp() != null ? json.whatsapp() : this.whatsapp;
        this.instagram = json.instagram() != null ? json.instagram() : this.instagram;
    }

    public void disable(){
        this.status = ClientStatus.INATIVO;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ClientStatus getStatus() {
        return status;
    }

    public void setStatus(ClientStatus clientStatus) {
        this.status = clientStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(Boolean whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return getClientId() == client.getClientId();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getClientId());
    }

    @Override
    public String toString() {
        return "Client{" +
            "client_id=" + clientId +
            ", name='" + name + '\'' +
            ", statusClient=" + status +
            ", address='" + address + '\'' +
            ", phone='" + phone + '\'' +
            ", whatsapp=" + whatsapp +
            ", intagram='" + instagram + '\'' +
            '}';
    }
}
