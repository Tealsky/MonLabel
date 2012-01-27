package com.ben.monlabel.web;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.ben.monlabel.domain.Commentaire;
import com.ben.monlabel.domain.MembreDuLabel;

@RequestMapping("/**")
@Controller
public class MainController {
	
	 @PersistenceContext
	 transient EntityManager entityManager;
	 
	@RequestMapping(value="/", method = RequestMethod.GET)
    public String get(@RequestParam(value = "page", required = false) java.lang.Integer page, @RequestParam(value = "size", required = false) java.lang.Integer size, Model modelMap, HttpServletRequest request, HttpServletResponse response) {
    	
		List musiques = entityManager.createQuery("select m from Musique m").getResultList();
		   
		modelMap.addAttribute("musiques", musiques);
		modelMap.addAttribute("contextPath", request.getContextPath());
		
		int sizeNo = 10;
		
		page = new Integer(1);
		
		if (page != null) {
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            modelMap.addAttribute("commentaires", Commentaire.findCommentaireEntries(firstResult, sizeNo));
            float nrOfPages = (float) MembreDuLabel.countMembreDuLabels() / sizeNo;
            int maxPages = (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages);
            modelMap.addAttribute("maxPages", maxPages);
            modelMap.addAttribute("page", (int)1);
            modelMap.addAttribute("size", (int)5);
        } else {
        	modelMap.addAttribute("commentaires", Commentaire.findAllCommentaires());
        }

    	return "index";
    }

}
