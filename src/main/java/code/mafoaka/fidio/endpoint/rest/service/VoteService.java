package code.mafoaka.fidio.endpoint.rest.service;

import code.mafoaka.fidio.endpoint.rest.entity.entity.Vote;
import code.mafoaka.fidio.endpoint.rest.repository.VoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoteService {

    @Autowired
    private VoteRepository voteRepository;

    public Vote createVote(Vote vote) {
        return voteRepository.save(vote);
    }

    public List<Vote> getVotesByElection(UUID electionId) {
        return voteRepository.findByElectionId(electionId);
    }
}
