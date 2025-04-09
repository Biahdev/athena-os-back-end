package dev.abeatriz.athena_os.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

public record OrderListDTO(
    Long orderId,
    String employeeName,
    Long employeeId,
    String clientName,
    Long clientId,
    Long quantity,
    BigDecimal value,
    OrderStatus status,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate deliveryDate

) {
}


/*
        "orderId": 1,
        "employeeName": "Ana Beatriz",
        "clientName": "Cecilia",
        "quantity": 1,
        "orderValue": 100.00,
        "status": "Finalizado",
        "data_entrega" : "09/10/23"
*/
