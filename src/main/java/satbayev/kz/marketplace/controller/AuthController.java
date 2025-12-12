package satbayev.kz.marketplace.controller;

import lombok.RequiredArgsConstructor;
import satbayev.kz.marketplace.dto.auth.LoginRequestDto;
import satbayev.kz.marketplace.dto.auth.LoginResponseDto;
import satbayev.kz.marketplace.service.auth.LoginService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto) {
        LoginResponseDto authentication = loginService.authenticate(loginRequestDto);
        return ResponseEntity.ok(authentication);
    }
}

