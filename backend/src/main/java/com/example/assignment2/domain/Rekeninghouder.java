package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Rekeninghouder extends BaseEntity {

    @ManyToMany(mappedBy = "rekeninghouders", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    @ElementCollection(targetClass = Rekening.class)
    public Set<Rekening> rekeningen = new HashSet<Rekening>();
    @Column(name = "voornaam", nullable = false)
    @Getter
    @Setter
    @NotNull
    @Size(message = "Minimaal 1 karakter, maximaal 50", min = 1, max = 50)
    private String voornaam;
    @Column(name = "achternaam", nullable = false)
    @Getter
    @Setter
    @Size(message = "Minimaal 1 karakter, maximaal 50", min = 1, max = 50)
    private String achternaam;
    @Column(name = "geslacht", nullable = false)
    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    private Geslacht geslacht;
    @Column(name = "adres", nullable = false)
    @Getter
    @Setter
    @Embedded
    private Adres adres;
}
