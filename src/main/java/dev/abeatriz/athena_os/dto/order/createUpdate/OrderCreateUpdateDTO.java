package dev.abeatriz.athena_os.dto.order.createUpdate;

import com.fasterxml.jackson.annotation.JsonFormat;
import dev.abeatriz.athena_os.entity.enums.OrderDeliveryType;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;
import dev.abeatriz.athena_os.exception.validators.enums.ValueOfEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Schema(title = "Pedidos ")
public record OrderCreateUpdateDTO(

    @Min(1)
    @Schema(title = "Cliente ID", example = "1", type = "long", minimum = "1")
    Long clientId,

    @Min(1)
    @Schema(title = "Funcionario ID", example = "1", type = "long", minimum = "1")
    Long employeeId,

    @ValueOfEnum(enumClass = OrderStatus.class)
    @Schema(title = "Status", example = "PENDENTE", type = "string", defaultValue = "PENDING", allowableValues = {"PENDENTE", "EM_PROCESSAMENTO", "ENTREGUE", "AGUARDANDO_PAGAMENTO", "CONCLUIDO", "CANCELADO"})
    String status,

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate deliveryDate,

    @ValueOfEnum(enumClass = OrderDeliveryType.class)
    @Schema(title = "Tipo de entrega", example = "BUSCAR", type = "string", defaultValue = "PENDING", allowableValues = {"BUSCAR", "ENTREGAR"})
    String deliveryType,

    @Min(1)
    @Schema(title = "Quantidade Total de produtos no pedido", example = "1", type = "long", minimum = "1")
    Long quantity,

    @Schema(title = "Desconto do pedido", example = "10.00", type = "double")
    BigDecimal discountOrder,

    @Schema(title = "Desconto de todos os produtos do pedido", example = "10.00", type = "double")
    BigDecimal discountOrderProducts,

    @Schema(title = "Desconto total", example = "20.00", type = "double")
    BigDecimal discountTotal,

    @Schema(title = "Total com desconto", example = "100.00", type = "double")
    BigDecimal initialTotal,

    @Schema(title = "Total sem desconto", example = "120.00", type = "double")
    BigDecimal finalTotal,

    @Schema(title = "Observações do pedido", example = "Propriae eum graece accusata massa elit dicit appetere saepe aliquip ", type = "string")
    String note,

    @Schema(title = "Items do pedido")
    List<OrderProductCreateUpdateDTO> products


) {


}
