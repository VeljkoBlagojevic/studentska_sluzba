package rs.fon.studentska_sluzba.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String username;
  private String password;
}
