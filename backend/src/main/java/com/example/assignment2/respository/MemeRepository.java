package com.example.assignment2.respository;

import com.example.assignment2.domain.Meme;
import com.example.assignment2.domain.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends CrudRepository<Meme, Long> {
}
