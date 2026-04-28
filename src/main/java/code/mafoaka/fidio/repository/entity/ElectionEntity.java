package code.mafoaka.fidio.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "\"election\"")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectionEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  private String title;
  private Instant startAt;
  private Instant endAt;

  @CreationTimestamp private Instant createdAt;

  @OneToMany(mappedBy = "election")
  private List<CandidateEntity> candidates;
}
