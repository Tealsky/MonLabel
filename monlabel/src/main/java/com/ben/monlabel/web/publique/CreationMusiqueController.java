package com.ben.monlabel.web.publique;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ben.monlabel.domain.Diffusion;
import com.ben.monlabel.domain.Etat;
import com.ben.monlabel.domain.FichierAudio;
import com.ben.monlabel.domain.Finitude;
import com.ben.monlabel.domain.Musique;
import com.ben.monlabel.domain.MusiqueVersion;
import com.ben.monlabel.domain.TypeAudio;

@RequestMapping("/public/creationmusique/**")
@Controller
public class CreationMusiqueController {
	
	@PersistenceContext
	 transient EntityManager entityManager;

	@RequestMapping(value="/index", method = RequestMethod.GET)
    public String get(Model modelMap, HttpServletRequest request, HttpServletResponse response) {
    	Musique musique = new Musique();
    	MusiqueVersion musiqueVersion = new MusiqueVersion();
    	musiqueVersion.setMusiqueActuelle(musique);
 	   	musique.setVersionActuelle(musiqueVersion);
    	addDateTimeFormatPatterns(modelMap);
    	modelMap.addAttribute("musique", musique);
    	modelMap.addAttribute("path", "public/creationmusique");
    	return "public/creationmusique/index";
    }

    @RequestMapping(method = RequestMethod.POST)
    public String post(@Valid Musique musique, BindingResult bindingResult, ModelMap modelMap, @RequestParam("file") MultipartFile file, HttpServletRequest request, HttpServletResponse response) throws Exception{
    	
    	if (bindingResult.hasErrors()) {
    		modelMap.addAttribute("musique", musique);
        	modelMap.addAttribute("path", "public/creationmusique");
        	return "public/creationmusique/index";
    	}
    	
    	InputStream inputStream = file.getInputStream();
    	
    	MPeg3 mPeg3 = new MPeg3(inputStream);
 	   	FichierAudio fichierAudio = new FichierAudio();
 	   	String titre = mPeg3.getTitre();
 	   	titre.trim();
 	   	fichierAudio.setTitreMusique(titre);
 	   	fichierAudio.setTypeAudio(TypeAudio.MP3);
 	   	fichierAudio.setFichierMP3(file.getBytes());
 	   	
 	   	/*MusiqueVersion musiqueVersion = new MusiqueVersion();
 	   	musiqueVersion.setDateEdition(Calendar.getInstance().getTime());*/
 	   	
 	   	MusiqueVersion musiqueVersion = musique.getVersionActuelle();
 	   	musiqueVersion.setMusiqueActuelle(musique);
 	   	musiqueVersion.setFichierAudio(fichierAudio);
 	   	
 	   	/*musiqueVersion.setMusiqueActuelle(musique);
 	   	musique.setVersionActuelle(musiqueVersion);*/
 	   	
 	   	musique.persist();
 	   	
 	   String realPath = request.getSession().getServletContext().getRealPath("/web.xml");
	   realPath = realPath.substring(0, realPath.indexOf("web.xml"));
	   realPath = realPath + "musics";
	   File fichier = new File(realPath);
	   if(fichier.exists() == false){
		   fichier.mkdir();
	   }
	   
	   realPath = realPath + "\\" + musique.getId();
	   fichier = new File(realPath);
	   if(fichier.exists() == false){
		   fichier.mkdir();
	   }
	   
	   realPath = realPath + "\\" + fichierAudio.getId();
	   fichier = new File(realPath);
	   if(fichier.exists() == false){
		   fichier.mkdir();
	   }
	   
	   realPath = realPath + "\\" + fichierAudio.getTitreMusique() + ".mp3";
	   fichier = new File(realPath);
	   
	   FileOutputStream fos = new FileOutputStream(fichier);
	   fos.write(file.getBytes());
	   fos.close();
 	   	
	   return "redirect:/public/versionsmusiques/contributions/" + musique.getId() + "/" + musiqueVersion.getId();
 	   //	return "index";
    }

    /*@RequestMapping(value="/infosversion/{idVersion}", method = RequestMethod.GET)
    public String getInfosVersion(@PathVariable Long idVersion, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    	
    	MusiqueVersion musiqueVersion = (MusiqueVersion)entityManager.find(MusiqueVersion.class, idVersion);
    	
    	modelMap.addAttribute("musiqueVersion", musiqueVersion);
    	modelMap.addAttribute("path", "public/creationmusique");
    	return "public/creationmusique/infosversion";
    }*/
    
    @RequestMapping
    public String index() {
        return "public/creationmusique/index";
    }
    
    @ModelAttribute("finitudes")
    public Collection<Finitude> populateFinitudes() {
        return Arrays.asList(Finitude.class.getEnumConstants());
    }
    
    @ModelAttribute("etats")
    public Collection<Etat> populateEtats() {
        return Arrays.asList(Etat.class.getEnumConstants());
    }
    
    void addDateTimeFormatPatterns(Model uiModel) {
        uiModel.addAttribute("musiqueVersion_dateedition_date_format", "dd-MM-yyyy");
    }
    
    @ModelAttribute("diffusions")
    public Collection<Diffusion> populateDiffusions() {
        return Arrays.asList(Diffusion.class.getEnumConstants());
    }
}
