package code.mafoaka.fidio.repository;

import code.mafoaka.fidio.repository.entity.BlindSignatureRequestEntity;
import code.mafoaka.fidio.repository.entity.CitizenEntity;
import code.mafoaka.fidio.repository.entity.ElectionEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlindSignatureRequestRepository
    extends JpaRepository<BlindSignatureRequestEntity, UUID> {
  Optional<BlindSignatureRequestEntity> findByElectionAndCitizen(
      ElectionEntity election, CitizenEntity citizen);
}
