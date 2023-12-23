package com.example.rentacarv1.repositories;

import com.example.rentacarv1.Entities.Rental;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RentalRepository extends JpaRepository<Rental,Integer> {
}
