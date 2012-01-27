package com.ben.monlabel.web;

import com.ben.monlabel.domain.MusiqueVersion;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/musiqueversions")
@Controller
@RooWebScaffold(path = "musiqueversions", formBackingObject = MusiqueVersion.class)
public class MusiqueVersionController {
}
