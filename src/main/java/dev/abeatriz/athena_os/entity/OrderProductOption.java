package dev.abeatriz.athena_os.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_product_options")
public class OrderProductOption {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_option_id")
    private Long orderProductOptionId;

    @ManyToOne
    @JoinColumn(name = "order_product_id", referencedColumnName = "order_product_id")
    private OrderProduct orderProduct;

    @ManyToOne
    @JoinColumn(name = "option_id", referencedColumnName = "option_id")
    private Option option;

    private String title;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orderProductOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductOptionValue> orderProductOptionValues;

    public OrderProductOption() {
    }

    public Long getOrderProductOptionId() {
        return orderProductOptionId;
    }

    public void setOrderProductOptionId(Long orderProductOptionId) {
        this.orderProductOptionId = orderProductOptionId;
    }

    public OrderProduct getOrderProduct() {
        return orderProduct;
    }

    public void setOrderProduct(OrderProduct orderProduct) {
        this.orderProduct = orderProduct;
    }

    public Option getOption() {
        return option;
    }

    public void setOption(Option option) {
        this.option = option;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public List<OrderProductOptionValue> getOrderProductOptionValues() {
        return orderProductOptionValues;
    }

    public void setOrderProductOptionValues(List<OrderProductOptionValue> orderProductOptionValues) {
        this.orderProductOptionValues = orderProductOptionValues;
    }

    @Override
    public String toString() {
        return "OrderProductOption{" +
            "orderProductOptionId=" + orderProductOptionId +
            ", orderProduct=" + orderProduct +
            ", option=" + option +
            ", title='" + title + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", orderProductOptionValues=" + orderProductOptionValues +
            '}';
    }
}
