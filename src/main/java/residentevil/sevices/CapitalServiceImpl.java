package residentevil.sevices;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.dtos.CapitalDto;
import residentevil.entities.Capital;
import residentevil.repositories.CapitalRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CapitalServiceImpl implements CapitalService {

    private CapitalRepository capitalRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CapitalServiceImpl(CapitalRepository capitalRepository, ModelMapper modelMapper) {
        this.capitalRepository = capitalRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CapitalDto> extractAllCapitals() {
        List<Capital> capitalsFromDb = this.capitalRepository.findAll();
        List<CapitalDto> capitalDtos = new ArrayList<>();

        for (Capital capital : capitalsFromDb) {
            CapitalDto capitalDto = this.modelMapper.map(capital, CapitalDto.class);

            capitalDtos.add(capitalDto);
        }

        return capitalDtos;
    }
}
