package residentevil.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.common.annotations.PreAuthenticate;
import residentevil.dtos.UserDto;
import residentevil.entities.enums.UserRole;
import residentevil.sevices.UserService;

import javax.servlet.http.HttpSession;

@Controller
public class UserController extends BaseController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/home")
    @PreAuthenticate(loggedIn = true)
    public ModelAndView home(HttpSession session, ModelAndView modelAndView) {
        modelAndView.addObject("username", session.getAttribute("user-username"));
        if (session.getAttribute("user-role") == UserRole.ADMIN) {
            return this.redirect("/admin/home");
        }

        return super.view("users/home-user");
    }

    @GetMapping("/register")
    @PreAuthenticate
    public ModelAndView register() {
        return super.view("users/register-user");
    }

    @PostMapping("/register")
    @PreAuthenticate
    public ModelAndView registerConfirm(@ModelAttribute UserDto userDto) {
        this.userService.registerUser(userDto);

        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthenticate
    public ModelAndView login() {
       return super.view("users/login-user");
    }

    @PostMapping("/login")
    @PreAuthenticate
    public ModelAndView loginConfirm(@ModelAttribute UserDto userDto, HttpSession session) {
        if (!this.userService.loginUser(userDto)) {
            return super.view("users/login-user");
        }

        UserDto userFromDb = this.userService.getUserByUsername(userDto.getUsername());

        session.setAttribute("user-id", userFromDb.getId());
        session.setAttribute("user-username", userFromDb.getUsername());
        session.setAttribute("user-role", userFromDb.getRole());

        if(userFromDb.getRole() == UserRole.ADMIN) {
            return this.redirect("/admin/home");
        }

        return super.redirect("/home");
    }

    @GetMapping("/logout")
    @PreAuthenticate(loggedIn = true)
    public ModelAndView logout(HttpSession session) {
        session.invalidate();

        return super.redirect("/");
    }
}
