package satbayev.kz.marketplace.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import satbayev.kz.marketplace.domain.entity.Role;
import satbayev.kz.marketplace.domain.repository.CustomerRepository;
import satbayev.kz.marketplace.domain.repository.RoleRepository;
import satbayev.kz.marketplace.domain.repository.UserAccountRepository;
import satbayev.kz.marketplace.dto.auth.LoginRequestDto;
import satbayev.kz.marketplace.dto.auth.LoginResponseDto;
import satbayev.kz.marketplace.service.auth.LoginService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;
    private final UserAccountRepository userAccountRepository;

    @SneakyThrows
    @Transactional
    public LoginResponseDto authenticate(LoginRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
            var user = customerRepository.findByEmail(request.getEmail()).orElseThrow();

            var account = userAccountRepository.findByCustomerEmail(user.getEmail())
                    .orElseThrow(() -> new BadCredentialsException("User not found"));

            String firstRole = account.getRoles()
                    .stream()
                    .findFirst()
                    .map(Role::getRoleName)
                    .orElse("No Role");

            if (!passwordEncoder.matches(request.getPassword(), account.getPassword())) {
                throw new BadCredentialsException("Wrong password");
            }
            return LoginResponseDto.builder()
                    .userId(user.getCustomerId())
                    .email(user.getEmail())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .role(firstRole)
                    .build();
        } catch (Exception e) {
            throw e;
        }
    }
}
