package code.mafoaka.fidio.service;

import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.security.JwtTokenUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final CitizenService citizenService;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenUtil jwtTokenUtil;

  public String login(String gid, String password) {
    CitizenEntity citizen = citizenService.getByGid(gid);
    if (passwordEncoder.matches(password, citizen.getPassword())) {
      return jwtTokenUtil.generateToken(gid, citizen.getRole().name());
    }
    throw new RuntimeException("Invalid credentials");
  }

  public CitizenEntity register(CitizenEntity citizen) {
    return citizenService.createCitizens(java.util.List.of(citizen)).get(0);
  }
}
