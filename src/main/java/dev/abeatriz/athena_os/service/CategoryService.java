package dev.abeatriz.athena_os.service;

import dev.abeatriz.athena_os.dto.category.CategoryCreateUpdateDTO;
import dev.abeatriz.athena_os.dto.category.CategoryDetailDTO;
import dev.abeatriz.athena_os.dto.category.CategoryWithCountProduct;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import dev.abeatriz.athena_os.mapper.CategoryMapper;
import dev.abeatriz.athena_os.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional()
    public CategoryDetailDTO create(CategoryCreateUpdateDTO createDTO) {
        var clientEntity = categoryMapper.toEntity(createDTO);
        clientEntity = categoryRepository.save(clientEntity);
        return categoryMapper.toDTO(clientEntity);
    }

    @Transactional(readOnly = true)
    public List<CategoryDetailDTO> listAll() {
        var clientEntity = categoryRepository.findAll();
        return categoryMapper.toDTO(clientEntity);
    }

    @Transactional(readOnly = true)
    public CategoryDetailDTO listById(Long id) {
        var clientEntity = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        return categoryMapper.toDTO(clientEntity);
    }

    @Transactional(readOnly = true)
    public List<CategoryWithCountProduct> listAllWithProductCount() {
        return categoryRepository.findAllWithProductCount();
    }

    @Transactional()
    public CategoryDetailDTO update(CategoryCreateUpdateDTO updateDTO, Long id) {
        var clientEntity = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        clientEntity.update(updateDTO);
        return categoryMapper.toDTO(clientEntity);
    }

    @Transactional()
    public CategoryDetailDTO disable(Long id) {
        var clientEntity = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        clientEntity.disable();
        return categoryMapper.toDTO(clientEntity);
    }

    @Transactional()
    public void delete(Long id) {
        var clientEntity = categoryRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        categoryRepository.delete(clientEntity);
    }

    @Transactional(readOnly = true)
    public List<String> listCategoryStatus() {
        return Arrays.stream(CategoryStatus.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

}
