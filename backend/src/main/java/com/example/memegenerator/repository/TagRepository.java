package com.example.memegenerator.repository;

import com.example.memegenerator.domain.Tag;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends CrudRepository<Tag, Long> {
}