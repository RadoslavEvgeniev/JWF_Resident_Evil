package residentevil.sevices;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import residentevil.entities.User;
import residentevil.entities.UserRole;
import residentevil.models.binding.UserLoginBindingModel;
import residentevil.models.binding.UserRegisterBindingModel;
import residentevil.models.view.UserViewModel;
import residentevil.repositories.RoleRepository;
import residentevil.repositories.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public boolean registerUser(UserRegisterBindingModel userRegisterBindingModel) {
        if (!userRegisterBindingModel.getPassword().equals(userRegisterBindingModel.getConfirmPassword())) {
            return false;
        }

        User user = this.modelMapper.map(userRegisterBindingModel, User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));

        this.insertUserRoles();

        if (this.userRepository.count() == 0) {
            user.getAuthorities().add(this.roleRepository.findByAuthority("ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        } else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }

        this.userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username not found!");
        }

        return user;
    }

    @Override
    public boolean loginUser(UserLoginBindingModel userLoginBindingModel) {
        UserDetails userDetails = this.loadUserByUsername(userLoginBindingModel.getUsername());

        if (!userLoginBindingModel.getUsername().equals(userDetails.getUsername()) || !userLoginBindingModel.getPassword().equals(userDetails.getPassword())) {
            return false;
        }

        return true;
    }

    @Override
    public List<UserViewModel> extractAllUsers() {
        List<User> usersFromDb = this.userRepository.findAll();
        List<UserViewModel> userViewModels = new ArrayList<>();

        for (User user : usersFromDb) {
            UserViewModel userViewModel = this.modelMapper.map(user, UserViewModel.class);
            userViewModel.setRoles(user.getAuthorities().stream().map(UserRole::getAuthority).collect(Collectors.joining(", ")));

            userViewModels.add(userViewModel);
        }

        return userViewModels;
    }

    private void insertUserRoles() {
        if (this.roleRepository.count() == 0) {
            UserRole admin = new UserRole();
            admin.setAuthority("ADMIN");
            UserRole moderator = new UserRole();
            moderator.setAuthority("MODERATOR");
            UserRole user = new UserRole();
            user.setAuthority("USER");

            this.roleRepository.save(admin);
            this.roleRepository.save(moderator);
            this.roleRepository.save(user);
        }
    }
}
