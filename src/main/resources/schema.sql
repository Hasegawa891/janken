CREATE TABLE users (
    id IDENTITY,
    name VARCHAR NOT NULL
);
CREATE TABLE matches (
    id IDENTITY,
    user1 INT NOT NULL,
    user2 INT NOT NULL,
    user1Hand VARCHAR,
    user2Hand VARCHAR,
    isActive BOOLEAN
);

CREATE TABLE matchinfo (
    id IDENTITY,
    user1 INT NOT NULL,
    user2 INT NOT NULL,
    user1Hand VARCHAR,
    user2Hand VARCHAR,
    isActive BOOLEAN
);
