package com.example.assignment2.infrastructure;

import com.example.assignment2.domain.Rekeninghouder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RekeninghouderRepository extends JpaRepository<Rekeninghouder, Integer> {
}
