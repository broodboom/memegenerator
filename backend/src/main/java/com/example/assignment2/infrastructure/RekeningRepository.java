package com.example.assignment2.infrastructure;

import com.example.assignment2.domain.Rekening;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RekeningRepository extends JpaRepository<Rekening, Integer> {
}
