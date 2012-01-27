package com.ben.monlabel.web.publique;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;
import javax.validation.Valid;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

import com.ben.monlabel.domain.Diffusion;
import com.ben.monlabel.domain.FichierAudio;
import com.ben.monlabel.domain.Musique;
import com.ben.monlabel.domain.MusiqueVersion;
import com.ben.monlabel.domain.TypeAudio;

@RequestMapping("/public/listemusiques/**")
@Controller
//@SessionAttributes("musiqueVersion")
public class ListeMusiquesController{	// implements ServletContextAware{

	 @PersistenceContext
	 transient EntityManager entityManager;
	 
	 //private String uploadedFilesPath;
    
  /*  @RequestMapping(method = RequestMethod.POST, value = "{id}")
    public void post(@PathVariable Long id, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
    }*/

   /*@RequestMapping
    public String index() {
        return "public/listemusiques/index";
    }*/
      
	 /**
	  * Liste des musiques
	  * @param page
	  * @param size
	  * @param uiModel
	  * @return
	  */
   @RequestMapping(value="/index", method = RequestMethod.GET)
   public String list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel, HttpServletRequest request) {

	   List<String> labels = new ArrayList<String>();
	   labels.add("Titre");
	   labels.add("Titre Provisoire");
	   labels.add("Fichier audio");
	   labels.add("Finie");
	   labels.add("Etat");
	   
	   List musiques = entityManager.createQuery("select m from Musique m").getResultList();
	   
	  /* for(int i=0; i<musiques.size(); i++){
		   MusiqueVersion musiqueVersion = ((Musique)musiques.get(i)).getVersionActuelle();
		   if(musiqueVersion != null){
			   FichierAudio fichierAudio = musiqueVersion.getFichierAudio();
		   }
	   }*/
	   
	   uiModel.addAttribute("musiques", musiques);
	   uiModel.addAttribute("update", true);
	   uiModel.addAttribute("path", "/public/listemusiques");
	   uiModel.addAttribute("itemId", 1);
	   uiModel.addAttribute("contextPath", request.getContextPath());
	   
	   uiModel.addAttribute("columnLabels", labels);
       return "public/listemusiques/index";
	   
   }

   /**
    * Les informations sur une musique retrouvée d'après son id
    * @param idMusique
    * @param modelMap
    * @param request
    * @param response
    * @return
    */
   @RequestMapping(method = RequestMethod.GET, value = "{idMusique}")
   public String listVersions(@PathVariable Long idMusique, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
	   
	   Musique musique = (Musique)entityManager.find(Musique.class, idMusique);

	   String titre = musique.getTitre();
	   if(titre != null){
		   modelMap.addAttribute("titre", "Les versions de la musique : " + titre);
	   } else {
		   modelMap.addAttribute("titre", "Les versions de la musique : " + musique.getTitreProvisoire() + " (titre provisoire)");
	   }
	   
	   modelMap.addAttribute("musique", musique);
	   modelMap.addAttribute("pathRecupererVersionActuelle", "public/listemusiques");
	   modelMap.addAttribute("recupererVersionActuelle", true);
	   
	   modelMap.addAttribute("soumettreVersion", true);
	   modelMap.addAttribute("pathSoumettreVersion", idMusique + "/nouvelleversion");
	   modelMap.addAttribute("version", true);
	   
	   String contextPath = request.getContextPath();
	   
	   modelMap.addAttribute("contextPath", contextPath);
	   
	   Set<MusiqueVersion> versionsFutures = musique.getPropositionsFuturesVersions();
	  
	   /*List versionsFutures = entityManager.createQuery("select m from MusiqueVersion m where m.musiqueActuelle.id like :idMusique order by m.dateEdition").setParameter("idMusique", idMusique).getResultList();
	   MusiqueVersion versionActuelle = musique.getVersionActuelle();
	   versionsFutures.remove(versionActuelle);
	   */
	   modelMap.addAttribute("versions", versionsFutures);
	   modelMap.addAttribute("validerVersion", true);
	   modelMap.addAttribute("pathValiderVersion", "/public/versionsmusiques/validationversion");
	   modelMap.addAttribute("plusDInfos", true);
	   modelMap.addAttribute("pathPlusDInfos", "/public/versionsmusiques/commentairesversion");
	   
	   return "public/listemusiques/versions";
   }
   
