package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    private Integer id;

    @CreationTimestamp
    @Column(name = "aangemaakt", unique = false, nullable = false)
    @Getter
    @Setter
    private Timestamp aangemaakt;
}
