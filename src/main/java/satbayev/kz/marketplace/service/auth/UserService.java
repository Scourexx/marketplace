package satbayev.kz.marketplace.service.auth;

import satbayev.kz.marketplace.dto.UserAccountDto;
import satbayev.kz.marketplace.dto.UserDto;

import java.util.List;

public interface UserService {
    void changePassword(Long userId, String newPassword, String oldPassword);

    void changeUserRoleToAdmin(Long userId,String role) ;

    UserAccountDto createUser(UserAccountDto userAccount);

    UserAccountDto updateUser(Long userId, UserDto userDto);

    void deleteUser(Long userId);

    List<UserDto> getAllUsers();

    UserDto getUserById(Long userId);
}
