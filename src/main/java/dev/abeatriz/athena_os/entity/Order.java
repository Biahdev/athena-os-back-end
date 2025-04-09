package dev.abeatriz.athena_os.entity;

import dev.abeatriz.athena_os.entity.enums.OrderDeliveryType;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @ManyToOne
    @JoinColumn(name = "client_id", referencedColumnName = "client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "delivery_type")
    private OrderDeliveryType deliveryType;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "discount_order", precision = 10, scale = 2)
    private BigDecimal discountOrder;

    @Column(name = "discount_order_products", precision = 10, scale = 2)
    private BigDecimal discountOrderProducts;

    @Column(name = "discount_total", precision = 10, scale = 2)
    private BigDecimal discountTotal;

    @Column(name = "initial_total", precision = 10, scale = 2)
    private BigDecimal initialTotal;

    @Column(name = "final_total", precision = 10, scale = 2)
    private BigDecimal finalTotal;

    private String note;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderProduct> orderProducts;

    public Order() {}

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OrderDeliveryType getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(OrderDeliveryType deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getDiscountOrder() {
        return discountOrder;
    }

    public void setDiscountOrder(BigDecimal discountOrder) {
        this.discountOrder = discountOrder;
    }

    public BigDecimal getDiscountOrderProducts() {
        return discountOrderProducts;
    }

    public void setDiscountOrderProducts(BigDecimal discountOrderProducts) {
        this.discountOrderProducts = discountOrderProducts;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public void setDiscountTotal(BigDecimal discountTotal) {
        this.discountTotal = discountTotal;
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

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }


    @Override
    public String toString() {
        return "Order{" +
            "orderId=" + orderId +
            ", client=" + client +
            ", employee=" + employee +
            ", status=" + status +
            ", deliveryDate=" + deliveryDate +
            ", deliveryType=" + deliveryType +
            ", quantity=" + quantity +
            ", discountOrder=" + discountOrder +
            ", discountOrderProducts=" + discountOrderProducts +
            ", discountTotal=" + discountTotal +
            ", initialTotal=" + initialTotal +
            ", finalTotal=" + finalTotal +
            ", note='" + note + '\'' +
            ", createdAt=" + createdAt +
            ", updatedAt=" + updatedAt +
            ", orderProducts=" + orderProducts +
            '}';
    }
}
