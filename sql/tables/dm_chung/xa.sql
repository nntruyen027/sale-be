CREATE TABLE dm_chung.xa
(
    id     BIGSERIAL PRIMARY KEY,
    ten    VARCHAR(120) NOT NULL,
    ghiChu VARCHAR(500),

    tinhId BIGINT,

    CONSTRAINT fk_tinh FOREIGN KEY (tinhId) REFERENCES dm_chung.tinh (id) ON DELETE CASCADE
);
