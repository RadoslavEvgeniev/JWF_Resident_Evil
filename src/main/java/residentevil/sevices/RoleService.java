package residentevil.sevices;

import residentevil.models.view.UserRoleViewModel;

import java.util.List;

public interface RoleService {

    List<UserRoleViewModel> extractAllRoles();
}
