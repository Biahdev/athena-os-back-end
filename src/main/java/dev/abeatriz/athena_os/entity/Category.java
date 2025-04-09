package dev.abeatriz.athena_os.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import dev.abeatriz.athena_os.dto.category.CategoryCreateUpdateDTO;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long categoryId;

    private String name;

    @Enumerated(EnumType.STRING)
    private CategoryStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "category")
    @JsonManagedReference
    private List<Product> products;

    public Category() {  }

    public Category(String name, CategoryStatus status) {
        this.name = name;
        this.status = status;
    }

    public void update(CategoryCreateUpdateDTO json) {
        this.name = json.name() != null ? json.name() : this.name;
        this.status = json.status() != null ? CategoryStatus.valueOf(json.status()) : this.status;
    }

    public void disable() {
        this.status = CategoryStatus.INATIVO;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CategoryStatus getStatus() {
        return status;
    }

    public void setStatus(CategoryStatus status) {
        this.status = status;
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
        Category category = (Category) o;
        return Objects.equals(categoryId, category.categoryId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(categoryId);
    }

    @Override
    public String toString() {
        return "Category{" +
            "categoryId=" + categoryId +
            ", name='" + name + '\'' +
            ", status=" + status +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
