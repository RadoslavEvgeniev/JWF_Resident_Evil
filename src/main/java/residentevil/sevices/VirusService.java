package residentevil.sevices;

import residentevil.models.binding.VirusBindingModel;
import residentevil.models.view.VirusViewModel;

import java.util.List;

public interface VirusService {

    void importVirus(VirusBindingModel virusBindingModel);

    List<VirusViewModel> extractAllViruses();

    VirusBindingModel extractVirusByIdForEditOrDelete(String id);

    void removeVirusById(String id);

    String extractVirusesAsJson();
}
