package com.example.memegenerator.repository;

import com.example.memegenerator.domain.Meme;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MemeRepository extends CrudRepository<Meme, Long> {
    @Query(value = "SELECT Count(*) FROM `meme` WHERE DATE(`createdat`) = :currentDate and `userid` = :userId", nativeQuery = true)
    Integer countAddedRecordsTodayByUser(@Param("currentDate") String currentDate, @Param("userId") Long userId);
}
