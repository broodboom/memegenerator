package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Rekening extends BaseEntity {

    @ManyToMany(mappedBy = "rekeningen", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @ElementCollection(targetClass = Rekeninghouder.class)
    public Set<Rekeninghouder> rekeninghouders = new HashSet<Rekeninghouder>();
    @Column(name = "iban", unique = true, nullable = false)
    @Getter
    @Setter
    @NotNull
    private String iban;
    @Column(name = "saldo", nullable = false)
    @Getter
    @Setter
    @Max(message = "Bedrag te hoog, wij bedienen geen rijke tata's", value = 20000)
    private double saldo;
    @Column(name = "geblokkeerd", nullable = false)
    @Getter
    @Setter
    private boolean geblokkeerd;
    @Column(name = "rekeningnummer", nullable = false)
    @Getter
    @Setter
    private int rekeningnummer;
}
