package dev.abeatriz.athena_os.dto.order.detail;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserDetailDTO;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderDetailDTO(
    Long orderId,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate deliveryDate,
    String deliveryType,
    Long quantity,
    BigDecimal discountOrder,
    BigDecimal discountOrderProducts,
    BigDecimal discountTotal,
    BigDecimal initialTotal,
    BigDecimal finalTotal,
    String note,
    OrderStatus status,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDateTime createdAt,
    ClientDetailDTO client,
    EmployeeUserDetailDTO employee,
    List<OrderProductDetailDTO> products
) {
}
