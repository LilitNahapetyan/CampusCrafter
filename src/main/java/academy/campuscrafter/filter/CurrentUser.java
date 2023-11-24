package academy.campuscrafter.filter;

import academy.campuscrafter.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUser {
    private UUID id;
    private String name;
    private String surname;
    private String email;
    private Role role;
}