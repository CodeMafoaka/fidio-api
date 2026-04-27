CREATE TABLE election_candidate (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                                    gid VARCHAR(100),
                                    description TEXT
);
