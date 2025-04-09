package dev.abeatriz.athena_os.mapper;

import dev.abeatriz.athena_os.dto.client.ClientDetailDTO;
import dev.abeatriz.athena_os.dto.employee.EmployeeUserDetailDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderProductOptionCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderProductOptionValueCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.detail.OrderDetailDTO;
import dev.abeatriz.athena_os.dto.order.detail.OrderProductDetailDTO;
import dev.abeatriz.athena_os.dto.order.detail.OrderProductOptionDetailDTO;
import dev.abeatriz.athena_os.dto.order.detail.OrderProductOptionValueDetailDTO;
import dev.abeatriz.athena_os.entity.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    ClientDetailDTO toClientDTO(Client client);

    EmployeeUserDetailDTO toEmployeeDTO(Employee employee);

    @Mapping(source = "clientId", target = "client.clientId")
    @Mapping(source = "employeeId", target = "employee.employeeId")
    @Mapping(source = "products", target = "orderProducts")
    Order toEntity(OrderCreateUpdateDTO dto);

    @Mapping(source = "productId", target = "product.productId")
    @Mapping(source = "options", target = "orderProductOptions")
    OrderProduct toEntity(OrderProductCreateUpdateDTO product);

    @Mapping(source = "optionId", target = "option.optionId")
    @Mapping(source = "values", target = "orderProductOptionValues")
    OrderProductOption toEntity(OrderProductOptionCreateUpdateDTO product);

    @Mapping(source = "valueId", target = "optionValue.optionValueId")
    OrderProductOptionValue toEntity(OrderProductOptionValueCreateUpdateDTO product);

    List<OrderProduct> toEntityProductList(List<OrderProductCreateUpdateDTO> dto);

    List<OrderProductOption> toEntityProductOptionList(List<OrderProductOptionCreateUpdateDTO> dto);

    List<OrderProductOptionValue> toEntityProductOptionValueList(List<OrderProductOptionValueCreateUpdateDTO> dto);


    @Mapping(source = "order.client", target = "client")
    @Mapping(source = "order.employee", target = "employee")
    @Mapping(source = "order.orderProducts", target = "products")
    @Mapping(source = "order.createdAt", target = "createdAt")
    OrderDetailDTO toDTO(Order order);

    @Mapping(source = "product.productId", target = "productId")
    @Mapping(source = "product.name", target = "name")
    @Mapping(source = "product.orderProductOptions", target = "options")
    OrderProductDetailDTO toDTO(OrderProduct product);

    @Mapping(source = "option.optionId", target = "optionId")
    @Mapping(source = "option.orderProductOptionValues", target = "values")
    OrderProductOptionDetailDTO toDTO(OrderProductOption option);

    @Mapping(source = "optionValue.optionValueId", target = "optionValueId")
    OrderProductOptionValueDetailDTO toDTO(OrderProductOptionValue optionValue);


}
