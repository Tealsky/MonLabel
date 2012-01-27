package com.ben.monlabel.web.publique;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ben.monlabel.domain.Commentaire;
import com.ben.monlabel.domain.Contribution;
import com.ben.monlabel.domain.Diffusion;
import com.ben.monlabel.domain.Etat;
import com.ben.monlabel.domain.MembreDuLabel;
import com.ben.monlabel.domain.Musique;
import com.ben.monlabel.domain.MusiqueVersion;
import com.ben.monlabel.domain.TypeContribution;

@RequestMapping("/public/versionsmusiques/**")
@Controller
public class VersionsController {
	
	@PersistenceContext
	transient EntityManager entityManager;
	
	@RequestMapping(value="commentairesversion/{idVersion}", method = RequestMethod.GET)
	 public String commentairesVersion(@PathVariable Long idVersion, Model uiModel, HttpServletRequest httpServletRequest){
		 
		String contextPath = httpServletRequest.getContextPath();
		 uiModel.addAttribute("contextPath", contextPath);
		 
		 MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
		 uiModel.addAttribute("musiqueversion", musiqueVersion);
		 
		 Commentaire commentaire = new Commentaire();
		 uiModel.addAttribute("nouveauCommentaire", commentaire);
		 
		 uiModel.addAttribute("path", contextPath + "/public/versionsmusiques/commentairesversion/" + idVersion);
		 
	 	return "public/versionsmusiques/commentairesversion";
	 }
	
	@RequestMapping(value="commentairesversion/{idVersion}", method = RequestMethod.POST)
	 public String nouveauCommentaireVersion(@PathVariable Long idVersion, @Valid Commentaire commentaire, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest){
		 
		if(commentaire.getTexte() == null){
			
		}
		
		MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
		
		Set<Commentaire> commentaires = musiqueVersion.getCommentaires();
		commentaire.setDateEdition(Calendar.getInstance().getTime());
		commentaires.add(commentaire);
		commentaire.setAvis(musiqueVersion);
		
		musiqueVersion.merge();
		
		return "redirect:/public/versionsmusiques/commentairesversion";
	 }
	

	 @RequestMapping(value="validationversion/{idMusique}/{idVersion}", method = RequestMethod.GET)
	 public String validationVersion(@PathVariable Long idMusique, @PathVariable Long idVersion, Model uiModel, HttpServletRequest httpServletRequest){
		 
		 Musique musique = (Musique)entityManager.find(Musique.class, idMusique);
		 uiModel.addAttribute("musique", musique);
		 
		 uiModel.addAttribute("contextPath", httpServletRequest.getContextPath());
		 
		 MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
		 uiModel.addAttribute("musiqueversion", musiqueVersion);
		 
		 uiModel.addAttribute("path", "public/versionsmusiques/validationversion/" + idMusique + "/" + idVersion);
		 
	 	return "public/versionsmusiques/validationversion";
	 }

