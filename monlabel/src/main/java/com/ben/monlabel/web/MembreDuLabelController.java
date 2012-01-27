package com.ben.monlabel.web;

import com.ben.monlabel.domain.MembreDuLabel;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/membredulabels")
@Controller
@RooWebScaffold(path = "membredulabels", formBackingObject = MembreDuLabel.class)
public class MembreDuLabelController {
}
