ALTER TABLE "vote" DROP CONSTRAINT vote_election_id_voter_id_key;
ALTER TABLE "vote" DROP COLUMN voter_id;
ALTER TABLE "vote" ADD COLUMN message TEXT NOT NULL;
ALTER TABLE "vote" ADD COLUMN signature TEXT NOT NULL;
ALTER TABLE "vote" ADD CONSTRAINT vote_election_id_message_key UNIQUE (election_id, message);

CREATE TABLE "rsa_key_pair" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    election_id UUID UNIQUE REFERENCES "election"(id),
    modulus TEXT NOT NULL,
    public_exponent TEXT NOT NULL,
    private_exponent TEXT NOT NULL
);

CREATE TABLE "blind_signature_request" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    election_id UUID REFERENCES "election"(id),
    citizen_id UUID REFERENCES "citizen"(id),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(election_id, citizen_id)
);
