package dev.abeatriz.athena_os.entity;

import dev.abeatriz.athena_os.dto.employee.EmployeeUserUpdateDTO;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;


@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    private String name;

    @Enumerated(EnumType.STRING)
    private EmployeeStatus status = EmployeeStatus.ATIVO;

    @Enumerated(EnumType.STRING)
    private EmployeePosition position;

    private String note;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "employee")
    private List<Order> orders;

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Employee() {
    }

    public Employee(String name, EmployeePosition position, String note, User user) {
        this.name = name;
        this.position = position;
        this.note = note;
        this.user = user;
    }

    public void update(EmployeeUserUpdateDTO json) {
        this.name = json.name() != null ? json.name() : this.name;
        this.position = json.position() != null ? EmployeePosition.valueOf(json.position()) : this.position;
        this.note = json.note() != null ? json.note() : this.note;
    }

    public void disable() {
        this.status = EmployeeStatus.INATIVO;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EmployeeStatus getStatus() {
        return status;
    }

    public void setStatus(EmployeeStatus status) {
        this.status = status;
    }

    public EmployeePosition getPosition() {
        return position;
    }

    public void setPosition(EmployeePosition position) {
        this.position = position;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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
        Employee employee = (Employee) o;
        return Objects.equals(getEmployeeId(), employee.getEmployeeId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getEmployeeId());
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employee_id=" + employeeId +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", position=" + position +
                ", note='" + note + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
