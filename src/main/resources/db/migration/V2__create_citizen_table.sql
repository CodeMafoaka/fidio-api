CREATE TABLE citizen (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         first_name VARCHAR(100),
                         last_name VARCHAR(100),
                         gid VARCHAR(100) UNIQUE NOT NULL,
                         password VARCHAR(255)
);
