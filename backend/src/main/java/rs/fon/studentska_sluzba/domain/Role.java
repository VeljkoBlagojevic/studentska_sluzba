package rs.fon.studentska_sluzba.domain;

import org.springframework.security.core.GrantedAuthority;

/**
 * Rola koju korisnik moze da zadobije pri autorizaciji na sistem.
 *
 * @author VeljkoBlagojevic
 */
public enum Role implements GrantedAuthority {

  /**
   * Obican koristnik sistema sa ogranicenim privilegijama
   */
  USER,
  /**
   * Administrator koji ima veliki niz mogucnosti i odobrenja u sistemu
   */
  ADMIN;

  @Override
  public String getAuthority() {
    return name();
  }
}
