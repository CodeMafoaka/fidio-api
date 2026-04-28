package code.mafoaka.fidio;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

import code.mafoaka.fidio.repository.CitizenRepository;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.Role;
import code.mafoaka.fidio.service.CitizenService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AdminAccountBootstrapTest {

  @Mock private CitizenService citizenService;

  @Mock private CitizenRepository citizenRepository;

  @InjectMocks private AdminAccountBootstrap adminAccountBootstrap;

  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(adminAccountBootstrap, "adminGid", "admin-gid");
    ReflectionTestUtils.setField(adminAccountBootstrap, "adminPassword", "admin-password");
    ReflectionTestUtils.setField(adminAccountBootstrap, "adminFirstName", "Admin");
    ReflectionTestUtils.setField(adminAccountBootstrap, "adminLastName", "User");
  }

  @Test
  void run_shouldCreateAdmin_whenNotExists() {
    when(citizenRepository.findByGid("admin-gid")).thenReturn(Optional.empty());

    adminAccountBootstrap.run();

    verify(citizenService, times(1)).createCitizens(anyList());
    verify(citizenRepository, never()).save(any());
  }

  @Test
  void run_shouldUpdateRoleToAdmin_whenExistsButNotAdmin() {
    CitizenEntity existingUser = CitizenEntity.builder().gid("admin-gid").role(Role.USER).build();
    when(citizenRepository.findByGid("admin-gid")).thenReturn(Optional.of(existingUser));

    adminAccountBootstrap.run();

    verify(citizenService, never()).createCitizens(anyList());
    verify(citizenRepository, times(1)).save(argThat(citizen -> citizen.getRole() == Role.ADMIN));
  }

  @Test
  void run_shouldDoNothing_whenAdminAlreadyExists() {
    CitizenEntity existingAdmin = CitizenEntity.builder().gid("admin-gid").role(Role.ADMIN).build();
    when(citizenRepository.findByGid("admin-gid")).thenReturn(Optional.of(existingAdmin));

    adminAccountBootstrap.run();

    verify(citizenService, never()).createCitizens(anyList());
    verify(citizenRepository, never()).save(any());
  }

  @Test
  void run_shouldSkip_whenGidNotProvided() {
    ReflectionTestUtils.setField(adminAccountBootstrap, "adminGid", "");

    adminAccountBootstrap.run();

    verify(citizenRepository, never()).findByGid(anyString());
  }
}
