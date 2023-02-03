package rs.fon.studentska_sluzba.auth;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class AuthenticationResponse {

  private String token;
}
