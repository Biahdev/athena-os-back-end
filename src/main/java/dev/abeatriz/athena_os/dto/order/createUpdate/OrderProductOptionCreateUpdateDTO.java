package dev.abeatriz.athena_os.dto.order.createUpdate;

import java.util.List;

public record OrderProductOptionCreateUpdateDTO(
    Long optionId,
    List<OrderProductOptionValueCreateUpdateDTO> values
) {
}