/*   @ModelAttribute("musiqueVersion")
   public MusiqueVersion getMusiqueVersion(){
	   System.out.println("new getMusiqueVersion");
	   return new MusiqueVersion();
   }*/
   
   @RequestMapping(value="{idMusique}/nouvelleversion", method = RequestMethod.GET)
   public String nouvelleVersion(@PathVariable Long idMusique, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
	   
	   Musique musique = (Musique)entityManager.find(Musique.class, idMusique);

	   String titre = musique.getTitre();
	   if(titre != null){
		   modelMap.addAttribute("titreMusique", titre);
	   } else {
		   modelMap.addAttribute("titreMusique", musique.getTitreProvisoire() + " (titre provisoire)");
	   }
	   
	   modelMap.addAttribute("render", true);
	   modelMap.addAttribute("multipart", true);
	   modelMap.addAttribute("id", 1);
	   modelMap.addAttribute("compositePkField", false);
	   modelMap.addAttribute("title_msg", "Soumission d'une nouvelle version d'une musique");
	   modelMap.addAttribute("openPane", true);
	   modelMap.addAttribute("path", "public/listemusiques/" + idMusique + "/nouvelleversion");
	   
	   List<Diffusion> diffusions = new ArrayList<Diffusion>();
	   diffusions.add(Diffusion.AU_MEMBRE_DU_LABEL);
	   diffusions.add(Diffusion.PUBLIQUE);
	   modelMap.addAttribute("diffusions", diffusions);
	   
	   modelMap.addAttribute("fichieraudios", FichierAudio.findAllFichierAudios());
	   
	   MusiqueVersion nouvelleVersion = new MusiqueVersion();
	  
	   modelMap.addAttribute("musiqueVersion_dateedition_date_format", "dd-MM-yyyy");
	   modelMap.addAttribute("nouvelleVersion", nouvelleVersion);
	   
	   return "public/listemusiques/nouvelleversion";
   }
   
