package residentevil.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import residentevil.sevices.VirusService;

@Controller
@RequestMapping("/map")
public class MapController extends BaseController {

    private VirusService virusService;

    @Autowired
    public MapController(VirusService virusService) {
        this.virusService = virusService;
    }

    @GetMapping("")
    public ModelAndView getMapPage(ModelAndView modelAndView) {
        String geoJson = this.virusService.extractVirusesAsJson();
        modelAndView.addObject("geoJson", geoJson);

        return super.view("maps/map", modelAndView);
    }
}
