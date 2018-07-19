package residentevil.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.models.binding.UserLoginBindingModel;
import residentevil.models.binding.UserRegisterBindingModel;
import residentevil.sevices.UserService;

@Controller
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register() {
        return super.view("users/register-user");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute("userBindingModel") UserRegisterBindingModel userRegisterBindingModel) {
        if (!this.userService.registerUser(userRegisterBindingModel)) {
            return super.view("users/register-user");
        }

        return super.redirect("/home");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login() {
        return super.view("users/login-user");
    }

    @PostMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView loginConfirm(@ModelAttribute("userBindingModel") UserLoginBindingModel userLoginBindingModel) {
        if (!this.userService.loginUser(userLoginBindingModel)) {
            return super.view("users/login-user");
        }

        return super.redirect("/home");
    }
}
