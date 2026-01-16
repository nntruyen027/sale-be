DROP TABLE IF EXISTS auth.users;

CREATE TABLE auth.users
(
    id        BIGSERIAL PRIMARY KEY,
    username  VARCHAR(120) UNIQUE NOT NULL,
    password  VARCHAR(500)        NOT NULL,
    email     VARCHAR(500),

    ho_ten    VARCHAR(500)        NOT NULL,
    avatar    VARCHAR(500),

    isActive  BOOLEAN   DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT now()
);
