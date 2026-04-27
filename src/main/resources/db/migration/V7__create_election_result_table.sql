CREATE TABLE election_result (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                                 total_vote INTEGER DEFAULT 0,
                                 updated_at TIMESTAMP DEFAULT NOW()
);
