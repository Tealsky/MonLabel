// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ben.monlabel.domain;

import com.ben.monlabel.domain.FichierAudio;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;

privileged aspect FichierAudio_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager FichierAudio.entityManager;
    
    @Transactional
    public void FichierAudio.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void FichierAudio.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            FichierAudio attached = FichierAudio.findFichierAudio(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void FichierAudio.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void FichierAudio.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public FichierAudio FichierAudio.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        FichierAudio merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
    public static final EntityManager FichierAudio.entityManager() {
        EntityManager em = new FichierAudio().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long FichierAudio.countFichierAudios() {
        return entityManager().createQuery("SELECT COUNT(o) FROM FichierAudio o", Long.class).getSingleResult();
    }
    
    public static List<FichierAudio> FichierAudio.findAllFichierAudios() {
        return entityManager().createQuery("SELECT o FROM FichierAudio o", FichierAudio.class).getResultList();
    }
    
    public static FichierAudio FichierAudio.findFichierAudio(java.lang.Long id) {
        if (id == null) return null;
        return entityManager().find(FichierAudio.class, id);
    }
    
    public static List<FichierAudio> FichierAudio.findFichierAudioEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM FichierAudio o", FichierAudio.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
}
