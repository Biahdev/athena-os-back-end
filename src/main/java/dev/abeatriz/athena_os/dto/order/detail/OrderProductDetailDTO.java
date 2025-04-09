package dev.abeatriz.athena_os.dto.order.detail;

import java.math.BigDecimal;
import java.util.List;

public record OrderProductDetailDTO(
        Long orderProductId,
        Long productId,
        String name,
        Long quantity,
        BigDecimal discount,
        BigDecimal extraPrice,
        BigDecimal initialUnit,
        BigDecimal finalUnit,
        BigDecimal initialTotal,
        BigDecimal finalTotal,
        List<OrderProductOptionDetailDTO>options
) {



}
