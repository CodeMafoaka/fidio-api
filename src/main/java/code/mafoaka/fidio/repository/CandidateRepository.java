package code.mafoaka.fidio.repository;

import code.mafoaka.fidio.repository.entity.CandidateEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {}
