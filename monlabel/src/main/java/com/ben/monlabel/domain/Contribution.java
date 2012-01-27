package com.ben.monlabel.domain;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Contribution {

    @NotNull
    @Enumerated
    private TypeContribution typeContribution;

    @NotNull
    @Value("0")
    @Min(0L)
    @Max(100L)
    private float pourcentageDroit;

    private String instrument;

    @ManyToOne
    private MusiqueVersion musique;

    @ManyToOne
    private MembreDuLabel membre;
}
