package residentevil.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.common.annotations.PreAuthenticate;
import residentevil.entities.enums.UserRole;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class AdminController extends BaseController {

    @GetMapping("/home")
    @PreAuthenticate(loggedIn = true, inRole = "ADMIN")
    public ModelAndView home(HttpSession session) {
        if (session.getAttribute("user-role") == UserRole.USER) {
            return this.redirect("/home");
        }

        return super.view("users/home-admin");
    }
}
