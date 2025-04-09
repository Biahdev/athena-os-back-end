package dev.abeatriz.athena_os.dto.order.createUpdate;

import java.math.BigDecimal;
import java.util.List;

public record OrderProductCreateUpdateDTO(
        Long productId,
        Long quantity,
        BigDecimal discount,
        BigDecimal extraPrice,
        BigDecimal initialUnit,
        BigDecimal finalUnit,
        BigDecimal initialTotal,
        BigDecimal finalTotal,
        List<OrderProductOptionCreateUpdateDTO> options

) {



}
