DROP TABLE IF EXISTS files;

CREATE TABLE files
(
    id           BIGSERIAL PRIMARY KEY,
    file_name    TEXT,
    stored_name  TEXT,
    url          TEXT,
    content_type VARCHAR(255),
    size         BIGINT,
    user_id      BIGINT,
    uploaded_at  TIMESTAMP,

    CONSTRAINT fk_user_files FOREIGN KEY (user_id) REFERENCES auth.users (id) ON DELETE SET NULL
);


select auth.fn_lay_nguoi_dung_theo_username(p_username := 'superadmin');

