DROP TABLE IF EXISTS dm_chung.nghe_nghiep;

CREATE TABLE dm_chung.nghe_nghiep
(
    id        BIGSERIAL PRIMARY KEY,
    ten       VARCHAR(255) NOT NULL,

    moTa      TEXT,
    thuTu     INT       DEFAULT 0,

    isActive  BOOLEAN   DEFAULT TRUE,
    createdAt TIMESTAMP DEFAULT now()
);
