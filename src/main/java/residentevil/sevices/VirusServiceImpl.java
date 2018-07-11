package residentevil.sevices;

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

    @Autowired
    public VirusServiceImpl(VirusRepository virusRepository, CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.virusRepository = virusRepository;
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
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
