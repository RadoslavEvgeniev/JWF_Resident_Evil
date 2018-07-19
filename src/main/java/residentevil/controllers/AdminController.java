package residentevil.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.models.binding.UserEditBindingModel;
import residentevil.models.view.UserViewModel;
import residentevil.sevices.RoleService;
import residentevil.sevices.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class AdminController extends BaseController {

    private UserService userService;
    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ModelAndView listUsers(Principal principal, ModelAndView modelAndView) {
        List<UserViewModel> userViewModels = this.userService.extractAllUsers()
                .stream().filter(u -> !u.getUsername().equals(principal.getName())).collect(Collectors.toList());
        modelAndView.addObject("users", userViewModels);

        return super.view("users/show-users", modelAndView);
    }

    @GetMapping("/users/edit/{id}")
    public ModelAndView editUser(@PathVariable("id") String id, ModelAndView modelAndView) {
        UserEditBindingModel userBindingModel = this.userService.extractUserForEditById(id);
        modelAndView.addObject("editBindingModel", userBindingModel);
        modelAndView.addObject("roles", this.roleService.extractAllRoles());

        return super.view("users/edit-user", modelAndView);
    }

    @PostMapping("/users/edit/{id}")
    public ModelAndView editUserConfirm(@PathVariable("id") String id,
                                        @Valid @ModelAttribute("editBindingModel") UserEditBindingModel userEditBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("roles", this.roleService.extractAllRoles());

            return super.view("users/edit-user", modelAndView);
        }

        this.userService.insertEditedUser(userEditBindingModel);

        return super.redirect("/users");
    }
}
