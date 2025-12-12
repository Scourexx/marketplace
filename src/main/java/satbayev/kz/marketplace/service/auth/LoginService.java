package satbayev.kz.marketplace.service.auth;

import satbayev.kz.marketplace.dto.auth.LoginRequestDto;
import satbayev.kz.marketplace.dto.auth.LoginResponseDto;

public interface LoginService {
    LoginResponseDto authenticate(LoginRequestDto request);
}
