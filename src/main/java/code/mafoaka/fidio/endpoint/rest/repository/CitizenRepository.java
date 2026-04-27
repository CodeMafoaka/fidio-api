package code.mafoaka.fidio.endpoint.rest.repository;

import code.mafoaka.fidio.endpoint.rest.entity.entity.Citizen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CitizenRepository extends JpaRepository<Citizen, UUID> {

    Optional<Citizen> findByGid(String gid);

    boolean existsByGid(String gid);
}
