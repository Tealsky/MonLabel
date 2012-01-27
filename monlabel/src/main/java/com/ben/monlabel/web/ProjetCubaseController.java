package com.ben.monlabel.web;

import com.ben.monlabel.domain.ProjetCubase;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/projetcubases")
@Controller
@RooWebScaffold(path = "projetcubases", formBackingObject = ProjetCubase.class)
public class ProjetCubaseController {
}
