package residentevil.sevices;

import residentevil.dtos.VirusDto;

import java.util.List;

public interface VirusService {

    void importVirus(VirusDto virusDto);

    List<VirusDto> extractAllViruses();

    VirusDto extractVirusById(String id);

    void removeVirusById(String id);

    String extractVirusesAsJson();
}
