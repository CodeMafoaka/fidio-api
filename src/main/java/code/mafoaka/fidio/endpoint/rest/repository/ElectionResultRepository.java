package code.mafoaka.fidio.endpoint.rest.repository;

import code.mafoaka.fidio.endpoint.rest.entity.entity.ElectionResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElectionResultRepository extends JpaRepository<ElectionResult, UUID> {

    ElectionResult findByElectionId(UUID electionId);
}