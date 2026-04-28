package code.mafoaka.fidio.endpoint.rest.controller;

import code.mafoaka.fidio.endpoint.rest.api.AuthApi;
import code.mafoaka.fidio.endpoint.rest.model.*;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.Role;
import code.mafoaka.fidio.service.AuthService;
import code.mafoaka.fidio.service.CitizenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController implements AuthApi {
  private final AuthService authService;
  private final CitizenService citizenService;

  @Override
  public ResponseEntity<AuthResponse> login(LoginRequest loginRequest) {
    String token = authService.login(loginRequest.getGid(), loginRequest.getPassword());
    AuthResponse response = new AuthResponse();
    response.setToken(token);
    return ResponseEntity.ok(response);
  }

  @Override
  public ResponseEntity<CitizenWithToken> register(CreateCitizen createCitizen) {
    CitizenEntity entity =
        CitizenEntity.builder()
            .firstName(createCitizen.getFirstName())
            .lastName(createCitizen.getLastName())
            .gid(createCitizen.getGid())
            .password(createCitizen.getPassword())
            .role(Role.USER)
            .build();
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(toDtoWithToken(authService.register(entity)));
  }

  @Override
  public ResponseEntity<Citizen> whoami() {
    String gid = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return ResponseEntity.ok(toDto(citizenService.getByGid(gid)));
  }

  private Citizen toDto(CitizenEntity entity) {
    Citizen dto = new Citizen();
    dto.setId(entity.getId().toString());
    dto.setFirstName(entity.getFirstName());
    dto.setLastName(entity.getLastName());
    dto.setGid(entity.getGid());
    dto.setRole(
        entity.getRole() != null
            ? code.mafoaka.fidio.endpoint.rest.model.Role.fromValue(entity.getRole().name())
            : null);
    return dto;
  }

  private CitizenWithToken toDtoWithToken(CitizenEntity entity) {
    CitizenWithToken dto = new CitizenWithToken();
    dto.setId(entity.getId().toString());
    dto.setFirstName(entity.getFirstName());
    dto.setLastName(entity.getLastName());
    dto.setGid(entity.getGid());
    dto.setRole(
        entity.getRole() != null
            ? code.mafoaka.fidio.endpoint.rest.model.Role.fromValue(entity.getRole().name())
            : null);
    dto.setToken(authService.login(entity.getGid(), entity.getPassword()));
    return dto;
  }
}