/*   @RequestMapping(method = RequestMethod.GET, value = "fichierMP3")
   public String fichierMP3(@ModelAttribute("musiqueVersion") MusiqueVersion musiqueVersion, ModelMap modelMap, HttpServletRequest request, HttpServletResponse response) {
	   System.out.println(musiqueVersion.getDateEdition());
	   FichierAudio fichierAudio = new FichierAudio();
	   Set<TypeAudio> typesAudio = new HashSet<TypeAudio>();
	   typesAudio.add(TypeAudio.MP3);
	   modelMap.addAttribute("typeaudios", typesAudio);
	   modelMap.addAttribute("fichierAudio", fichierAudio);
	   return "public/listemusiques/fichierMP3";
   }
   
   @RequestMapping(value = "fichierMP3", method = RequestMethod.POST) 
   public String handleFormUpload(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws IOException {

       if (!file.isEmpty()) {
    	   InputStream inputStream = file.getInputStream();
    	   try {
    		   MPeg3 mPeg3 = new MPeg3(inputStream);
    		   FichierAudio fichierAudio = new FichierAudio();
    		   fichierAudio.setTitreMusique(mPeg3.getTitre());
    		   fichierAudio.setTypeAudio(TypeAudio.MP3);
    		   fichierAudio.setFichierMP3(file.getBytes());
    		   fichierAudio.merge();
    	   } catch (Exception e) {
    		   throw new IOException(e.getLocalizedMessage());
    	   }
    	   return "redirect:/public/listemusiques/1/nouvelleversion";
      } else {
          return "redirect:uploadFailure";
      }
   }
*/   
   @RequestMapping(value="{idMusique}/nouvelleversion", method = RequestMethod.POST)
   public String create(@PathVariable Long idMusique, @Valid MusiqueVersion musiqueVersion, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, @RequestParam("file") MultipartFile file, HttpServletResponse response) throws Exception {
   //public String create(@PathVariable Long idMusique, @Valid  @ModelAttribute("musiqueVersion") MusiqueVersion musiqueVersion, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest, SessionStatus sessionStatus, @RequestParam("file") MultipartFile file) throws Exception {
	   
	   if (bindingResult.hasErrors()) {
    	   
    	   Musique musique = (Musique)entityManager.find(Musique.class, idMusique);

    	   String titre = musique.getTitre();
    	   if(titre != null){
    		   uiModel.addAttribute("titreMusique", titre);
    	   } else {
    		   uiModel.addAttribute("titreMusique", musique.getTitreProvisoire() + " (titre provisoire)");
    	   }
    	   
    	   uiModel.addAttribute("render", true);
    	   uiModel.addAttribute("multipart", true);
    	   uiModel.addAttribute("id", 1);
    	   uiModel.addAttribute("compositePkField", false);
    	   uiModel.addAttribute("title_msg", "Soumission d'une nouvelle version d'une musique");
    	   uiModel.addAttribute("openPane", true);
    	   uiModel.addAttribute("path", "public/listemusiques/" + idMusique + "/nouvelleversion");
    	   
    	   List<Diffusion> diffusions = new ArrayList<Diffusion>();
    	   diffusions.add(Diffusion.AU_MEMBRE_DU_LABEL);
    	   diffusions.add(Diffusion.PUBLIQUE);
    	   uiModel.addAttribute("diffusions", diffusions);
    	   
    	   uiModel.addAttribute("fichieraudios", FichierAudio.findAllFichierAudios());
    	   //MusiqueVersion nouvelleVersion = new MusiqueVersion();
    	  
    	   uiModel.addAttribute("musiqueVersion_dateedition_date_format", "dd-MM-yyyy");
    	   uiModel.addAttribute("nouvelleVersion", musiqueVersion);
    	   
    	   return "public/listemusiques/" + idMusique + "/nouvelleversion";
           
           /*uiModel.addAttribute("render", true);
           uiModel.addAttribute("label", "mon label");
           uiModel.addAttribute("multipart", true);
           uiModel.addAttribute("id", 1);
           uiModel.addAttribute("compositePkField", false);
           uiModel.addAttribute("title_msg", "mon titre");
           uiModel.addAttribute("openPane", true);
           uiModel.addAttribute("path", "public/listemusiques/nouvelleversion/");
    	   
           uiModel.addAttribute("disabledDate", false);
           
           uiModel.addAttribute("musiqueVersion_dateedition_date_format", "dd-MM-yyyy");
           uiModel.addAttribute("nouvelleVersion", musiqueVersion);
           
           return "public/listemusiques/nouvelleversion";*/
       }
    
       if (!file.isEmpty()) {
    	   
    	  /* JspFactory jspFactoty = JspFactory.getDefaultFactory();
    	   PageContext pageContext = jspFactoty.getPageContext(
    	   	this,		// La Servlet
    	   	httpServletRequest,	// Le request courant
    	   	response,	// La réponse courante
    	   	null,		// La page de redirection d'erreur (null si aucune)
    	   	true,		// Utilise la session
    	   	PageContext.REQUEST_SCOPE,	// Pas de buffer
    	   	false );	// pas d'autoflush
    	   try {
    		   ServletContext context = pageContext.getServletContext();
        	   String filePath = context.getInitParameter("uploadedFiles");
    	    
    	   } finally {
    	   	// Libération du PageContext
    	   	jspFactoty.releasePageContext(pageContext);
    	   }*/
    	
    	   
    	   InputStream inputStream = file.getInputStream();
    	   
    	   MPeg3 mPeg3 = new MPeg3(inputStream);
    	   FichierAudio fichierAudio = new FichierAudio();
    	   String titre = mPeg3.getTitre();
    	   titre.trim();
    	   fichierAudio.setTitreMusique(titre);
    	   fichierAudio.setTypeAudio(TypeAudio.MP3);
    	   fichierAudio.setFichierMP3(file.getBytes());
    	   //System.out.println(fichierAudio.getTitreMusique());
    	   fichierAudio.persist();
    	   
    	   /*//String fileName = fichierAudio.getTitreMusique() + ".mp3";
    	   URI uri = new URI(uploadedFilesPath);
    	   File fichier = new File(uri);*/
    	   
    	   String realPath = httpServletRequest.getSession().getServletContext().getRealPath("/web.xml");
    	   realPath = realPath.substring(0, realPath.indexOf("web.xml"));
    	   realPath = realPath + "musics\\" + idMusique;
    	   File fichier = new File(realPath);
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
    	   
    	   musiqueVersion.setFichierAudio(fichierAudio);
           
       } else {
    	   throw new Exception("mp3 vide !");
       }
       
       
       uiModel.asMap().clear();
       
       musiqueVersion.persist();
       //sessionStatus.setComplete();
      
       return "redirect:/public/versionsmusiques/contributions/" + idMusique + "/" + musiqueVersion.getId();
       
   }
   
  

//	@Override
//	public void setServletContext(ServletContext servletContext) {
//		uploadedFilesPath = "/" + servletContext.getInitParameter("uploadedFilesPath");
//	}
      
}
