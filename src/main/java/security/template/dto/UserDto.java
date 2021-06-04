package security.template.dto;

import lombok.*;

import javax.persistence.Column;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class UserDto {
    private String firstName;
    private String secondName;
    private String userEmail;
    private String password;
    private String code = null;
    private Boolean deleted = false;
}
