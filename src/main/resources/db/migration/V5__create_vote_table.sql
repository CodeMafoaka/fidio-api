CREATE TABLE vote (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                      candidate_id UUID,
                      created_at TIMESTAMP DEFAULT NOW(),
                      CONSTRAINT unique_vote UNIQUE (election_id)
);
