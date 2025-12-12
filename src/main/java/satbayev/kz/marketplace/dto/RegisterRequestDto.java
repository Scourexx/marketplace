package satbayev.kz.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequestDto {
    @Size(min = 2, message = "Имя должно содержать минимум 2 символа")
    @NotEmpty(message = "Имя не может быть пустым")
    private String firstName;

    @Size(min = 2, message = "Фамилия должна содержать минимум 2 символа")
    @NotEmpty(message = "Фамилия не может быть пустой")
    private String lastName;

    @Email(message = "Email должен быть валидным")
    @NotEmpty(message = "Email не может быть пустым")
    private String email;

    @Size(min = 6, message = "Пароль должен содержать минимум 6 символов")
    @NotEmpty(message = "Пароль не может быть пустым")
    private String password;
}

