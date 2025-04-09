package dev.abeatriz.athena_os.repository;

import dev.abeatriz.athena_os.dto.order.OrderListDTO;
import dev.abeatriz.athena_os.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("""
        SELECT new dev.abeatriz.athena_os.dto.order.OrderListDTO(
            order.orderId,
            employee.name,
            employee.employeeId,
            client.name,
            client.clientId,
            order.quantity,
            order.finalTotal,
            order.status,
            order.deliveryDate
        )
        FROM Order order
        JOIN order.employee employee
        JOIN order.client client""")
    List<OrderListDTO> findAllOrderListDTO();
}

