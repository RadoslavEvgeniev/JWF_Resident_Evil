package residentevil.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import residentevil.dtos.CapitalDto;
import residentevil.dtos.VirusDto;
import residentevil.entities.enums.Magnitude;
import residentevil.entities.enums.Mutation;
import residentevil.sevices.CapitalService;
import residentevil.sevices.VirusService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
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
    public ModelAndView addVirus(@ModelAttribute("virusDto") VirusDto virusDto, ModelAndView modelAndView) {
        modelAndView.addObject("virusDto", virusDto);
        modelAndView.addObject("releasedOnDate", virusDto.getReleasedOn());
        this.addObjectsInModelAndView(modelAndView);

        return super.view("viruses/add-virus", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addVirusConfirm(@Valid @ModelAttribute("virusDto") VirusDto virusDto, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            this.addObjectsInModelAndView(modelAndView);

            return super.view("viruses/add-virus", modelAndView);
        }

        this.virusService.importVirus(virusDto);

        return super.redirect("/");
    }

    @GetMapping("")
    public ModelAndView showViruses(ModelAndView modelAndView) {
        modelAndView.addObject("viruses", this.virusService.extractAllViruses());

        return super.view("viruses/show-viruses", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteVirus(@PathVariable("id") String id, ModelAndView modelAndView) {
        VirusDto virusDto = this.virusService.extractVirusById(id);
        List<Long> capitalIds = virusDto.getCapitals().stream().map(CapitalDto::getId).collect(Collectors.toList());
        virusDto.setCapitalIds(capitalIds);
        modelAndView.addObject("virusDto", virusDto);
        this.addObjectsInModelAndView(modelAndView);

        return super.view("viruses/delete-virus", modelAndView);
    }

    @PostMapping("/delete/{id}")
    public ModelAndView deleteVirusConfirm(@PathVariable("id") String id) {
        this.virusService.removeVirusById(id);

        return super.redirect("/viruses");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editVirus(@PathVariable("id") String id, ModelAndView modelAndView) {
        VirusDto virusDto = this.virusService.extractVirusById(id);
        List<Long> capitalIds = virusDto.getCapitals().stream().map(CapitalDto::getId).collect(Collectors.toList());
        virusDto.setCapitalIds(capitalIds);
        modelAndView.addObject("virusDto", virusDto);
        this.addObjectsInModelAndView(modelAndView);

        return super.view("viruses/edit-virus", modelAndView);
    }

    @PostMapping("/edit/{id}")
    public ModelAndView editVirusConfirm(@PathVariable("id") String id, @Valid @ModelAttribute("virusDto") VirusDto virusDto, BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            this.addObjectsInModelAndView(modelAndView);

            return super.view("viruses/edit-virus", modelAndView);
        }

        this.virusService.importVirus(virusDto);

        return super.redirect("/");
    }

    private void addObjectsInModelAndView(ModelAndView modelAndView) {
        modelAndView.addObject("mutations", Mutation.values());
        modelAndView.addObject("magnitudes", Magnitude.values());
        modelAndView.addObject("capitals", this.capitalService.extractAllCapitals().stream().sorted(Comparator.comparing(CapitalDto::getName)).collect(Collectors.toList()));
    }
}
