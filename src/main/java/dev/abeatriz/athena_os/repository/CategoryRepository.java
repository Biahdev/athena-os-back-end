package dev.abeatriz.athena_os.repository;

import dev.abeatriz.athena_os.dto.category.CategoryWithCountProduct;
import dev.abeatriz.athena_os.entity.Category;
import dev.abeatriz.athena_os.entity.enums.CategoryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByCategoryIdAndStatus(Long categoryId, CategoryStatus status);

    @Query("SELECT new dev.abeatriz.athena_os.dto.category.CategoryWithCountProduct(c.categoryId, c.name, c.status, COUNT(p)) " +
        "FROM Category c LEFT JOIN c.products p " +
        "WHERE c.status = 'ATIVO'" +
        "GROUP BY c.categoryId, c.name, c.status, c.createdAt, c.updatedAt ORDER BY COUNT(p) DESC ")
    List<CategoryWithCountProduct> findAllWithProductCount();

}
