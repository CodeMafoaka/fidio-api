package code.mafoaka.fidio.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.Instant;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "blind_signature_request")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlindSignatureRequestEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "election_id")
  private ElectionEntity election;

  @ManyToOne
  @JoinColumn(name = "citizen_id")
  private CitizenEntity citizen;

  @CreationTimestamp private Instant createdAt;
}
