package satbayev.kz.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import satbayev.kz.marketplace.domain.entity.Customer;
import satbayev.kz.marketplace.domain.entity.Role;
import satbayev.kz.marketplace.domain.entity.UserAccount;
import satbayev.kz.marketplace.domain.repository.CustomerRepository;
import satbayev.kz.marketplace.domain.repository.RoleRepository;
import satbayev.kz.marketplace.domain.repository.UserAccountRepository;
import satbayev.kz.marketplace.dto.UserAccountDto;
import satbayev.kz.marketplace.dto.UserDto;
import satbayev.kz.marketplace.mapper.UserAccountMapper;
import satbayev.kz.marketplace.service.auth.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserAccountRepository userAccountRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserAccountMapper userAccountMapper;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;


    @Transactional
    public void changePassword(Long userId, String newPassword, String oldPassword) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        if (!passwordEncoder.matches(oldPassword, userAccount.getPassword())) {
            throw new IllegalArgumentException("Wrong password");
        }
        userAccount.setPassword(passwordEncoder.encode(newPassword));
        userAccountRepository.save(userAccount);
    }

    @Transactional
    public void changeUserRoleToAdmin(Long userId,String role) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        Role roles = roleRepository.findByUserAccountId(userAccount.getUserAccountId());
        roles.setRoleName(role);
        roleRepository.save(roles);
        userAccountRepository.save(userAccount);
    }

    @Transactional
    public UserAccountDto createUser(UserAccountDto userAccountDto) {
        UserAccount userAccount = userAccountMapper.toEntity(userAccountDto);
        userAccount.setPassword(passwordEncoder.encode(userAccount.getPassword()));
        return userAccountMapper.toDto(userAccountRepository.save(userAccount));
    }

    @Transactional
    public UserAccountDto updateUser(Long userId, UserDto updatedUserDto) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (updatedUserDto.getFirstName() != null) {
            userAccount.getCustomer().setFirstName(updatedUserDto.getFirstName());
        }
        if (updatedUserDto.getLastName() != null) {
            userAccount.getCustomer().setLastName(updatedUserDto.getLastName());
        }
        if (updatedUserDto.getEmail() != null) {
            userAccount.getCustomer().setEmail(updatedUserDto.getEmail());
        }
        userAccountRepository.save(userAccount);
        return userAccountMapper.toDto(userAccount);
    }


    @Transactional
    public void deleteUser(Long userId) {
        if (!userAccountRepository.existsById(userId)) {
            throw new IllegalArgumentException("User not found");
        }
        userAccountRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userAccountRepository.findAll()
                .stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public UserDto toUserDto(UserAccount entity) {
        UserDto userDto = new UserDto();
        userDto.setUserId(entity.getCustomer() != null ? entity.getCustomer().getCustomerId() : null);
        userDto.setFirstName(entity.getCustomer() != null ? entity.getCustomer().getFirstName() : null);
        userDto.setLastName(entity.getCustomer() != null ? entity.getCustomer().getLastName() : null);
        userDto.setEmail(entity.getCustomer() != null ? entity.getCustomer().getEmail() : null);
        return userDto;
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        UserAccount userAccount = userAccountRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        return toUserDto(userAccount);
    }
}