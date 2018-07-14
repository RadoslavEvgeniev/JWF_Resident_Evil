package residentevil.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import residentevil.common.annotations.PreAuthenticate;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController extends BaseController {

    @GetMapping("/")
    public ModelAndView index(HttpSession session) {
        if (session.getAttribute("user-id") != null) {
            return super.redirect("/home");
        }

        return super.view("index");
    }
}
