package code.mafoaka.fidio.endpoint.rest.service;

import code.mafoaka.fidio.endpoint.rest.entity.entity.Election;
import code.mafoaka.fidio.endpoint.rest.repository.ElectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ElectionService {

    @Autowired
    private ElectionRepository electionRepository;

    public Election createElection(Election election) {
        return electionRepository.save(election);
    }
}
