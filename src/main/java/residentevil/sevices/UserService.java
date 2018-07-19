package residentevil.sevices;

import org.springframework.security.core.userdetails.UserDetailsService;
import residentevil.models.binding.UserEditBindingModel;
import residentevil.models.binding.UserLoginBindingModel;
import residentevil.models.binding.UserRegisterBindingModel;
import residentevil.models.view.UserViewModel;

import java.util.List;

public interface UserService extends UserDetailsService {

    boolean registerUser(UserRegisterBindingModel userRegisterBindingModel);

    boolean loginUser(UserLoginBindingModel userLoginBindingModel);

    List<UserViewModel> extractAllUsers();

    UserEditBindingModel extractUserForEditById(String id);

    boolean insertEditedUser(UserEditBindingModel userEditBindingModel);
}
