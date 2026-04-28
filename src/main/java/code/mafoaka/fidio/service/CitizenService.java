package code.mafoaka.fidio.service;

import code.mafoaka.fidio.repository.CitizenRepository;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CitizenService {
  private final CitizenRepository repository;
  private final PasswordEncoder passwordEncoder;

  public List<CitizenEntity> createCitizens(List<CitizenEntity> toCreate) {
    toCreate.forEach(c -> c.setPassword(passwordEncoder.encode(c.getPassword())));
    return repository.saveAll(toCreate);
  }

  public List<CitizenEntity> getCitizens(String gid, UUID id) {
    if (gid != null) {
      return repository.findByGid(gid).map(List::of).orElse(List.of());
    }
    if (id != null) {
      return repository.findById(id).map(List::of).orElse(List.of());
    }
    return repository.findAll();
  }

  public List<CitizenEntity> updateCitizens(List<CitizenEntity> toUpdate) {
    return toUpdate.stream()
        .map(
            citizen -> {
              CitizenEntity existing = repository.findById(citizen.getId()).orElseThrow();
              if (citizen.getFirstName() != null) {
                existing.setFirstName(citizen.getFirstName());
              }
              if (citizen.getLastName() != null) {
                existing.setLastName(citizen.getLastName());
              }
              // gid and password should not be updated through this endpoint usually
              return repository.save(existing);
            })
        .toList();
  }

  public CitizenEntity getByGid(String gid) {
    return repository.findByGid(gid).orElseThrow();
  }
}
