package residentevil.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.entities.enums.Magnitude;
import residentevil.entities.enums.Mutation;
import residentevil.models.binding.VirusBindingModel;
import residentevil.models.view.CapitalViewModel;
import residentevil.sevices.CapitalService;
import residentevil.sevices.VirusService;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {

    private CapitalService capitalService;
    private VirusService virusService;

    @Autowired
    public VirusController(CapitalService capitalService, VirusService virusService) {
        this.capitalService = capitalService;
        this.virusService = virusService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ModelAndView addVirus(@ModelAttribute("virusBindingModel") VirusBindingModel virusBindingModel, ModelAndView modelAndView) {
        modelAndView.addObject("virusBindingModel", virusBindingModel);

        this.addObjectsInModelAndView(modelAndView);

        return super.view("viruses/add-virus", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ModelAndView addVirusConfirm(@Valid @ModelAttribute("virusBindingModel") VirusBindingModel virusBindingModel,
                                        BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            this.addObjectsInModelAndView(modelAndView);

            return super.view("viruses/add-virus", modelAndView);
        }

        this.virusService.importVirus(virusBindingModel);

        return super.redirect("/");
    }

    @GetMapping("")
    @PreAuthorize("hasAuthority('USER')")
    public ModelAndView showViruses(ModelAndView modelAndView) {
        modelAndView.addObject("viruses", this.virusService.extractAllViruses());

        return super.view("viruses/show-viruses", modelAndView);
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ModelAndView deleteVirus(@PathVariable("id") String id, ModelAndView modelAndView) {
        VirusBindingModel virus = this.virusService.extractVirusByIdForEditOrDelete(id);
        modelAndView.addObject("virusBindingModel", virus);

        this.addObjectsInModelAndView(modelAndView);

        return super.view("viruses/delete-virus", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ModelAndView deleteVirusConfirm(@PathVariable("id") String id) {
        this.virusService.removeVirusById(id);

        return super.redirect("/viruses");
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ModelAndView editVirus(@PathVariable("id") String id, ModelAndView modelAndView) {
        VirusBindingModel virus = this.virusService.extractVirusByIdForEditOrDelete(id);
        modelAndView.addObject("virusBindingModel", virus);

        this.addObjectsInModelAndView(modelAndView);

        return super.view("viruses/edit-virus", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasAuthority('MODERATOR')")
    public ModelAndView editVirusConfirm(@PathVariable("id") String id,
                                         @Valid @ModelAttribute("virusBindingModel") VirusBindingModel virusBindingModel,
                                         BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            this.addObjectsInModelAndView(modelAndView);

            return super.view("viruses/edit-virus", modelAndView);
        }

        this.virusService.importVirus(virusBindingModel);

        return super.redirect("/");
    }

    private void addObjectsInModelAndView(ModelAndView modelAndView) {
        modelAndView.addObject("mutations", Mutation.values());
        modelAndView.addObject("magnitudes", Magnitude.values());
        modelAndView.addObject("capitals", this.capitalService.extractAllCapitals().stream().sorted(Comparator.comparing(CapitalViewModel::getName)).collect(Collectors.toList()));
    }
}
