package satbayev.kz.marketplace.dto.auth;

import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
}
