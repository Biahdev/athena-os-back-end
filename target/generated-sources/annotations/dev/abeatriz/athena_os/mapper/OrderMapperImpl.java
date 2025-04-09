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
import dev.abeatriz.athena_os.entity.Client;
import dev.abeatriz.athena_os.entity.Employee;
import dev.abeatriz.athena_os.entity.Option;
import dev.abeatriz.athena_os.entity.OptionValue;
import dev.abeatriz.athena_os.entity.Order;
import dev.abeatriz.athena_os.entity.OrderProduct;
import dev.abeatriz.athena_os.entity.OrderProductOption;
import dev.abeatriz.athena_os.entity.OrderProductOptionValue;
import dev.abeatriz.athena_os.entity.Product;
import dev.abeatriz.athena_os.entity.enums.ClientStatus;
import dev.abeatriz.athena_os.entity.enums.EmployeePosition;
import dev.abeatriz.athena_os.entity.enums.EmployeeStatus;
import dev.abeatriz.athena_os.entity.enums.OrderDeliveryType;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;
import dev.abeatriz.athena_os.entity.enums.UserRole;
import dev.abeatriz.athena_os.entity.enums.UserStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-30T19:54:29-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public ClientDetailDTO toClientDTO(Client client) {
        if ( client == null ) {
            return null;
        }

        Long clientId = null;
        String name = null;
        ClientStatus status = null;
        String address = null;
        String phone = null;
        Boolean whatsapp = null;
        String instagram = null;

        clientId = client.getClientId();
        name = client.getName();
        status = client.getStatus();
        address = client.getAddress();
        phone = client.getPhone();
        whatsapp = client.getWhatsapp();
        instagram = client.getInstagram();

        ClientDetailDTO clientDetailDTO = new ClientDetailDTO( clientId, name, status, address, phone, whatsapp, instagram );

        return clientDetailDTO;
    }

    @Override
    public EmployeeUserDetailDTO toEmployeeDTO(Employee employee) {
        if ( employee == null ) {
            return null;
        }

        Long employeeId = null;
        String name = null;
        EmployeeStatus status = null;
        EmployeePosition position = null;
        String note = null;

        employeeId = employee.getEmployeeId();
        name = employee.getName();
        status = employee.getStatus();
        position = employee.getPosition();
        note = employee.getNote();

        String email = null;
        UserStatus userStatus = null;
        UserRole userRole = null;

        EmployeeUserDetailDTO employeeUserDetailDTO = new EmployeeUserDetailDTO( employeeId, name, status, position, note, email, userStatus, userRole );

        return employeeUserDetailDTO;
    }

    @Override
    public Order toEntity(OrderCreateUpdateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Order order = new Order();

        order.setClient( orderCreateUpdateDTOToClient( dto ) );
        order.setEmployee( orderCreateUpdateDTOToEmployee( dto ) );
        order.setOrderProducts( toEntityProductList( dto.products() ) );
        if ( dto.status() != null ) {
            order.setStatus( Enum.valueOf( OrderStatus.class, dto.status() ) );
        }
        order.setDeliveryDate( dto.deliveryDate() );
        if ( dto.deliveryType() != null ) {
            order.setDeliveryType( Enum.valueOf( OrderDeliveryType.class, dto.deliveryType() ) );
        }
        order.setQuantity( dto.quantity() );
        order.setDiscountOrder( dto.discountOrder() );
        order.setDiscountOrderProducts( dto.discountOrderProducts() );
        order.setDiscountTotal( dto.discountTotal() );
        order.setInitialTotal( dto.initialTotal() );
        order.setFinalTotal( dto.finalTotal() );
        order.setNote( dto.note() );

        return order;
    }

    @Override
    public OrderProduct toEntity(OrderProductCreateUpdateDTO product) {
        if ( product == null ) {
            return null;
        }

        OrderProduct orderProduct = new OrderProduct();

        orderProduct.setProduct( orderProductCreateUpdateDTOToProduct( product ) );
        orderProduct.setOrderProductOptions( toEntityProductOptionList( product.options() ) );
        orderProduct.setQuantity( product.quantity() );
        orderProduct.setDiscount( product.discount() );
        orderProduct.setExtraPrice( product.extraPrice() );
        orderProduct.setInitialUnit( product.initialUnit() );
        orderProduct.setFinalUnit( product.finalUnit() );
        orderProduct.setInitialTotal( product.initialTotal() );
        orderProduct.setFinalTotal( product.finalTotal() );

        return orderProduct;
    }

    @Override
    public OrderProductOption toEntity(OrderProductOptionCreateUpdateDTO product) {
        if ( product == null ) {
            return null;
        }

        OrderProductOption orderProductOption = new OrderProductOption();

        orderProductOption.setOption( orderProductOptionCreateUpdateDTOToOption( product ) );
        orderProductOption.setOrderProductOptionValues( toEntityProductOptionValueList( product.values() ) );

        return orderProductOption;
    }

    @Override
    public OrderProductOptionValue toEntity(OrderProductOptionValueCreateUpdateDTO product) {
        if ( product == null ) {
            return null;
        }

        OrderProductOptionValue orderProductOptionValue = new OrderProductOptionValue();

        orderProductOptionValue.setOptionValue( orderProductOptionValueCreateUpdateDTOToOptionValue( product ) );

        return orderProductOptionValue;
    }

    @Override
    public List<OrderProduct> toEntityProductList(List<OrderProductCreateUpdateDTO> dto) {
        if ( dto == null ) {
            return null;
        }

        List<OrderProduct> list = new ArrayList<OrderProduct>( dto.size() );
        for ( OrderProductCreateUpdateDTO orderProductCreateUpdateDTO : dto ) {
            list.add( toEntity( orderProductCreateUpdateDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderProductOption> toEntityProductOptionList(List<OrderProductOptionCreateUpdateDTO> dto) {
        if ( dto == null ) {
            return null;
        }

        List<OrderProductOption> list = new ArrayList<OrderProductOption>( dto.size() );
        for ( OrderProductOptionCreateUpdateDTO orderProductOptionCreateUpdateDTO : dto ) {
            list.add( toEntity( orderProductOptionCreateUpdateDTO ) );
        }

        return list;
    }

    @Override
    public List<OrderProductOptionValue> toEntityProductOptionValueList(List<OrderProductOptionValueCreateUpdateDTO> dto) {
        if ( dto == null ) {
            return null;
        }

        List<OrderProductOptionValue> list = new ArrayList<OrderProductOptionValue>( dto.size() );
        for ( OrderProductOptionValueCreateUpdateDTO orderProductOptionValueCreateUpdateDTO : dto ) {
            list.add( toEntity( orderProductOptionValueCreateUpdateDTO ) );
        }

        return list;
    }

    @Override
    public OrderDetailDTO toDTO(Order order) {
        if ( order == null ) {
            return null;
        }

        ClientDetailDTO client = null;
        EmployeeUserDetailDTO employee = null;
        List<OrderProductDetailDTO> products = null;
        LocalDateTime createdAt = null;
        Long orderId = null;
        LocalDate deliveryDate = null;
        String deliveryType = null;
        Long quantity = null;
        BigDecimal discountOrder = null;
        BigDecimal discountOrderProducts = null;
        BigDecimal discountTotal = null;
        BigDecimal initialTotal = null;
        BigDecimal finalTotal = null;
        String note = null;
        OrderStatus status = null;

        client = toClientDTO( order.getClient() );
        employee = toEmployeeDTO( order.getEmployee() );
        products = orderProductListToOrderProductDetailDTOList( order.getOrderProducts() );
        createdAt = order.getCreatedAt();
        orderId = order.getOrderId();
        deliveryDate = order.getDeliveryDate();
        if ( order.getDeliveryType() != null ) {
            deliveryType = order.getDeliveryType().name();
        }
        quantity = order.getQuantity();
        discountOrder = order.getDiscountOrder();
        discountOrderProducts = order.getDiscountOrderProducts();
        discountTotal = order.getDiscountTotal();
        initialTotal = order.getInitialTotal();
        finalTotal = order.getFinalTotal();
        note = order.getNote();
        status = order.getStatus();

        OrderDetailDTO orderDetailDTO = new OrderDetailDTO( orderId, deliveryDate, deliveryType, quantity, discountOrder, discountOrderProducts, discountTotal, initialTotal, finalTotal, note, status, createdAt, client, employee, products );

        return orderDetailDTO;
    }

    @Override
    public OrderProductDetailDTO toDTO(OrderProduct product) {
        if ( product == null ) {
            return null;
        }

        Long productId = null;
        String name = null;
        List<OrderProductOptionDetailDTO> options = null;
        Long orderProductId = null;
        Long quantity = null;
        BigDecimal discount = null;
        BigDecimal extraPrice = null;
        BigDecimal initialUnit = null;
        BigDecimal finalUnit = null;
        BigDecimal initialTotal = null;
        BigDecimal finalTotal = null;

        productId = productProductProductId( product );
        name = productProductName( product );
        options = orderProductOptionListToOrderProductOptionDetailDTOList( product.getOrderProductOptions() );
        orderProductId = product.getOrderProductId();
        quantity = product.getQuantity();
        discount = product.getDiscount();
        extraPrice = product.getExtraPrice();
        initialUnit = product.getInitialUnit();
        finalUnit = product.getFinalUnit();
        initialTotal = product.getInitialTotal();
        finalTotal = product.getFinalTotal();

        OrderProductDetailDTO orderProductDetailDTO = new OrderProductDetailDTO( orderProductId, productId, name, quantity, discount, extraPrice, initialUnit, finalUnit, initialTotal, finalTotal, options );

        return orderProductDetailDTO;
    }

    @Override
    public OrderProductOptionDetailDTO toDTO(OrderProductOption option) {
        if ( option == null ) {
            return null;
        }

        Long optionId = null;
        List<OrderProductOptionValueDetailDTO> values = null;
        Long orderProductOptionId = null;
        String title = null;

        optionId = optionOptionOptionId( option );
        values = orderProductOptionValueListToOrderProductOptionValueDetailDTOList( option.getOrderProductOptionValues() );
        orderProductOptionId = option.getOrderProductOptionId();
        title = option.getTitle();

        OrderProductOptionDetailDTO orderProductOptionDetailDTO = new OrderProductOptionDetailDTO( orderProductOptionId, optionId, title, values );

        return orderProductOptionDetailDTO;
    }

    @Override
    public OrderProductOptionValueDetailDTO toDTO(OrderProductOptionValue optionValue) {
        if ( optionValue == null ) {
            return null;
        }

        Long optionValueId = null;
        Long orderProductOptionValueId = null;
        String name = null;
        BigDecimal price = null;

        optionValueId = optionValueOptionValueOptionValueId( optionValue );
        orderProductOptionValueId = optionValue.getOrderProductOptionValueId();
        name = optionValue.getName();
        price = optionValue.getPrice();

        OrderProductOptionValueDetailDTO orderProductOptionValueDetailDTO = new OrderProductOptionValueDetailDTO( orderProductOptionValueId, optionValueId, name, price );

        return orderProductOptionValueDetailDTO;
    }

    protected Client orderCreateUpdateDTOToClient(OrderCreateUpdateDTO orderCreateUpdateDTO) {
        if ( orderCreateUpdateDTO == null ) {
            return null;
        }

        Client client = new Client();

        client.setClientId( orderCreateUpdateDTO.clientId() );

        return client;
    }

    protected Employee orderCreateUpdateDTOToEmployee(OrderCreateUpdateDTO orderCreateUpdateDTO) {
        if ( orderCreateUpdateDTO == null ) {
            return null;
        }

        Employee employee = new Employee();

        employee.setEmployeeId( orderCreateUpdateDTO.employeeId() );

        return employee;
    }

    protected Product orderProductCreateUpdateDTOToProduct(OrderProductCreateUpdateDTO orderProductCreateUpdateDTO) {
        if ( orderProductCreateUpdateDTO == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductId( orderProductCreateUpdateDTO.productId() );

        return product;
    }

    protected Option orderProductOptionCreateUpdateDTOToOption(OrderProductOptionCreateUpdateDTO orderProductOptionCreateUpdateDTO) {
        if ( orderProductOptionCreateUpdateDTO == null ) {
            return null;
        }

        Option option = new Option();

        option.setOptionId( orderProductOptionCreateUpdateDTO.optionId() );

        return option;
    }

    protected OptionValue orderProductOptionValueCreateUpdateDTOToOptionValue(OrderProductOptionValueCreateUpdateDTO orderProductOptionValueCreateUpdateDTO) {
        if ( orderProductOptionValueCreateUpdateDTO == null ) {
            return null;
        }

        OptionValue optionValue = new OptionValue();

        optionValue.setOptionValueId( orderProductOptionValueCreateUpdateDTO.valueId() );

        return optionValue;
    }

    protected List<OrderProductDetailDTO> orderProductListToOrderProductDetailDTOList(List<OrderProduct> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderProductDetailDTO> list1 = new ArrayList<OrderProductDetailDTO>( list.size() );
        for ( OrderProduct orderProduct : list ) {
            list1.add( toDTO( orderProduct ) );
        }

        return list1;
    }

    private Long productProductProductId(OrderProduct orderProduct) {
        if ( orderProduct == null ) {
            return null;
        }
        Product product = orderProduct.getProduct();
        if ( product == null ) {
            return null;
        }
        Long productId = product.getProductId();
        if ( productId == null ) {
            return null;
        }
        return productId;
    }

    private String productProductName(OrderProduct orderProduct) {
        if ( orderProduct == null ) {
            return null;
        }
        Product product = orderProduct.getProduct();
        if ( product == null ) {
            return null;
        }
        String name = product.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    protected List<OrderProductOptionDetailDTO> orderProductOptionListToOrderProductOptionDetailDTOList(List<OrderProductOption> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderProductOptionDetailDTO> list1 = new ArrayList<OrderProductOptionDetailDTO>( list.size() );
        for ( OrderProductOption orderProductOption : list ) {
            list1.add( toDTO( orderProductOption ) );
        }

        return list1;
    }

    private Long optionOptionOptionId(OrderProductOption orderProductOption) {
        if ( orderProductOption == null ) {
            return null;
        }
        Option option = orderProductOption.getOption();
        if ( option == null ) {
            return null;
        }
        Long optionId = option.getOptionId();
        if ( optionId == null ) {
            return null;
        }
        return optionId;
    }

    protected List<OrderProductOptionValueDetailDTO> orderProductOptionValueListToOrderProductOptionValueDetailDTOList(List<OrderProductOptionValue> list) {
        if ( list == null ) {
            return null;
        }

        List<OrderProductOptionValueDetailDTO> list1 = new ArrayList<OrderProductOptionValueDetailDTO>( list.size() );
        for ( OrderProductOptionValue orderProductOptionValue : list ) {
            list1.add( toDTO( orderProductOptionValue ) );
        }

        return list1;
    }

    private Long optionValueOptionValueOptionValueId(OrderProductOptionValue orderProductOptionValue) {
        if ( orderProductOptionValue == null ) {
            return null;
        }
        OptionValue optionValue = orderProductOptionValue.getOptionValue();
        if ( optionValue == null ) {
            return null;
        }
        Long optionValueId = optionValue.getOptionValueId();
        if ( optionValueId == null ) {
            return null;
        }
        return optionValueId;
    }
}
