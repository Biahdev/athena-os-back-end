package dev.abeatriz.athena_os.config;

import com.github.javafaker.Faker;
import dev.abeatriz.athena_os.dto.product.OptionDTO;
import dev.abeatriz.athena_os.dto.product.OptionValueDTO;
import dev.abeatriz.athena_os.dto.product.ProductCreateUpdateDTO;
import dev.abeatriz.athena_os.entity.*;
import dev.abeatriz.athena_os.entity.enums.*;
import dev.abeatriz.athena_os.mapper.ProductMapper;
import dev.abeatriz.athena_os.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.logging.Logger;


@Component
public class Seeds implements CommandLineRunner {

    @Value("${jwt.secret.admin}")
    private String jwtSecretAdmin;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private ProductMapper mapper = ProductMapper.INSTANCE;

    private static final Logger LOGGER = Logger.getLogger(Seeds.class.getName());
    private static final Faker faker = new Faker(Locale.forLanguageTag("pt-br"));
    private static final Random random = new Random();
    private List<OptionValueDTO> optionsValue = new ArrayList<>();
    private List<OptionDTO> options;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) {
        if (true) {
            createUsers();
            createProductOptionsValues();

            for (int i = 0; i < 5; i++) {
                var client = this.createClient();
                this.createCategory();
                var product = this.createProducts();
                var employee = this.createEmployees();

                this.createOrder(client, employee, product);
            }

            LOGGER.info("Seeds Completo");
        }
    }

    private void createCategory() {
        var category = new Category();
        category.setName(faker.commerce().department());
        category.setStatus(CategoryStatus.ATIVO);
        categoryRepository.save(category);
    }

    private Client createClient() {
        var client = new Client();
        client.setName(faker.name().fullName());
        client.setAddress(faker.address().fullAddress());
        client.setStatus(randomStatusClient());
        client.setInstagram(faker.name().fullName().toLowerCase().replace(".", "").replace(" ", "_"));
        client.setPhone(randomPhone());
        client.setWhatsapp(faker.random().nextBoolean());
        return clientRepository.save(client);
    }

    private Employee createEmployees() {
        var user = new User();
        user.setEmail(faker.internet().emailAddress());
        user.setPassword(passwordEncoder.encode(user.getEmail()));
        user.setRole(UserRole.ADMIN);
        user.setStatus(UserStatus.ATIVO);

        var employee = new Employee();
        employee.setName(faker.name().fullName());
        employee.setStatus(randomStatusEmployee());
        employee.setPosition(randomPositionEmployee());
        employee.setNote(faker.lorem().paragraph(1));

        employee.setUser(user);
        user.setEmployee(employee);

        return employeeRepository.save(employee);
    }

    private Product createProducts() {
        createProductOptionsValues();
        createProductOptions();
        var costValue = getBigDecimal(10.00, 100.00);
        var salesValue = getBigDecimal(100.00, 500.00);
        var createDTO = new ProductCreateUpdateDTO(
                1L,
                faker.commerce().productName(),
                faker.commerce().department() + " " + faker.lorem().characters(10, 20),
                randomProductStatus().toString(),
                costValue,
                salesValue,
                this.options
        );

        var produtoEntity = mapper.toEntity(createDTO);

        if (produtoEntity.getOptions() != null) {
            produtoEntity.getOptions().forEach(option -> {
                option.setProduct(produtoEntity);
                if (option.getValues() != null) {
                    option.getValues().forEach(value -> {
                        value.setOption(option);
                    });
                }
            });
        }
        return productRepository.save(produtoEntity);
    }

    private void createProductOptions() {
        this.options = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            var option = new OptionDTO(faker.commerce().material(), randomOptionType(), this.optionsValue);
            this.options.add(option);
        }
    }

    private void createProductOptionsValues() {
        this.optionsValue = new ArrayList<>();
        for (int i = 0; i < faker.random().nextInt(1, 4); i++) {
            var price = getBigDecimal(0.0, 10.0);
            var optionValue = new OptionValueDTO(faker.commerce().color(), price);
            this.optionsValue.add(optionValue);
        }
    }

    private void createOrder(Client client, Employee employee, Product product) {
        Order order = new Order();
        order.setClient(client);
        order.setEmployee(employee);
        order.setDeliveryDate(LocalDate.of(faker.random().nextInt(2024, 2025), faker.random().nextInt(1, 12), faker.random().nextInt(1, 28)));
        order.setDeliveryType(randomOrderDeliveredType());
        order.setStatus(randomOrderStatus());
        order.setQuantity(2L);
        order.setDiscountOrder(new BigDecimal("24.25"));
        order.setDiscountOrderProducts(new BigDecimal("24.25"));
        order.setDiscountTotal(new BigDecimal("24.25"));
        order.setInitialTotal(new BigDecimal("175.00"));
        order.setFinalTotal(new BigDecimal("150.75"));
        order.setNote("Urgent order, please prioritize.");

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setOrder(order);
        orderProduct.setProduct(product);
        orderProduct.setQuantity(2L);
        orderProduct.setDiscount(new BigDecimal("10.00"));
        orderProduct.setExtraPrice(new BigDecimal("10.00"));
        orderProduct.setInitialUnit(new BigDecimal("10.00"));
        orderProduct.setFinalUnit(new BigDecimal("10.00"));
        orderProduct.setInitialTotal(new BigDecimal("100.00"));
        orderProduct.setFinalTotal(new BigDecimal("10.00"));

        OrderProductOption orderProductOption = new OrderProductOption();
        orderProductOption.setOrderProduct(orderProduct);
        orderProductOption.setOption(product.getOptions().getFirst());
        orderProductOption.setTitle(product.getOptions().getFirst().getTitle());

        OrderProductOptionValue orderProductOptionValue1 = new OrderProductOptionValue();
        orderProductOptionValue1.setOrderProductOption(orderProductOption);
        orderProductOptionValue1.setOptionValue(product.getOptions().getFirst().getValues().getFirst());
        orderProductOptionValue1.setName(product.getOptions().getFirst().getValues().getFirst().getName());
        orderProductOptionValue1.setPrice(product.getOptions().getFirst().getValues().getFirst().getPrice());

        orderProductOption.setOrderProductOptionValues(List.of(orderProductOptionValue1));
        orderProduct.setOrderProductOptions(List.of(orderProductOption));
        order.setOrderProducts(List.of(orderProduct));

        this.orderRepository.save(order);
    }

    private void createUsers() {
        var password = "$2y$10$pW5ZZDGqhBl8f90l79CwfOpYz2mJMU7LBJ30t5MR3F8DLDbtUsS/y";
        var emailAdminFind = userRepository.findByEmail("admin@admin.com.br");
        if (emailAdminFind.isEmpty()) {
            var user = new User();
            user.setEmail("admin@admin.com.br");
            user.setPassword(password);
            user.setRole(UserRole.ADMIN);
            user.setStatus(UserStatus.ATIVO);
            userRepository.save(user);
        }
    }

    private static BigDecimal getBigDecimal(Double min, Double max) {
        return new BigDecimal(faker.commerce().price(min, max).replace(",", "."));
    }

    private static String randomPhone() {
        var tipo1 = "55##9########";
        var tipo2 = "55##########";
        if (random.nextInt(1, 10) > 5) {
            return faker.numerify(tipo1);
        } else {
            return faker.numerify(tipo2);
        }
    }

    private static ClientStatus randomStatusClient() {
        ClientStatus[] statusValue = ClientStatus.values();
        return statusValue[random.nextInt(statusValue.length)];
    }

    private static EmployeeStatus randomStatusEmployee() {
        EmployeeStatus[] statusValue = EmployeeStatus.values();
        return statusValue[random.nextInt(statusValue.length)];
    }

    private static EmployeePosition randomPositionEmployee() {
        EmployeePosition[] statusValue = EmployeePosition.values();
        return statusValue[random.nextInt(statusValue.length)];
    }

    private static CategoryStatus randomCategoryStatus() {
        CategoryStatus[] statusValue = CategoryStatus.values();
        return statusValue[random.nextInt(statusValue.length)];
    }

    private static ProductStatus randomProductStatus() {
        ProductStatus[] statusValue = ProductStatus.values();
        return statusValue[random.nextInt(statusValue.length)];
    }

    private static OptionType randomOptionType() {
        OptionType[] statusValue = OptionType.values();
        return statusValue[random.nextInt(statusValue.length)];
    }

    private static OrderStatus randomOrderStatus() {
        OrderStatus[] statusValue = OrderStatus.values();
        return statusValue[random.nextInt(statusValue.length)];
    }

    private static OrderDeliveryType randomOrderDeliveredType() {
        OrderDeliveryType[] statusValue = OrderDeliveryType.values();
        return statusValue[random.nextInt(statusValue.length)];
    }


}
