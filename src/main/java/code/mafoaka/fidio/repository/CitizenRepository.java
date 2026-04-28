package code.mafoaka.fidio.repository;

import code.mafoaka.fidio.repository.entity.CitizenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitizenRepository extends JpaRepository<CitizenEntity, UUID> {
  Optional<CitizenEntity> findByGid(String gid);
}
