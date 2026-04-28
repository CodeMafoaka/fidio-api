package code.mafoaka.fidio;

import code.mafoaka.fidio.repository.CitizenRepository;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.Role;
import code.mafoaka.fidio.service.CitizenService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminAccountBootstrap implements CommandLineRunner {

  private final CitizenService citizenService;
  private final CitizenRepository citizenRepository;

  @Value("${ADMIN_GID:}")
  private String adminGid;

  @Value("${ADMIN_PASSWORD:}")
  private String adminPassword;

  @Value("${ADMIN_FIRST_NAME:Admin}")
  private String adminFirstName;

  @Value("${ADMIN_LAST_NAME:User}")
  private String adminLastName;

  @Override
  public void run(String... args) {
    if (adminGid == null
        || adminGid.isEmpty()
        || adminPassword == null
        || adminPassword.isEmpty()) {
      log.info("Admin credentials not provided, skipping admin bootstrap.");
      return;
    }

    Optional<CitizenEntity> existingAdmin = citizenRepository.findByGid(adminGid);

    if (existingAdmin.isEmpty()) {
      log.info("Creating admin account with GID: {}", adminGid);
      CitizenEntity admin =
          CitizenEntity.builder()
              .gid(adminGid)
              .password(adminPassword)
              .firstName(adminFirstName)
              .lastName(adminLastName)
              .role(Role.ADMIN)
              .build();
      citizenService.createCitizens(List.of(admin));
    } else {
      CitizenEntity admin = existingAdmin.get();
      if (admin.getRole() != Role.ADMIN) {
        log.info("Updating existing citizen with GID: {} to ADMIN role", adminGid);
        admin.setRole(Role.ADMIN);
        citizenRepository.save(admin);
      } else {
        log.info("Admin account with GID: {} already exists.", adminGid);
      }
    }
  }
}