    @RequestMapping(value="contributions/{idMusique}/{idVersion}", method = RequestMethod.GET)
    public String get(@PathVariable Long idMusique, @PathVariable Long idVersion, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	
    	Musique musique = (Musique)entityManager.find(Musique.class, idMusique);
    	
    	String titre = musique.getTitre();
 	   	if(titre != null){
 	   		modelMap.addAttribute("titreMusique", titre);
 	   	} else {
 		   modelMap.addAttribute("titreMusique", musique.getTitreProvisoire() + " (titre provisoire)");
 	   	}
 	   
    	MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
    	modelMap.addAttribute("musiqueversion", musiqueVersion);
    	
 	   	modelMap.addAttribute("render", true);
 	   	modelMap.addAttribute("id", 1);
	 	modelMap.addAttribute("title_msg", "Les contributeurs à la nouvelle version");
	 	modelMap.addAttribute("openPane", true);
	 	modelMap.addAttribute("path", "public/versionsmusiques/contributions/" + idMusique + "/" + idVersion);
	 	   
	 	/*List<TypeContribution> typeContributions = new ArrayList<TypeContribution>();
	 	typeContributions.add(TypeContribution.COMPOSITION);
	 	typeContributions.add(TypeContribution.ARRANGEMENT_ORCHESTRATION);
	 	typeContributions.add(TypeContribution.INTERPRETATION);
	 	typeContributions.add(TypeContribution.REALISATION);
	 	typeContributions.add(TypeContribution.MIXAGE);
	 	typeContributions.add(TypeContribution.MASTERISATION);
		modelMap.addAttribute("typecontributions", typeContributions);
    	*/
    	modelMap.addAttribute("nouvellecontribution", new Contribution());
  	
    	List membresDuLabel = entityManager.createQuery("select m from MembreDuLabel m").getResultList();
    	modelMap.addAttribute("membresDuLabel", membresDuLabel);
    	
    	return "public/versionsmusiques/contributions";
    }

    @RequestMapping(value="contributions/{idMusique}/{idVersion}", method = RequestMethod.POST)
    public String post(@PathVariable Long idMusique, @PathVariable Long idVersion, @Valid Contribution contribution, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest){
    	
    	String submit = httpServletRequest.getParameter("contributionsBouton");

		String nomMembre = httpServletRequest.getParameter("membre");
    	
    	MembreDuLabel membreDuLabel = (MembreDuLabel)entityManager.createQuery("select m from MembreDuLabel m where m.nomDArtiste like :nomDArtiste").setParameter("nomDArtiste", nomMembre).getSingleResult();
    	Set<Contribution> contributions = membreDuLabel.getContributions();
    	contributions.add(contribution);
    	membreDuLabel.setContributions(contributions);
    	contribution.setMembre(membreDuLabel);
    	
    	membreDuLabel.merge();
    	
    	MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
    	contributions = musiqueVersion.getContributions();
    	contributions.add(contribution);
    	musiqueVersion.setContributions(contributions);
    	contribution.setMusique(musiqueVersion);
    	
    	musiqueVersion.merge();
    	
    	if(submit.equals("Ajouter un autre contributeur à cette version...")){
	
        	return "redirect:/public/versionsmusiques/contributions/" + idMusique + "/" + idVersion;
        	
    	} else if(submit.equals("J'ai fini d'ajouter les contributions")){
    		
    		return "redirect:/public/versionsmusiques/contributions/" + idMusique + "/" + idVersion + "/copyright";
    		
    	}
    	
    	return null;
    	
    }
    
