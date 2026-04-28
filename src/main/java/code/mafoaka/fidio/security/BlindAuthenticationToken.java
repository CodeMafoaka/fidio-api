package code.mafoaka.fidio.security;

import java.util.Collection;
import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

@Getter
public class BlindAuthenticationToken extends AbstractAuthenticationToken {
  private final String message;
  private final String signature;
  private final String electionId;

  public BlindAuthenticationToken(
      String electionId,
      String message,
      String signature,
      Collection<? extends GrantedAuthority> authorities) {
    super(authorities);
    this.electionId = electionId;
    this.message = message;
    this.signature = signature;
    setAuthenticated(true);
  }

  @Override
  public Object getCredentials() {
    return signature;
  }

  @Override
  public Object getPrincipal() {
    return message;
  }
}
