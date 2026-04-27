package code.mafoaka.fidio.endpoint.rest.service;

import code.mafoaka.fidio.endpoint.rest.entity.entity.BiometricData;
import code.mafoaka.fidio.endpoint.rest.repository.BiometricDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BiometricService {

    @Autowired
    private BiometricDataRepository biometricRepository;

    public BiometricData save(BiometricData data) {
        return biometricRepository.save(data);
    }
}
