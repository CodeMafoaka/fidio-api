CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE citizen (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         gid VARCHAR(100) UNIQUE NOT NULL,
                         password VARCHAR(255)
);

CREATE TABLE election (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          title VARCHAR(255),
                          start_at TIMESTAMP,
                          end_at TIMESTAMP,
                          created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE election_candidate (
                                    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                    election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                                    gid VARCHAR(100),
                                    description TEXT
);

CREATE TABLE vote (
                      id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                      election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                      candidate_id UUID,
                      created_at TIMESTAMP DEFAULT NOW(),
                      CONSTRAINT unique_vote UNIQUE (election_id)
);

CREATE TABLE biometric_data (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                citizen_id UUID REFERENCES citizen(id) ON DELETE CASCADE,
                                type VARCHAR(50),
                                embedding FLOAT[],
                                created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE election_result (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                                 total_vote INTEGER DEFAULT 0,
                                 updated_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE election_candidate_result (
                                           id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                           election_id UUID REFERENCES election(id) ON DELETE CASCADE,
                                           candidate_gid VARCHAR(100),
                                           vote_amount INTEGER DEFAULT 0
);