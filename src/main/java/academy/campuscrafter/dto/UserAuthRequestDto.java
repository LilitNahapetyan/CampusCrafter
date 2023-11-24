package academy.campuscrafter.dto;

import lombok.Data;

@Data
public class UserAuthRequestDto {
    private String email;
    private String password;
}
