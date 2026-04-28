package code.mafoaka.fidio.endpoint.rest.controller;

import code.mafoaka.fidio.endpoint.rest.api.CitizensApi;
import code.mafoaka.fidio.endpoint.rest.model.Citizen;
import code.mafoaka.fidio.endpoint.rest.model.CreateCitizen;
import code.mafoaka.fidio.endpoint.rest.model.UpdateCitizen;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.Role;
import code.mafoaka.fidio.service.CitizenService;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CitizenController implements CitizensApi {
  private final CitizenService service;

  @Override
  public ResponseEntity<List<Citizen>> createCitizen(List<CreateCitizen> createCitizen) {
    List<CitizenEntity> entities =
        createCitizen.stream()
            .map(
                c ->
                    CitizenEntity.builder()
                        .firstName(c.getFirstName())
                        .lastName(c.getLastName())
                        .gid(c.getGid())
                        .password(c.getPassword())
                        .role(Role.USER)
                        .build())
            .collect(Collectors.toList());
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(
            service.createCitizens(entities).stream()
                .map(this::toDto)
                .collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<List<Citizen>> getCitizens(String gid, String id) {
    UUID uuid = id != null ? UUID.fromString(id) : null;
    return ResponseEntity.ok(
        service.getCitizens(gid, uuid).stream().map(this::toDto).collect(Collectors.toList()));
  }

  @Override
  public ResponseEntity<List<Citizen>> updateCitizens(List<UpdateCitizen> updateCitizen) {
    List<CitizenEntity> entities =
        updateCitizen.stream()
            .map(
                c ->
                    CitizenEntity.builder()
                        .id(UUID.fromString(c.getId()))
                        .firstName(c.getFirstName())
                        .lastName(c.getLastName())
                        .build())
            .collect(Collectors.toList());
    return ResponseEntity.ok(
        service.updateCitizens(entities).stream().map(this::toDto).collect(Collectors.toList()));
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
}
