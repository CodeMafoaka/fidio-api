CREATE TABLE biometric_data (
                                id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                citizen_id UUID REFERENCES citizen(id) ON DELETE CASCADE,
                                type VARCHAR(50),
                                embedding FLOAT[],
                                created_at TIMESTAMP DEFAULT NOW()
);
