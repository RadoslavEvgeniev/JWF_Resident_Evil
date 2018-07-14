package residentevil.sevices;

import residentevil.dtos.UserDto;

public interface UserService {

    boolean registerUser(UserDto userDto);

    boolean loginUser(UserDto userDto);

    UserDto getUserByUsername(String username);
}
