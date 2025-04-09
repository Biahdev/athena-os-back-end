package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.product.ProductCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.product.ProductDetailDTO;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import dev.abeatriz.athena_os.entity.enums.ProductStatus;
import dev.abeatriz.athena_os.mapper.CategoryMapper;
import dev.abeatriz.athena_os.mapper.ProductMapper;
import dev.abeatriz.athena_os.repository.CategoryRepository;
import dev.abeatriz.athena_os.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper mapper;
    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional()
    public ProductDetailDTO create(ProductCreateUpdateDTO createDTO) {
        var category = categoryRepository.findByCategoryIdAndStatus(createDTO.categoryId(), CategoryStatus.ATIVO).orElseThrow(() -> new EntityNotFoundException("Categoria selecionada esta inativa"));
        var produto = mapper.toEntity(createDTO);
        produto.setCategory(category);

        // TODO: Melhorar isso
        if (produto.getOptions() != null) {
            produto.getOptions().forEach(option -> {
                option.setProduct(produto);
                if (option.getValues() != null) {
                    option.getValues().forEach(value -> {
                        value.setOption(option);
                    });
                }
            });
        }

        var savedProduto = repository.save(produto);
        return mapper.toDTO(savedProduto);
    }

    @Transactional(readOnly = true)
    public List<ProductDetailDTO> listAll() {
        var products = repository.findAll();
        return mapper.toDTO(products);
    }

    @Transactional(readOnly = true)
    public ProductDetailDTO listById(Long id) {
        var product = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        return mapper.toDTO(product);
    }

    @Transactional(readOnly = true)
    public List<String> listProductStatus() {
        return Arrays
            .stream(ProductStatus.values())
            .map(Enum::name)
            .collect(Collectors.toList());
    }

    @Transactional
    public ProductDetailDTO update(ProductCreateUpdateDTO updateDTO, Long id) {
        var existingProduto = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        var category = categoryRepository.findByCategoryIdAndStatus(updateDTO.categoryId(), CategoryStatus.ATIVO).orElseThrow(EntityNotFoundException::new);

        var updatedProduto = mapper.toEntity(updateDTO);
        updatedProduto.setProductId(existingProduto.getProductId());
        updatedProduto.setCategory(category);

        updatedProduto.getOptions().forEach(option -> {
            option.setProduct(updatedProduto);
            if (option.getValues() != null) {
                option.getValues().forEach(value -> {
                    value.setOption(option);
                });
            }
        });

        var savedProduto = repository.save(updatedProduto);
        return mapper.toDTO(savedProduto);
    }

    @Transactional()
    public ProductDetailDTO disable(Long id) {
        var productEntity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        productEntity.disable();
        return mapper.toDTO(productEntity);
    }

    @Transactional
    public void delete(Long id) {
        var productEntity = repository.findById(id).orElseThrow(EntityNotFoundException::new);
        repository.delete(productEntity);
    }
}
