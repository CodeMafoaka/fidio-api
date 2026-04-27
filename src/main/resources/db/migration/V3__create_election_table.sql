CREATE TABLE election (
                          id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                          title VARCHAR(255),
                          start_at TIMESTAMP,
                          end_at TIMESTAMP,
                          created_at TIMESTAMP DEFAULT NOW()
);
