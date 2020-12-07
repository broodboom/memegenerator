package com.example.memegenerator.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    
    /**
     *
     */
    private static final long serialVersionUID = -1957642603302635457L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", unique = true, nullable = false)
    @Getter
    @Setter
    public Long id;

    @CreationTimestamp
    @Column(name = "createdat", unique = false, nullable = false)
    @Getter
    @Setter
    public Timestamp createdat;
}
