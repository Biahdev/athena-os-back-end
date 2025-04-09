package dev.abeatriz.athena_os.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderProductOptionCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.createUpdate.OrderProductOptionValueCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.order.detail.OrderDetailDTO;
import dev.abeatriz.athena_os.entity.Order;
import dev.abeatriz.athena_os.entity.enums.OrderDeliveryType;
import dev.abeatriz.athena_os.entity.enums.OrderStatus;
import dev.abeatriz.athena_os.mapper.OrderMapper;
import dev.abeatriz.athena_os.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(OrderController.class)
public class OrderControllerTest {

    @MockitoBean
    private OrderService service;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderMapper mapper = OrderMapper.INSTANCE;

    private OrderCreateUpdateDTO orderCreatUpdateDTO;
    private OrderDetailDTO orderDetailDTO;
    private Order orderEntity;

    @BeforeEach
    void setup() {
        var orderProductOptionValue = new OrderProductOptionValueCreateUpdateDTO(1L);
        var orderProductOptions = new OrderProductOptionCreateUpdateDTO(1L, List.of(orderProductOptionValue));
        var orderProduct = new OrderProductCreateUpdateDTO(1L, 1L, new BigDecimal(5), new BigDecimal(0), new BigDecimal(100), new BigDecimal(95), new BigDecimal(100), new BigDecimal(95), List.of(orderProductOptions));
        orderCreatUpdateDTO = new OrderCreateUpdateDTO(
            1L,
            1L,
            OrderStatus.CANCELADO.toString(),
            LocalDate.of(2024, 12, 20),
            OrderDeliveryType.BUSCAR.toString(),
            2L,
            new BigDecimal(5),
            new BigDecimal(5),
            new BigDecimal(10),
            new BigDecimal(100),
            new BigDecimal(90),
            "Uma anotação",
            List.of(orderProduct)
        );


        orderEntity = mapper.toEntity(orderCreatUpdateDTO);
        orderDetailDTO = mapper.toDTO(orderEntity);
    }

    @Test
    @DisplayName("Create Success")
    void givenOrder_whenCreate_thenReturnDetailOrder() throws Exception {
        //Given
        var url = "/orders";
        given(service.create(any(OrderCreateUpdateDTO.class))).willReturn(orderDetailDTO);

        // When
        var response = mockMvc.perform(post(url)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(orderCreatUpdateDTO)));


