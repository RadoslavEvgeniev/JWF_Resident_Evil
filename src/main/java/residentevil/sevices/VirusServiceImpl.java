package residentevil.sevices;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.entities.Capital;
import residentevil.entities.Virus;
import residentevil.models.binding.VirusBindingModel;
import residentevil.models.view.VirusViewModel;
import residentevil.repositories.CapitalRepository;
import residentevil.repositories.VirusRepository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class VirusServiceImpl implements VirusService {

    private VirusRepository virusRepository;
    private CapitalRepository capitalRepository;
    private ModelMapper modelMapper;
    private Gson gson;

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, CapitalRepository capitalRepository, ModelMapper modelMapper, Gson gson) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
        this.gson = gson;
    }

    @Override
    public void importVirus(VirusBindingModel virusBindingModel) {
        Virus virus = this.modelMapper.map(virusBindingModel, Virus.class);

        Set<Capital> capitals = new LinkedHashSet<>();
        for (Long capitalId : virusBindingModel.getCapitalIds()) {
            Capital capital = this.capitalRepository.findById(capitalId).orElse(null);

            if (capital == null) {
                continue;
            }

            capitals.add(capital);
        }

        virus.setCapitals(capitals);

        this.virusRepository.save(virus);
    }

    @Override
    public List<VirusViewModel> extractAllViruses() {
        List<Virus> virusesFromDb = this.virusRepository.findAll();
        List<VirusViewModel> viruses = new ArrayList<>();

        for (Virus virusFromDb : virusesFromDb) {
            VirusViewModel virus = this.modelMapper.map(virusFromDb, VirusViewModel.class);

            viruses.add(virus);
        }

        return viruses;
    }

    @Override
    public VirusBindingModel extractVirusByIdForEditOrDelete(String id) {
        Virus virusFromDb = this.virusRepository.findById(id).orElse(null);
        if (virusFromDb == null) {
            throw new IllegalArgumentException("Invalid id");
        }

        VirusBindingModel virus = this.modelMapper.map(virusFromDb, VirusBindingModel.class);
        List<Long> capitalIds = virusFromDb.getCapitals().stream().map(Capital::getId).collect(Collectors.toList());
        virus.setCapitalIds(capitalIds);

        return virus;
    }

    @Override
    public void removeVirusById(String id) {
        this.virusRepository.deleteById(id);
    }

    @Override
    public String extractVirusesAsJson() {
        List<Virus> virusesFromDb  = this.virusRepository.findAll();
        String geoJson = this.gson.toJson(virusesFromDb);

        return geoJson;
    }
}
