// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.ben.monlabel.web;

import com.ben.monlabel.domain.ProjetCubase;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect ProjetCubaseController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST)
    public java.lang.String ProjetCubaseController.create(@Valid ProjetCubase projetCubase, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("projetCubase", projetCubase);
            return "projetcubases/create";
        }
        uiModel.asMap().clear();
        projetCubase.persist();
        return "redirect:/projetcubases/" + encodeUrlPathSegment(projetCubase.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", method = RequestMethod.GET)
    public java.lang.String ProjetCubaseController.createForm(Model uiModel) {
        uiModel.addAttribute("projetCubase", new ProjetCubase());
        return "projetcubases/create";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public java.lang.String ProjetCubaseController.show(@PathVariable("id") java.lang.Long id, Model uiModel) {
        uiModel.addAttribute("projetcubase", ProjetCubase.findProjetCubase(id));
        uiModel.addAttribute("itemId", id);
        return "projetcubases/show";
    }
    
    @RequestMapping(method = RequestMethod.GET)
    public java.lang.String ProjetCubaseController.list(@RequestParam(value = "page", required = false) java.lang.Integer page, @RequestParam(value = "size", required = false) java.lang.Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("projetcubases", ProjetCubase.findProjetCubaseEntries(firstResult, sizeNo));
            float nrOfPages = (float) ProjetCubase.countProjetCubases() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("projetcubases", ProjetCubase.findAllProjetCubases());
        }
        return "projetcubases/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT)
    public java.lang.String ProjetCubaseController.update(@Valid ProjetCubase projetCubase, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            uiModel.addAttribute("projetCubase", projetCubase);
            return "projetcubases/update";
        }
        uiModel.asMap().clear();
        projetCubase.merge();
        return "redirect:/projetcubases/" + encodeUrlPathSegment(projetCubase.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", method = RequestMethod.GET)
    public java.lang.String ProjetCubaseController.updateForm(@PathVariable("id") java.lang.Long id, Model uiModel) {
        uiModel.addAttribute("projetCubase", ProjetCubase.findProjetCubase(id));
        return "projetcubases/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public java.lang.String ProjetCubaseController.delete(@PathVariable("id") java.lang.Long id, @RequestParam(value = "page", required = false) java.lang.Integer page, @RequestParam(value = "size", required = false) java.lang.Integer size, Model uiModel) {
        ProjetCubase projetCubase = ProjetCubase.findProjetCubase(id);
        projetCubase.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/projetcubases";
    }
    
    @ModelAttribute("projetcubases")
    public Collection<ProjetCubase> ProjetCubaseController.populateProjetCubases() {
        return ProjetCubase.findAllProjetCubases();
    }
    
    java.lang.String ProjetCubaseController.encodeUrlPathSegment(java.lang.String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
