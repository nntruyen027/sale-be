DROP TABLE IF EXISTS blog.chuyen_muc;

CREATE TABLE blog.chuyen_muc
(
    id         BIGSERIAL PRIMARY KEY,
    ten        VARCHAR(255)        NOT NULL,
    slug       VARCHAR(255) UNIQUE NOT NULL,
    "parentId" BIGINT REFERENCES blog.chuyen_muc (id) ON DELETE CASCADE
);

CREATE INDEX idx_chuyen_muc_parent
    ON blog.chuyen_muc ("parentId");
