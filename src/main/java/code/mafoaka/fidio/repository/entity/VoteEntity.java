package code.mafoaka.fidio.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"vote\"")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VoteEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "election_id")
  private ElectionEntity election;

  @ManyToOne
  @JoinColumn(name = "candidate_id")
  private CandidateEntity candidate;

  @ManyToOne
  @JoinColumn(name = "voter_id")
  private CitizenEntity voter;
}
