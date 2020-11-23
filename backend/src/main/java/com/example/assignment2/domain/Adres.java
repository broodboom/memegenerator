package com.example.assignment2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class Adres {
    @Column(name = "straat", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String straat;

    @Column(name = "huisnummer", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String huisnummer;

    @Column(name = "postcode", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String postcode;

    @Column(name = "plaats", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String plaats;

    @Column(name = "land", nullable = false)
    @Getter
    @Setter
    @NotNull
    private String land;
}
