package code.mafoaka.fidio.endpoint.rest.repository;

import code.mafoaka.fidio.endpoint.rest.entity.entity.Election;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ElectionRepository extends JpaRepository<Election, UUID> {
}
