package dev.abeatriz.athena_os.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "order_products")
public class OrderProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long orderProductId;

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private Product product;

    private Long quantity;

    @Column(precision = 10, scale = 2)
    private BigDecimal discount;

    @Column(name = "extra_price", precision = 10, scale = 2)
    private BigDecimal extraPrice;

    @Column(name = "initial_unit", precision = 10, scale = 2)
    private BigDecimal initialUnit;

    @Column(name = "final_unit", precision = 10, scale = 2)
    private BigDecimal finalUnit;

    @Column(name = "initial_total", precision = 10, scale = 2)
    private BigDecimal initialTotal;

    @Column(name = "final_total", precision = 10, scale = 2)
    private BigDecimal finalTotal;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProductOption> orderProductOptions;

    public OrderProduct() {
    }

    public Long getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(Long orderProductId) {
        this.orderProductId = orderProductId;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public BigDecimal getExtraPrice() {
        return extraPrice;
    }

    public void setExtraPrice(BigDecimal extraPrice) {
        this.extraPrice = extraPrice;
    }

    public BigDecimal getInitialUnit() {
        return initialUnit;
    }

    public void setInitialUnit(BigDecimal initialUnit) {
        this.initialUnit = initialUnit;
    }

    public BigDecimal getFinalUnit() {
        return finalUnit;
    }

    public void setFinalUnit(BigDecimal finalUnit) {
        this.finalUnit = finalUnit;
    }

    public BigDecimal getInitialTotal() {
        return initialTotal;
    }

    public void setInitialTotal(BigDecimal initialTotal) {
        this.initialTotal = initialTotal;
    }

    public BigDecimal getFinalTotal() {
        return finalTotal;
    }

    public void setFinalTotal(BigDecimal finalTotal) {
        this.finalTotal = finalTotal;
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

    public List<OrderProductOption> getOrderProductOptions() {
        return orderProductOptions;
    }

    public void setOrderProductOptions(List<OrderProductOption> orderProductOptions) {
        this.orderProductOptions = orderProductOptions;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
            "orderProductId=" + orderProductId +
            ", order=" + order +
            ", product=" + product +
            ", quantity=" + quantity +
            ", discount=" + discount +
            ", extraPrice=" + extraPrice +
            ", initialUnit=" + initialUnit +
            ", finalUnit=" + finalUnit +
            ", initialTotal=" + initialTotal +
            ", finalTotal=" + finalTotal +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", orderProductOptions=" + orderProductOptions +
            '}';
    }
}
