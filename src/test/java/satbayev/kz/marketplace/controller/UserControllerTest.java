package satbayev.kz.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satbayev.kz.marketplace.dto.PasswordChangeRequest;
import satbayev.kz.marketplace.dto.UserAccountDto;
import satbayev.kz.marketplace.dto.UserDto;
import satbayev.kz.marketplace.service.auth.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserDto userDto;
    private UserAccountDto userAccountDto;

    @BeforeEach
    void setUp() {
        userDto = new UserDto(1L, "John", "Doe", "john.doe@example.com");
        userAccountDto = UserAccountDto.builder()
                .userAccountId(1L)
                .username("john.doe@example.com")
                .build();
    }

    @Test
    void testGetAllUsers() {
        List<UserDto> users = Arrays.asList(userDto);
        when(userService.getAllUsers()).thenReturn(users);

        List<UserDto> response = userController.getAllUsers();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(userDto, response.get(0));
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void testGetUserById_Success() {
        when(userService.getUserById(1L)).thenReturn(userDto);

        ResponseEntity<UserDto> response = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userDto, response.getBody());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserById_NotFound() {
        when(userService.getUserById(999L)).thenThrow(new IllegalArgumentException("User not found"));

        ResponseEntity<UserDto> response = userController.getUserById(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).getUserById(999L);
    }

    @Test
    void testCreateUser() {
        when(userService.createUser(any(UserAccountDto.class))).thenReturn(userAccountDto);

        ResponseEntity<UserAccountDto> response = userController.createUser(userAccountDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userAccountDto, response.getBody());
        verify(userService, times(1)).createUser(userAccountDto);
    }

    @Test
    void testUpdateUser_Success() {
        when(userService.updateUser(anyLong(), any(UserDto.class))).thenReturn(userAccountDto);

        ResponseEntity<UserAccountDto> response = userController.updateUser(1L, userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        verify(userService, times(1)).updateUser(1L, userDto);
    }

    @Test
    void testUpdateUser_NotFound() {
        when(userService.updateUser(anyLong(), any(UserDto.class)))
                .thenThrow(new IllegalArgumentException("User not found"));

        ResponseEntity<UserAccountDto> response = userController.updateUser(999L, userDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).updateUser(999L, userDto);
    }

    @Test
    void testDeleteUser_Success() {
        doNothing().when(userService).deleteUser(1L);

        ResponseEntity<Void> response = userController.deleteUser(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userService, times(1)).deleteUser(1L);
    }

    @Test
    void testDeleteUser_NotFound() {
        doThrow(new IllegalArgumentException("User not found")).when(userService).deleteUser(999L);

        ResponseEntity<Void> response = userController.deleteUser(999L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).deleteUser(999L);
    }

    @Test
    void testChangePassword() {
        PasswordChangeRequest request = new PasswordChangeRequest();
        request.setOldPassword("oldPassword");
        request.setNewPassword("newPassword");
        doNothing().when(userService).changePassword(anyLong(), anyString(), anyString());

        userController.changePassword(1L, request);

        verify(userService, times(1)).changePassword(1L, request.getNewPassword(), request.getOldPassword());
    }

    @Test
    void testChangeRoleToAdmin_Success() {
        doNothing().when(userService).changeUserRoleToAdmin(anyLong(), anyString());

        ResponseEntity<Void> response = userController.changeRoleToAdmin(1L, "ADMIN");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).changeUserRoleToAdmin(1L, "ADMIN");
    }

    @Test
    void testChangeRoleToAdmin_NotFound() {
        doThrow(new IllegalArgumentException("User not found"))
                .when(userService).changeUserRoleToAdmin(anyLong(), anyString());

        ResponseEntity<Void> response = userController.changeRoleToAdmin(999L, "ADMIN");

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userService, times(1)).changeUserRoleToAdmin(999L, "ADMIN");
    }
}

