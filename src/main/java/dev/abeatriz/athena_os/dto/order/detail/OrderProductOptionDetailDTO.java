package dev.abeatriz.athena_os.dto.order.detail;

import java.util.List;

public record OrderProductOptionDetailDTO(
    Long orderProductOptionId,
    Long optionId,
    String title,
    List<OrderProductOptionValueDetailDTO> values
) {
}
