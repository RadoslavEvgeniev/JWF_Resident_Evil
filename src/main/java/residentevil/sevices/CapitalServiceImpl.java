package residentevil.sevices;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.entities.Capital;
import residentevil.models.view.CapitalViewModel;
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
    public List<CapitalViewModel> extractAllCapitals() {
        List<Capital> capitalsFromDb = this.capitalRepository.findAll();
        List<CapitalViewModel> capitalViewModels = new ArrayList<>();

        for (Capital capital : capitalsFromDb) {
            CapitalViewModel capitalViewModel = this.modelMapper.map(capital, CapitalViewModel.class);

            capitalViewModels.add(capitalViewModel);
        }

        return capitalViewModels;
    }
}
