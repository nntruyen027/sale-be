CREATE TABLE auth.users
(
    id         BIGSERIAL PRIMARY KEY,
    username   VARCHAR(120) UNIQUE NOT NULL,
    password   VARCHAR(500)        NOT NULL,
    email      VARCHAR(500),

    ho_ten     VARCHAR(500)        NOT NULL,
    avatar     VARCHAR(500),

    is_active  BOOLEAN   DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT now()
);

CREATE TABLE auth.roles
(
    id   BIGSERIAL PRIMARY KEY,
    code VARCHAR(50) UNIQUE NOT NULL,
    name VARCHAR(500)
);

CREATE TABLE auth.permissions
(
    id   BIGSERIAL PRIMARY KEY,
    code VARCHAR(100) UNIQUE NOT NULL
);

CREATE TABLE auth.thong_tin_nguoi_dung
(
    user_id   BIGINT PRIMARY KEY,


    ngay_sinh DATE,
    la_nam    BOOLEAN,

    CONSTRAINT fk_profile_user FOREIGN KEY (user_id)
        REFERENCES auth.users (id)
        ON DELETE CASCADE
);

CREATE TABLE auth.user_roles
(
    user_id BIGINT REFERENCES auth.users (id) ON DELETE CASCADE,
    role_id BIGINT REFERENCES auth.roles (id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, role_id)
);

CREATE TABLE auth.role_permissions
(
    role_id       BIGINT REFERENCES auth.roles (id),
    permission_id BIGINT REFERENCES auth.permissions (id),
    PRIMARY KEY (role_id, permission_id)
);

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
