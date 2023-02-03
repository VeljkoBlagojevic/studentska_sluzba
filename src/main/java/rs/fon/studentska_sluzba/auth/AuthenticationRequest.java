package rs.fon.studentska_sluzba.auth;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthenticationRequest {

  private String username;
  private String password;
}
