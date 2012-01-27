package com.ben.monlabel.web.publique;

import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Value;

import com.ben.monlabel.domain.TypeContribution;

public class ContributionVersion {

	private String nomDArtiste;
	
    @NotNull
    @Enumerated
    private TypeContribution typeContribution;

    @NotNull
    @Value("0")
    @Min(0L)
    @Max(100L)
    private float pourcentageDroit;
    
    public ContributionVersion() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ContributionVersion(String nomDArtiste, TypeContribution typeContribution, float pourcentageDroit) {
		super();
		this.nomDArtiste = nomDArtiste;
		this.typeContribution = typeContribution;
		this.pourcentageDroit = pourcentageDroit;
	}

	public String getNomDArtiste() {
		return nomDArtiste;
	}

	public void setNomDArtiste(String nomDArtiste) {
		this.nomDArtiste = nomDArtiste;
	}

	public TypeContribution getTypeContribution() {
		return typeContribution;
	}

	public void setTypeContribution(TypeContribution typeContribution) {
		this.typeContribution = typeContribution;
	}

	public float getPourcentageDroit() {
		return pourcentageDroit;
	}

	public void setPourcentageDroit(float pourcentageDroit) {
		this.pourcentageDroit = pourcentageDroit;
	}
    
}
