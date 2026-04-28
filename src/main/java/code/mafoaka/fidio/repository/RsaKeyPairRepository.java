package code.mafoaka.fidio.repository;

import code.mafoaka.fidio.repository.entity.ElectionEntity;
import code.mafoaka.fidio.repository.entity.RsaKeyPairEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RsaKeyPairRepository extends JpaRepository<RsaKeyPairEntity, UUID> {
  Optional<RsaKeyPairEntity> findByElection(ElectionEntity election);
}
