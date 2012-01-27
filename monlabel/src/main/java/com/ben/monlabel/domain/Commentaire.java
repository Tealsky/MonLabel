package com.ben.monlabel.domain;

import java.util.Date;
import javax.persistence.ManyToOne;
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
public class Commentaire {

    @NotNull
    @Size(max = 255)
    private String texte;

    @ManyToOne
    private MembreDuLabel auteur;

    @ManyToOne
    private MusiqueVersion avis;

    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    //@DateTimeFormat(style = "M-")
    @DateTimeFormat(pattern = "'Le' dd/MM/yyyy 'à' HH:mm:ss")
    private Date dateEdition;
}
