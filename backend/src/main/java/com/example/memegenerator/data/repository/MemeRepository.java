package com.example.memegenerator.data.repository;

import com.example.memegenerator.data.entity.Meme;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends JpaRepository<Meme, Long> {
}
