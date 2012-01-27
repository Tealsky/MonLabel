package com.ben.monlabel.domain;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class MembreDuLabel extends Invite {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "membre")
    private Set<Contribution> contributions = new HashSet<Contribution>();

    @NotNull
    @Column(unique = true)
    private String nomDArtiste;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "auteur")
    private Set<Commentaire> commentaires = new HashSet<Commentaire>();
}
