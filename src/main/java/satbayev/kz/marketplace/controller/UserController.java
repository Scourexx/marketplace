package satbayev.kz.marketplace.controller;

import lombok.RequiredArgsConstructor;
import satbayev.kz.marketplace.dto.PasswordChangeRequest;
import satbayev.kz.marketplace.dto.UserAccountDto;
import satbayev.kz.marketplace.dto.UserDto;
import satbayev.kz.marketplace.service.auth.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getUserById(id));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<UserAccountDto> createUser(@RequestBody UserAccountDto userAccount) {
        return ResponseEntity.ok(userService.createUser(userAccount));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserAccountDto> updateUser(@PathVariable Long id, @RequestBody UserDto userDto) {
        try {
            return ResponseEntity.ok(userService.updateUser(id, userDto));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/password")
    public void changePassword(@PathVariable Long id, @RequestBody PasswordChangeRequest request) {
        userService.changePassword(id, request.getNewPassword(), request.getOldPassword());
    }


    @PatchMapping("/{id}/role-admin")
    public ResponseEntity<Void> changeRoleToAdmin(@PathVariable Long id,@RequestBody String role) {
        try {
            userService.changeUserRoleToAdmin(id,role);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
