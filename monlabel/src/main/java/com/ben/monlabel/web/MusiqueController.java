package com.ben.monlabel.web;

import com.ben.monlabel.domain.Musique;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/musiques")
@Controller
@RooWebScaffold(path = "musiques", formBackingObject = Musique.class)
public class MusiqueController {
}
