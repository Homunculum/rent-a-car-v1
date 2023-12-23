package com.example.rentacarv1.repositories;

import com.example.rentacarv1.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer> {
}
