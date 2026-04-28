package code.mafoaka.fidio;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import code.mafoaka.fidio.repository.CitizenRepository;
import code.mafoaka.fidio.repository.ElectionRepository;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.repository.entity.Role;
import code.mafoaka.fidio.security.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ElectionIT {

  @Autowired private MockMvc mockMvc;
  @Autowired private ElectionRepository electionRepository;
  @Autowired private CitizenRepository citizenRepository;
  @Autowired private JwtTokenUtil jwtTokenUtil;
  @Autowired private PasswordEncoder passwordEncoder;

  private String adminToken;
  private String userToken;

  @BeforeEach
  void setUp() {
    electionRepository.deleteAll();
    citizenRepository.deleteAll();

    CitizenEntity admin =
        CitizenEntity.builder()
            .gid("ADMIN_GID")
            .firstName("Admin")
            .lastName("User")
            .password("password")
            .role(Role.ADMIN)
            .build();
    citizenRepository.save(admin);
    adminToken = jwtTokenUtil.generateToken(admin.getGid(), admin.getRole().name());

    CitizenEntity user =
        CitizenEntity.builder()
            .gid("USER_GID")
            .firstName("Regular")
            .lastName("User")
            .password("password")
            .role(Role.USER)
            .build();
    citizenRepository.save(user);
    userToken = jwtTokenUtil.generateToken(user.getGid(), user.getRole().name());
  }

  @Test
  void get_elections_ok() throws Exception {
    mockMvc
        .perform(get("/elections").header("Authorization", "Bearer " + userToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(0));

    electionRepository.save(
        ElectionEntity.builder()
            .title("Election 2026")
            .startAt(java.time.Instant.now())
            .endAt(java.time.Instant.now().plus(java.time.Duration.ofHours(10)))
            .build());

    mockMvc
        .perform(get("/elections").header("Authorization", "Bearer " + userToken))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(1))
        .andExpect(jsonPath("$[0].title").value("Election 2026"));
  }
}
