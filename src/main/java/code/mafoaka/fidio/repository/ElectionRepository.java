package code.mafoaka.fidio.repository;

import code.mafoaka.fidio.repository.entity.ElectionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectionRepository extends JpaRepository<ElectionEntity, UUID> {}
