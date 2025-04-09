package dev.abeatriz.athena_os.dto.order.detail;

import java.math.BigDecimal;

public record OrderProductOptionValueDetailDTO(
    Long orderProductOptionValueId,
    Long optionValueId,
    String name,
    BigDecimal price
) {

}
