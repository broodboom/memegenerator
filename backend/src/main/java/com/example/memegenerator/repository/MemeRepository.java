package com.example.memegenerator.repository;

import com.example.memegenerator.domain.Meme;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends CrudRepository<Meme, Long> {
}
