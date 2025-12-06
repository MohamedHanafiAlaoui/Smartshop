package com.example.SmartShop.repository;

import com.example.SmartShop.model.entitie.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {
    @Query("""
        SELECT p FROM Product p
        WHERE (:search IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :search, '%')))
        AND (:minPrice IS NULL OR p.prixUnitaireHT >= :minPrice)
        AND (:maxPrice IS NULL OR p.prixUnitaireHT <= :maxPrice)
    """)
    Page<Product> findByFilters(
            @Param("search") String search,
            @Param("minPrice") Double minPrice,
            @Param("maxPrice") Double maxPrice,
            Pageable pageable
    );
}
