package residentevil.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.models.view.UserViewModel;
import residentevil.sevices.UserService;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController {

    private UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView listUsers(Principal principal, ModelAndView modelAndView) {
        List<UserViewModel> userViewModels = this.userService.extractAllUsers()
                .stream().filter(u -> !u.getUsername().equals(principal.getName())).collect(Collectors.toList());
        modelAndView.addObject("users", userViewModels);

        return super.view("users/show-users", modelAndView);
    }
}
