package com.ben.monlabel.web;

import com.ben.monlabel.domain.Contribution;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/contributions")
@Controller
@RooWebScaffold(path = "contributions", formBackingObject = Contribution.class)
public class ContributionController {
}
