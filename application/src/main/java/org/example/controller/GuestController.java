package org.example.controller;

import com.google.common.cache.LoadingCache;
import org.example.exception.BlockedException;
import org.example.model.Guest;
import org.example.model.GuestModel;
import org.example.model.IpModel;
import org.example.service.GuestService;
import org.example.service.LoginAttemptService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class GuestController {

    private final GuestService guestService;

    private final LoginAttemptService loginAttemptService;

    public GuestController(GuestService guestService, LoginAttemptService loginAttemptService) {
        super();
        this.guestService = guestService;
        this.loginAttemptService = loginAttemptService;
    }

    @GetMapping(value = {"/", "/index"})
    public String getHomePage(Model model) {

        return "index";
    }

    @GetMapping(value = "/login")
    public String getLoginPage(Model model) {
        return "login";
    }

    @GetMapping(value = "/logout-success")
    public String getLogoutPage(Model model) {
        return "logout";
    }

    @GetMapping(value = "/guests")
    @PreAuthorize("hasRole('ROLE_REGISTERED_USER') or hasRole('ROLE_ADMIN_USER')")
    public String getGuests(Model model) {
        List<Guest> guests = this.guestService.getAllGuests();
        model.addAttribute("guests", guests);
        return "guests-view";
    }

    @GetMapping(value = "/guests/add")
    @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
    public String getAddGuestForm(Model model) {
        return "guest-view";
    }

    @PostMapping(value = "/guests")
    @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
    public ModelAndView addGuest(HttpServletRequest request, Model model, @ModelAttribute GuestModel guestModel) {
        Guest guest = this.guestService.addGuest(guestModel);
        model.addAttribute("guest", guest);
        request.setAttribute(View.RESPONSE_STATUS_ATTRIBUTE, HttpStatus.TEMPORARY_REDIRECT);
        return new ModelAndView("redirect:/guests/" + guest.getId());
    }

    @GetMapping(value = "/guests/{id}")
    @PreAuthorize("hasRole('ROLE_REGISTERED_USER') or hasRole('ROLE_ADMIN_USER')")
    public String getGuest(Model model, @PathVariable long id) {
        Guest guest = this.guestService.getGuest(id);
        model.addAttribute("guest", guest);
        return "guest-view";
    }

    @PostMapping(value = "/guests/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN_USER')")
    public String updateGuest(Model model, @PathVariable long id, @ModelAttribute GuestModel guestModel) {
        Guest guest = this.guestService.updateGuest(id, guestModel);
        model.addAttribute("guest", guest);
        model.addAttribute("guestModel", new GuestModel());
        return "guest-view";
    }

    @GetMapping(value = "/getAttemptsCache")
    public String getAttemptsCache(Model model) {
        Map<String, Integer> ips = this.loginAttemptService.getAttemptsCache().asMap();
        List<IpModel> ipModels = new ArrayList<>();
        for (String key : ips.keySet()) {
            ipModels.add(new IpModel(key, ips.get(key)));
        }
        model.addAttribute("ips", ipModels);
        return "ips-view";
    }

}
