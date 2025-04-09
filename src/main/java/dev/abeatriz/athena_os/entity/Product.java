package dev.abeatriz.athena_os.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    private String name;

    private String description;

    @Enumerated(value = EnumType.STRING)
    private ProductStatus status = ProductStatus.REGULAR;

    @Column(name = "cost_value", precision = 10, scale = 2)
    private BigDecimal costValue;

    @Column(name = "sales_value", precision = 10, scale = 2)
    private BigDecimal salesValue;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "category_id", referencedColumnName = "category_id")
    private Category category;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<Option> options;

    public Product() {

    }

    public Product(String name, String description, ProductStatus status, BigDecimal costValue, BigDecimal salesValue, Category category) {
        this.name = name;
        this.description = description;
        this.status = status != null ? status : this.status;
        this.costValue = costValue;
        this.salesValue = salesValue;
        this.category = category;
    }

    public void disable() {
        this.status = ProductStatus.INATIVO;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public ProductStatus getStatus() {
        return status;
    }

    public void setStatus(ProductStatus status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Long getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getCostValue() {
        return costValue;
    }

    public void setCostValue(BigDecimal costValue) {
        this.costValue = costValue;
    }

    public BigDecimal getSalesValue() {
        return salesValue;
    }

    public void setSalesValue(BigDecimal salesValue) {
        this.salesValue = salesValue;
    }

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Category getCategory() {
        return category;
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
        Product product = (Product) o;
        return Objects.equals(getProductId(), product.getProductId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getProductId());
    }

    @Override
    public String toString() {
        return "Product{" +
            "product_id=" + productId +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", categoryId=" + category +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
