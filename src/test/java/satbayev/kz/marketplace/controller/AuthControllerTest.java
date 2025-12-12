package satbayev.kz.marketplace.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import satbayev.kz.marketplace.dto.auth.LoginRequestDto;
import satbayev.kz.marketplace.dto.auth.LoginResponseDto;
import satbayev.kz.marketplace.service.auth.LoginService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthControllerTest {

    @Mock
    private LoginService loginService;

    @InjectMocks
    private AuthController authController;

    private LoginRequestDto loginRequest;
    private LoginResponseDto loginResponse;

    @BeforeEach
    void setUp() {
        loginRequest = new LoginRequestDto();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("password123");

        loginResponse = LoginResponseDto.builder()
                .userId(1L)
                .email("test@example.com")
                .firstName("John")
                .lastName("Doe")
                .role("USER")
                .build();
    }

    @Test
    void testLogin_Success() {
        when(loginService.authenticate(any(LoginRequestDto.class))).thenReturn(loginResponse);

        ResponseEntity<LoginResponseDto> response = authController.login(loginRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(loginResponse.getEmail(), response.getBody().getEmail());
        assertEquals(loginResponse.getRole(), response.getBody().getRole());
        verify(loginService, times(1)).authenticate(loginRequest);
    }

    @Test
    void testLogin_WithNullRequest() {
        when(loginService.authenticate(null)).thenThrow(new RuntimeException("Invalid credentials"));

        assertThrows(RuntimeException.class, () -> {
            authController.login(null);
        });

        verify(loginService, times(1)).authenticate(null);
    }
}

