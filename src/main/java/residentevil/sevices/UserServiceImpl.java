package residentevil.sevices;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import residentevil.dtos.UserDto;
import residentevil.entities.User;
import residentevil.entities.enums.UserRole;
import residentevil.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registerUser(UserDto userDto) {
        if (!userDto.getPassword().equals(userDto.getConfirmPassword())) {
            return false;
        }

        User user = this.modelMapper.map(userDto, User.class);

        if (this.userRepository.count() == 0) {
            user.setRole(UserRole.ADMIN);
        } else {
            user.setRole(UserRole.USER);
        }

        this.userRepository.save(user);
        return true;
    }

    @Override
    public boolean loginUser(UserDto userDto) {
        User userFromDb = this.userRepository.findByUsername(userDto.getUsername());

        if (userFromDb == null || !userFromDb.getPassword().equals(userDto.getPassword())) {
            return false;
        }

        return true;
    }

    @Override
    public UserDto getUserByUsername(String username) {
        return this.modelMapper.map(this.userRepository.findByUsername(username), UserDto.class);
    }
}
