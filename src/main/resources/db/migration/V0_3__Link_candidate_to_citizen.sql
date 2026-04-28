ALTER TABLE "candidate"
ADD CONSTRAINT fk_candidate_citizen_gid
FOREIGN KEY (gid) REFERENCES "citizen"(gid);
