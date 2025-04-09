package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.order.OrderListDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.detail.OrderDetailDTO;
import dev.abeatriz.athena_os.entity.OrderProduct;
import dev.abeatriz.athena_os.entity.OrderProductOption;
import dev.abeatriz.athena_os.entity.OrderProductOptionValue;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;
import dev.abeatriz.athena_os.mapper.OrderMapper;
import dev.abeatriz.athena_os.repository.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderMapper mapper;

    @Autowired
    private OptionValueRepository optionValueRepository;

    @Autowired
    private OptionRepository optionRepository;


    //TODO PRECISO FAZER DE UM JEITO QUE PESQUISE EM CADA TABELA 1 VEZ SÓ
    @Transactional
    public OrderDetailDTO create(OrderCreateUpdateDTO orderCreateDTO) {
        var orderEntity = mapper.toEntity(orderCreateDTO);
        var client = clientRepository.findById(orderCreateDTO.clientId()).orElseThrow(() -> new EntityNotFoundException("Não existe um cliente com esse ID"));
        var employee = employeeRepository.findById(orderCreateDTO.employeeId()).orElseThrow(() -> new EntityNotFoundException("Não existe um funcionário com esse ID"));

        List<OrderProduct> orderProducts = mapper.toEntityProductList(orderCreateDTO.products());

        for (OrderProduct orderProduct : orderProducts) {
            var product = productRepository.findById(orderProduct.getProduct().getProductId()).orElseThrow(() -> new EntityNotFoundException("Não existe um produto com esse ID"));
            orderProduct.setProduct(product);
            orderProduct.setOrder(orderEntity);

            for (OrderProductOption orderProductOption : orderProduct.getOrderProductOptions()) {
                var option = optionRepository.findById(orderProductOption.getOption().getOptionId()).orElseThrow(() -> new EntityNotFoundException("Não existe um Option com esse ID"));
                orderProductOption.setOption(option);
                orderProductOption.setOrderProduct(orderProduct);
                orderProductOption.setTitle(option.getTitle());

                for (OrderProductOptionValue orderProductOptionValue : orderProductOption.getOrderProductOptionValues()) {
                    var optionValue = optionValueRepository.findById(orderProductOptionValue.getOptionValue().getOptionValueId()).orElseThrow(() -> new EntityNotFoundException("Não existe um OptionValue com esse ID"));
                    orderProductOptionValue.setOptionValue(optionValue);
                    orderProductOptionValue.setOrderProductOption(orderProductOption);
                    orderProductOptionValue.setName(optionValue.getName());
                    orderProductOptionValue.setPrice(optionValue.getPrice());
                }
            }
        }

        orderEntity.setClient(client);
        orderEntity.setEmployee(employee);
        orderEntity.setOrderProducts(orderProducts);

        var newOrder = orderRepository.save(orderEntity);
        return mapper.toDTO(newOrder);
    }


    @Transactional(readOnly = true)
    public List<OrderListDTO> listAll() {
        return orderRepository.findAllOrderListDTO();
    }

    @Transactional(readOnly = true)
    public OrderDetailDTO listById(Long id) {
        var order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.toDTO(order);
    }


    @Transactional(readOnly = true)
    public List<String> listStatus() {
        return Arrays
            .stream(OrderStatus.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long id) {
        var order = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        orderRepository.delete(order);
    }

    /*
    @Transactional
    public OrderDetailDTO update(OrderCreateUpdateDTO updateDTO, Long id) {
        var orderEntity = orderRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        var client = clientRepository.findById(updateDTO.clientId()).orElseThrow(() -> new EntityNotFoundException("Não existe um cliente com esse ID"));
        var employee = employeeRepository.findById(updateDTO.employeeId()).orElseThrow(() -> new EntityNotFoundException("Não existe um funcionário com esse ID"));

        orderEntity.update(updateDTO);
        orderEntity.setClientId(client);
        orderEntity.setEmployeeId(employee);

        var newOrder = orderRepository.save(orderEntity);
        return mapper.toDTO(newOrder);
    }*/


}