        //Then
        response
            .andDo(print())
            .andExpectAll(
                status().isCreated(),
                jsonPath("$.client['clientId']", is(1)),
                jsonPath("$.employee['employeeId']", is(1)),
                jsonPath("$.status", is(OrderStatus.CANCELADO.toString())),
                jsonPath("$.deliveryDate", is(LocalDate.of(2024, 12, 20).toString())),
                jsonPath("$.deliveryType", is(OrderDeliveryType.BUSCAR.toString())),
                jsonPath("$.quantity", is(2)),
                jsonPath("$.discountOrder", is(new BigDecimal(5).intValue())),
                jsonPath("$.discountOrderProducts", is(new BigDecimal(5).intValue())),
                jsonPath("$.discountTotal", is(new BigDecimal(10).intValue())),
                jsonPath("$.initialTotal", is(new BigDecimal(100).intValue())),
                jsonPath("$.finalTotal", is(new BigDecimal(90).intValue())),
                jsonPath("$.note", is("Uma anotação"))
            );
    }
    /*
    @Test
    @DisplayName("List Sucess")
    void givenGetRequest_whenListAll_thenReturnDetailOrder() throws Exception {
        // Given
        var url = "/orders";
        given(service.listAll()).willReturn(List.of(productDetail));

        // When
        var response = mockMvc.perform(get(url));

        // Then
        response
            .andDo(print())
            .andExpectAll(status().isOk(),
                jsonPath("$[0].categoryName", is(productDetail.categoryName())),
                jsonPath("$[0].name", is(productDetail.name())),
                jsonPath("$[0].description", is(productDetail.description())),
                jsonPath("$[0].status", is(productDetail.status().name())),
                jsonPath("$[0].costValue", is(productDetail.costValue().doubleValue())),
                jsonPath("$[0].salesValue", is(productDetail.salesValue().doubleValue())),
                jsonPath("$[0].options[0].title", is(options.title())),
                jsonPath("$[0].options[0].type", is(options.type().toString())),
                jsonPath("$[0].options[0].values[0].name", is(optionValue.name())),
                jsonPath("$[0].options[0].values[0].price", is(optionValue.price().doubleValue()))
            );
    }

    */


    /*
`    {
  "clientId": 1,
  "employeeId": 1,
  "status": "PENDENTE",
  "deliveryDate": "2024-09-17",
  "deliveryType": "BUSCAR",
  "quantity": 1,
  "discountOrder": 10,
  "discountOrderProducts": 10,
  "discountTotal": 20,
  "initialTotal": 100,
  "finalTotal": 120,
  "note": "Propriae eum graece accusata massa elit dicit appetere saepe aliquip ",
  "products": [
    {
      "productId": 0,
      "quantity": 0,
      "discount": 0,
      "extraPrice": 0,
      "initialUnit": 0,
      "finalUnit": 0,
      "initialTotal": 0,
      "finalTotal": 0,
      "options": [
        {
          "optionId": 0,
          "values": [
            {
              "valueId": 0
            }
          ]
        }
      ]
    }
  ]
}`







    /*
     */

    /*
    @Test
    @DisplayName("List Sucess")
    void givenGetRequest_whenListAll_thenReturnDetailOrder() throws Exception {
        // Given
        var url = "/orders";
        given(service.listAll()).willReturn(List.of(productDetail));

        // When
        var response = mockMvc.perform(get(url));

        // Then
        response
            .andDo(print())
            .andExpectAll(status().isOk(),
                jsonPath("$[0].categoryName", is(productDetail.categoryName())),
                jsonPath("$[0].name", is(productDetail.name())),
                jsonPath("$[0].description", is(productDetail.description())),
                jsonPath("$[0].status", is(productDetail.status().name())),
                jsonPath("$[0].costValue", is(productDetail.costValue().doubleValue())),
                jsonPath("$[0].salesValue", is(productDetail.salesValue().doubleValue())),
                jsonPath("$[0].options[0].title", is(options.title())),
                jsonPath("$[0].options[0].type", is(options.type().toString())),
                jsonPath("$[0].options[0].values[0].name", is(optionValue.name())),
                jsonPath("$[0].options[0].values[0].price", is(optionValue.price().doubleValue()))
            );
    }

    @Test
    @DisplayName("Get By Id Sucess")
    void givenProductId_whenGetById_thenReturnDetailProduct() throws Exception {
        // Given
        var id = 1L;
        var url = "/products/{id}";
        given(service.listById(id)).willReturn(productDetail);

        // When
        var response = mockMvc.perform(get(url, id));

        // Then
        response
            .andDo(print())
            .andExpectAll(
                status().isOk(),
                jsonPath("$.categoryName", is(productDetail.categoryName())),
                jsonPath("$.name", is(productDetail.name())),
                jsonPath("$.description", is(productDetail.description())),
                jsonPath("$.status", is(productDetail.status().name())),
                jsonPath("$.costValue", is(productDetail.costValue().doubleValue())),
                jsonPath("$.salesValue", is(productDetail.salesValue().doubleValue())),
                jsonPath("$.options[0].title", is(options.title())),
                jsonPath("$.options[0].type", is(options.type().toString())),
                jsonPath("$.options[0].values[0].name", is(optionValue.name())),
                jsonPath("$.options[0].values[0].price", is(optionValue.price().doubleValue()))
            );

    }
    */


}
