package com.ben.monlabel.domain;

import java.util.Date;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.roo.classpath.operations.jsr303.RooUploadedFile;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class FichierAudio {

    @RooUploadedFile(contentType = "audio/mpeg", autoUpload = true)
    @Lob
    private byte[] fichierMP3;

    @Enumerated
    private TypeAudio typeAudio;

    private float duree;

    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date dateCreation;

    private String copyright;

    private String titreMusique;
}
