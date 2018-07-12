package residentevil.sevices;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.dtos.CapitalDto;
import residentevil.dtos.VirusDto;
import residentevil.entities.Capital;
import residentevil.entities.Virus;
import residentevil.repositories.CapitalRepository;
import residentevil.repositories.VirusRepository;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
    public void importVirus(VirusDto virusDto) {
        this.addCapitalDtosToVirusDto(virusDto);

        Virus virus = this.modelMapper.map(virusDto, Virus.class);

        this.virusRepository.save(virus);
    }

    @Override
    public List<VirusDto> extractAllViruses() {
        List<Virus> virusesFromDb = this.virusRepository.findAll();
        List<VirusDto> virusDtos = new ArrayList<>();

        for (Virus virus : virusesFromDb) {
            VirusDto virusDto = this.modelMapper.map(virus, VirusDto.class);

            virusDtos.add(virusDto);
        }

        return virusDtos;
    }

    @Override
    public VirusDto extractVirusById(String id) {
        Virus virusFromDb = this.virusRepository.findById(id).orElse(null);
        if (virusFromDb == null) {
            throw new IllegalArgumentException("Invalid id");
        }

        VirusDto virusDto = this.modelMapper.map(virusFromDb, VirusDto.class);

        return virusDto;
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

    private void addCapitalDtosToVirusDto(VirusDto virusDto) {
        Set<CapitalDto> capitalDtos = new LinkedHashSet<>();
        for (Long capitalId : virusDto.getCapitalIds()) {
            Capital capitalFromDb = this.capitalRepository.findById(capitalId).orElse(null);

            CapitalDto capitalDto = this.modelMapper.map(capitalFromDb, CapitalDto.class);
            capitalDtos.add(capitalDto);
        }

        virusDto.setCapitals(capitalDtos);
    }
}
