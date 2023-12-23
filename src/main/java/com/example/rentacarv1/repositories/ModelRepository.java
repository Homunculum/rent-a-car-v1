package com.example.rentacarv1.repositories;

import com.example.rentacarv1.Entities.Model;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModelRepository extends JpaRepository<Model,Integer> {

    boolean existsByName(String colorName);
}
