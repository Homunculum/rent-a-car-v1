package com.example.rentacarv1.repositories;

import com.example.rentacarv1.Entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand,Integer> {
    boolean existsByName(String brandName);
}
