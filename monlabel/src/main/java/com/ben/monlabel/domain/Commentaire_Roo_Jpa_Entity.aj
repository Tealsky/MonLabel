// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ben.monlabel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect Commentaire_Roo_Jpa_Entity {
    
    declare @type: Commentaire: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private java.lang.Long Commentaire.id;
    
    @Version
    @Column(name = "version")
    private java.lang.Integer Commentaire.version;
    
    public java.lang.Long Commentaire.getId() {
        return this.id;
    }
    
    public void Commentaire.setId(java.lang.Long id) {
        this.id = id;
    }
    
    public java.lang.Integer Commentaire.getVersion() {
        return this.version;
    }
    
    public void Commentaire.setVersion(java.lang.Integer version) {
        this.version = version;
    }
    
}
