package dev.abeatriz.athena_os.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "order_product_option_values")
public class OrderProductOptionValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderProductOptionValueId;

    @ManyToOne
    @JoinColumn(name = "order_product_option_id", referencedColumnName = "order_product_option_id")
    private OrderProductOption orderProductOption;

    @ManyToOne
    @JoinColumn(name = "option_value_id", referencedColumnName = "option_value_id")
    private OptionValue optionValue;

    private String name;

    @Column(precision = 10, scale = 2)
    private BigDecimal price;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public OrderProductOptionValue() {}

    public Long getOrderProductOptionValueId() {
        return orderProductOptionValueId;
    }

    public void setOrderProductOptionValueId(Long orderProductOptionValueId) {
        this.orderProductOptionValueId = orderProductOptionValueId;
    }

    public OrderProductOption getOrderProductOption() {
        return orderProductOption;
    }

    public void setOrderProductOption(OrderProductOption orderProductOption) {
        this.orderProductOption = orderProductOption;
    }

    public OptionValue getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(OptionValue optionValue) {
        this.optionValue = optionValue;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "OrderProductOptionValue{" +
            "orderProductOptionValueId=" + orderProductOptionValueId +
            ", orderProductOption=" + orderProductOption +
            ", optionValue=" + optionValue +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            '}';
    }
}
