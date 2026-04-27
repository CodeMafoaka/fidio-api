CREATE TABLE election_candidate_result (
                                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                           election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                                           candidate_gid VARCHAR(100),
                                           vote_amount INTEGER DEFAULT 0
);
