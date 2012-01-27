package com.ben.monlabel.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Enumerated;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Musique {

    @NotNull
    @Column(unique = true)
    private String titre;

    private String titreProvisoire;

    @Enumerated
    private Etat etat;

    @Enumerated
    private Finitude finie;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musiqueEnCours")
    private Set<MusiqueVersion> propositionsFuturesVersions = new HashSet<MusiqueVersion>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "musiqueActuelle")
    private MusiqueVersion versionActuelle;

    @Size(max = 255)
    private String objectifProchaineVersion;
}
