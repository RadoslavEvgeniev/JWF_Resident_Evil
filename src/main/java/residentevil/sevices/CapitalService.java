package residentevil.sevices;

import residentevil.dtos.CapitalDto;

import java.util.List;

public interface CapitalService {

    List<CapitalDto> extractAllCapitals();
}
