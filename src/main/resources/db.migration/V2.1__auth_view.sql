-- ===============================
-- VIEW: v_file
-- ===============================
CREATE OR REPLACE VIEW v_file AS
SELECT *
FROM files;


-- ===============================
-- VIEW: auth.v_users_login
-- ===============================
CREATE OR REPLACE VIEW auth.v_users_login AS
SELECT u.id,
       u.username,
       u.password,
       u.ho_ten,
       u.avatar,
       u.is_active,

       COALESCE(
                       array_agg(DISTINCT r.code)
                       FILTER (WHERE r.code IS NOT NULL),
                       '{}'
       ) AS roles,

       COALESCE(
                       array_agg(DISTINCT p.code)
                       FILTER (WHERE p.code IS NOT NULL),
                       '{}'
       ) AS permissions
FROM auth.users u
         LEFT JOIN auth.user_roles ur
                   ON ur.user_id = u.id
         LEFT JOIN auth.roles r
                   ON r.id = ur.role_id
         LEFT JOIN auth.role_permissions rp
                   ON rp.role_id = r.id
         LEFT JOIN auth.permissions p
                   ON p.id = rp.permission_id
GROUP BY u.id;


-- ===============================
-- VIEW: auth.v_permission
-- ===============================
CREATE OR REPLACE VIEW auth.v_permission AS
SELECT id AS out_id,
       code
FROM auth.permissions;


-- ===============================
-- VIEW: auth.v_role
-- ===============================
CREATE OR REPLACE VIEW auth.v_role AS
SELECT r.id,
       r.name,
       r.code,

       COALESCE(
                       array_agg(p.code ORDER BY p.code)
                       FILTER (WHERE p.id IS NOT NULL),
                       '{}'
       ) AS permissions
FROM auth.roles r
         LEFT JOIN auth.role_permissions rp
                   ON r.id = rp.role_id
         LEFT JOIN auth.permissions p
                   ON rp.permission_id = p.id
GROUP BY r.id,
         r.name,
         r.code;


-- ===============================
-- VIEW: auth.v_thong_tin_auth
-- ===============================
CREATE OR REPLACE VIEW auth.v_thong_tin_auth AS
SELECT u.id,
       u.username,
       u.email,
       u.password,

       -- ===== DANH SÁCH ROLE CODE =====
       COALESCE(
                       array_agg(DISTINCT r.code)
                       FILTER (WHERE r.code IS NOT NULL),
                       ARRAY []::VARCHAR[]
       ) AS roles,

       -- ===== DANH SÁCH PERMISSION CODE =====
       COALESCE(
                       array_agg(DISTINCT p.code)
                       FILTER (WHERE p.code IS NOT NULL),
                       ARRAY []::VARCHAR[]
       ) AS permissions
FROM auth.users u
         LEFT JOIN auth.user_roles ur
                   ON ur.user_id = u.id
         LEFT JOIN auth.roles r
                   ON r.id = ur.role_id
         LEFT JOIN auth.role_permissions rp
                   ON rp.role_id = r.id
         LEFT JOIN auth.permissions p
                   ON p.id = rp.permission_id
GROUP BY u.id;


-- ===============================
-- VIEW: auth.v_users_full
-- ===============================
CREATE OR REPLACE VIEW auth.v_users_full AS
SELECT u.id,
       u.username,
       u.ho_ten,
       u.avatar,
       u.is_active,
       u.created_at,

       -- ===== DANH SÁCH ROLE CODE =====
       COALESCE(
                       array_agg(DISTINCT r.code)
                       FILTER (WHERE r.code IS NOT NULL),
                       ARRAY []::VARCHAR[]
       ) AS roles,

       -- ===== DANH SÁCH PERMISSION CODE =====
       COALESCE(
                       array_agg(DISTINCT p.code)
                       FILTER (WHERE p.code IS NOT NULL),
                       ARRAY []::VARCHAR[]
       ) AS permissions,

       u.email
FROM auth.users u
         LEFT JOIN auth.user_roles ur
                   ON ur.user_id = u.id
         LEFT JOIN auth.roles r
                   ON r.id = ur.role_id
         LEFT JOIN auth.role_permissions rp
                   ON rp.role_id = r.id
         LEFT JOIN auth.permissions p
                   ON p.id = rp.permission_id
GROUP BY u.id;