    @RequestMapping(value="contributions/{idMusique}/{idVersion}/copyright", method = RequestMethod.GET)
    public String getCopyright(@PathVariable Long idMusique, @PathVariable Long idVersion, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	
    	Musique musique = (Musique)entityManager.find(Musique.class, idMusique);
    	
    	String titre = musique.getTitre();
 	   	if(titre != null){
 	   		modelMap.addAttribute("titreMusique", titre);
 	   	} else {
 		   modelMap.addAttribute("titreMusique", musique.getTitreProvisoire() + " (titre provisoire)");
 	   	}
 	   
    	MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
    	
    	Version version = new Version();
    	
    	Contribution contribution;
    	ContributionVersion contributionVersion;
    	
    	Iterator<Contribution> contributions = musiqueVersion.getContributions().iterator();
    	while(contributions.hasNext()){
    		contribution = contributions.next();
    		contributionVersion = new ContributionVersion(contribution.getMembre().getNomDArtiste(), contribution.getTypeContribution(), contribution.getPourcentageDroit());
    		version.addContribution(contributionVersion);
    	}
    	
    	modelMap.addAttribute("musiqueversion", version);
    	
    	/*MusiqueVersion nouvelleMusiqueVersion = new MusiqueVersion();
    	Contribution contribution;
    	Contribution nouvelleContribution = null;
    	List<Contribution> nouvellesContributions = new ArrayList<Contribution>();
    	
    	Iterator<Contribution> contributions = musiqueVersion.getContributions().iterator();
    	while(contributions.hasNext()){
    		contribution = contributions.next();
    		nouvelleContribution = new Contribution();
    		nouvelleContribution.setPourcentageDroit(contribution.getPourcentageDroit());
    		nouvelleContribution.setTypeContribution(contribution.getTypeContribution());
    		nouvellesContributions.add(nouvelleContribution);
    		//System.out.println(.getPourcentageDroit());
    	}
    	
    	nouvelleMusiqueVersion.setContributions(nouvellesContributions);
    	
    	System.out.println("size=" + nouvelleMusiqueVersion.getContributions().size());*/
    	
    	//modelMap.addAttribute("musiqueversion", nouvelleMusiqueVersion);
    	
 	   	modelMap.addAttribute("render", true);
 	   	modelMap.addAttribute("id", 1);
	 	modelMap.addAttribute("title_msg", "Les contributeurs à la nouvelle version");
	 	modelMap.addAttribute("openPane", true);
	 	modelMap.addAttribute("path", "public/versionsmusiques/contributions/" + idMusique + "/" + idVersion + "/copyright");
	 	
    	return "public/versionsmusiques/copyright";
    	
    }
    
    @RequestMapping(value="contributions/{idMusique}/{idVersion}/copyright", method = RequestMethod.POST)
    public String postCopyright(@PathVariable Long idMusique, @PathVariable Long idVersion, @Valid Version version, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest){
    	
    	Musique musique = (Musique)entityManager.find(Musique.class, idMusique);
    	
    	MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
    	
    	Contribution contribution;
    	ContributionVersion contributionVersion;
    	
    	Iterator<Contribution> contributions = musiqueVersion.getContributions().iterator();
    	Iterator<ContributionVersion> majContributions = version.getContributions().iterator();
    	while(contributions.hasNext()){
    		contribution = contributions.next();
    		contributionVersion = majContributions.next();
    		
    		contribution.setPourcentageDroit(contributionVersion.getPourcentageDroit());
    		
    		contribution.merge();
    	}
    	    	
    	Set<MusiqueVersion> versions = musique.getPropositionsFuturesVersions();
    	versions.add(musiqueVersion);
    	
    	musique.setPropositionsFuturesVersions(versions);
    	musiqueVersion.setMusiqueEnCours(musique);
    	
    	musique.merge();
    	
    	//System.out.println("nb versions = " + musique.getPropositionsFuturesVersions().size());
    	
    	return "public/versionsmusiques/index";
    }
    
   

    @RequestMapping
    public String index() {
        return "public/versionsmusiques/index";
    }
    
    @ModelAttribute("typecontributions")
    public Collection<TypeContribution> populateTypeContributions() {
        return Arrays.asList(TypeContribution.class.getEnumConstants());
    }
    
    /*public void repartitionDesDroits(Musique musique, List<ContributionVersion> nouvelleContributions){
    	MusiqueVersion versionActuelle = musique.getVersionActuelle();
    	Iterator<Contribution> contributions = versionActuelle.getContributions().iterator();
    	Contribution contribution;
    	float montantDroitsFixe = 0;
    	while(contributions.hasNext()){
    		contribution = contributions.next();
    		if(contribution.getTypeReparitionDesDroits() == TypeReparitionDesDroits.PART_FIXE){
    			montantDroitsFixe = montantDroitsFixe + contribution.getPourcentageDroit();
    		}
    	}
    	float montantRestantAPartager = 100 - montantDroitsFixe;
    	
    	ContributionVersion contributionVersion;
    	for(int i=0; i<nouvelleContributions.size(); i++){
    		contributionVersion = nouvelleContributions.get(i);
    		contributionVersion.
    	}
    }*/
    
}
