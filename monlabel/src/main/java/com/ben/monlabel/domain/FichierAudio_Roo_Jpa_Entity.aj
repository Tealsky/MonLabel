// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ben.monlabel.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

privileged aspect FichierAudio_Roo_Jpa_Entity {
    
    declare @type: FichierAudio: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private java.lang.Long FichierAudio.id;
    
    @Version
    @Column(name = "version")
    private java.lang.Integer FichierAudio.version;
    
    public java.lang.Long FichierAudio.getId() {
        return this.id;
    }
    
    public void FichierAudio.setId(java.lang.Long id) {
        this.id = id;
    }
    
    public java.lang.Integer FichierAudio.getVersion() {
        return this.version;
    }
    
    public void FichierAudio.setVersion(java.lang.Integer version) {
        this.version = version;
    }
    
}
