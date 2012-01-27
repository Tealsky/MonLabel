package com.ben.monlabel.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class MusiqueVersion {

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateEdition;

    @Size(max = 256)
    private String description;

    @Enumerated
    private Diffusion diffusion;

    @ManyToOne
    private Musique musiqueEnCours;

    @OneToOne
    private Musique musiqueActuelle;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musique")
    private Set<Contribution> contributions = new HashSet<Contribution>();

    @OneToOne
    private ProjetCubase projetCubase;

    @OneToOne(cascade = CascadeType.ALL)
    private FichierAudio fichierAudio;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "avis")
    private Set<Commentaire> commentaires = new HashSet<Commentaire>();
}
