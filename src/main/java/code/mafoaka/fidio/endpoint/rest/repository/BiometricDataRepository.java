package code.mafoaka.fidio.endpoint.rest.repository;

import code.mafoaka.fidio.endpoint.rest.entity.entity.BiometricData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BiometricDataRepository extends JpaRepository<BiometricData, UUID> {

    List<BiometricData> findByCitizenId(UUID citizenId);
}
