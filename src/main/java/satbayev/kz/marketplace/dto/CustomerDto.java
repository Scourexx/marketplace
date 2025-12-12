package satbayev.kz.marketplace.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link satbayev.kz.marketplace.domain.entity.Customer}
 */
@Value
public class CustomerDto implements Serializable {
    Long customerId;
    @Size(message = "El nombre debe tener al menos 2 caracteres", min = 2)
    @NotEmpty(message = "El nombre no puede estar vacio")
    String firstName;
    @Size(message = "El apellido debe tener al menos 2 caracteres", min = 2)
    @NotEmpty(message = "El apellido no puede estar vacio")
    String lastName;
    @Email(message = "El email debe ser valido")
    @NotEmpty(message = "El email no puede estar vacio")
    String email;
}