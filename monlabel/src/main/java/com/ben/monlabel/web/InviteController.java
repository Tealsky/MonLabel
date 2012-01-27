package com.ben.monlabel.web;

import com.ben.monlabel.domain.Invite;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/invites")
@Controller
@RooWebScaffold(path = "invites", formBackingObject = Invite.class)
public class InviteController {
}
