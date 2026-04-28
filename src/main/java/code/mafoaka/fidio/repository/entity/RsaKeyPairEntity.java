package code.mafoaka.fidio.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "rsa_key_pair")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RsaKeyPairEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private UUID id;

  @OneToOne
  @JoinColumn(name = "election_id")
  private ElectionEntity election;

  @Column(nullable = false)
  private String modulus;

  @Column(name = "public_exponent", nullable = false)
  private String publicExponent;

  @Column(name = "private_exponent", nullable = false)
  private String privateExponent;
}
