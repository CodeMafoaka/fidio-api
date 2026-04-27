package code.mafoaka.fidio.endpoint.rest.service;

import code.mafoaka.fidio.endpoint.rest.entity.entity.Citizen;
import code.mafoaka.fidio.endpoint.rest.repository.CitizenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CitizenService {

    @Autowired
    private CitizenRepository citizenRepository;

    public Citizen createCitizen(Citizen citizen) {
        return citizenRepository.save(citizen);
    }

    public List<Citizen> getAll() {
        return citizenRepository.findAll();
    }
}
