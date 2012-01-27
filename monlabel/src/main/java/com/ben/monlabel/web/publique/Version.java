package com.ben.monlabel.web.publique;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import com.ben.monlabel.domain.Contribution;

public class Version {
	
	List<ContributionVersion> contributions = new ArrayList<ContributionVersion>();
	
	public void addContribution(ContributionVersion contribution){
		contributions.add(contribution);
	}

	public List<ContributionVersion> getContributions() {
		return contributions;
	}

	public void setContributions(List<ContributionVersion> contributions) {
		this.contributions = contributions;
	}
	
}
