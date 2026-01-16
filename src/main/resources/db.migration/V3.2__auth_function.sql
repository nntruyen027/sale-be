CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_nguoi_dung(p_user_id bigint, p_ho_ten character varying,
                                                       p_avatar character varying, p_is_active boolean DEFAULT true)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result JSONB;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'User ID % không tồn tại', p_user_id;
    END IF;

    UPDATE auth.users
    SET hoTen    = p_ho_ten,
        avatar   = p_avatar,
        isActive = p_is_active
    WHERE id = p_user_id;

    -- Trả về user dạng JSON
    SELECT to_jsonb(v)
    INTO v_result
    FROM auth.v_users_full v
    WHERE v.id = p_user_id
    LIMIT 1;

    RETURN v_result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_thong_tin_quan_tri_vien(p_id bigint, p_avatar text, p_ho_ten text)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_re jsonb;
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM auth.v_users_full a
                   WHERE a.id = p_id) THEN
        RAISE EXCEPTION 'Quản trị viên với id % không tồn tại', p_id;
    END IF;

    UPDATE auth.users
    SET avatar = p_avatar,
        hoTen  = p_ho_ten
    WHERE id = p_id;

    SELECT to_jsonb(a)
    into v_re
    FROM auth.v_users_full a
    WHERE a.id = p_id
    LIMIT 1;

    return v_re;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_co_ton_tai_vai_tro_theo_ma(p_code character varying)
    RETURNS boolean
    LANGUAGE plpgsql
AS
$$
BEGIN
    -- Kiểm tra nếu tồn tại vai trò với code tương ứng
    IF EXISTS (SELECT 1
               FROM auth.roles
               WHERE code = p_code) THEN
        RETURN TRUE;
    END IF;

    RETURN FALSE;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_dem_tat_ca_quyen(p_search character varying)
    RETURNS bigint
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count BIGINT;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM auth.v_permission p
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(p.code)) LIKE '%' || unaccent(lower(p_search)) || '%';

    RETURN v_count;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_dem_tat_ca_vai_tro(p_search character varying)
    RETURNS bigint
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count BIGINT;
BEGIN
    SELECT COUNT(*)
    INTO v_count
    FROM auth.v_role r
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(r.name)) LIKE '%' || unaccent(lower(p_search)) || '%'
       OR unaccent(lower(r.code)) LIKE '%' || unaccent(lower(p_search)) || '%';

    RETURN v_count;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_doi_mat_khau(p_user_id bigint, p_mat_khau_moi text)
    RETURNS boolean
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF not EXISTS(SELECT 1 FROM auth.users where id = p_user_id) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_user_id;
    end if;

    UPDATE auth.users
    SET password = p_mat_khau_moi
    WHERE id = p_user_id;


    RETURN TRUE;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_kiem_tra_ton_tai_permission_theo_code(p_code character varying)
    RETURNS boolean
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF EXISTS(SELECT 1 FROM auth.permissions WHERE code = p_code) THEN
        RETURN TRUE;
    end if;
    RETURN FALSE;
end;

$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_ds_nguoi_dung(p_search character varying DEFAULT NULL::character varying,
                                                     p_page integer DEFAULT 1, p_limit integer DEFAULT 10)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_offset      INT := (p_page - 1) * p_limit;
    v_total       BIGINT;
    v_total_pages INT;
    v_data        JSONB;
BEGIN
    -- Tổng số bản ghi
    SELECT COUNT(*)
    INTO v_total
    FROM auth.users u
    WHERE (
              p_search IS NULL OR p_search = ''
                  OR unaccent(lower(u.hoTen)) LIKE '%' || unaccent(lower(p_search)) || '%'
                  OR u.email ILIKE '%' || p_search || '%'
                  OR u.username ILIKE '%' || p_search || '%'
              );

    v_total_pages := util.fn_tinh_total_page(v_total, p_limit);

    -- Data
    SELECT COALESCE(jsonb_agg(to_jsonb(u)), '[]'::jsonb)
    INTO v_data
    FROM auth.v_users_full u
    WHERE (
              p_search IS NULL OR p_search = ''
                  OR unaccent(lower(u.ho_ten)) LIKE '%' || unaccent(lower(p_search)) || '%'
                  OR u.email ILIKE '%' || p_search || '%'
                  OR u.username ILIKE '%' || p_search || '%'
              )
    LIMIT p_limit OFFSET v_offset;

    RETURN jsonb_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_nguoi_dung_theo_id(p_id bigint)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result JSONB;
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM auth.users
                   WHERE id = p_id) THEN
        RAISE EXCEPTION 'Người dùng với id % không tồn tại', p_id;
    END IF;

    SELECT to_jsonb(u)
    INTO v_result
    FROM auth.v_users_full u
    WHERE u.id = p_id
    LIMIT 1;

    RETURN v_result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_nguoi_dung_theo_username(p_username character varying)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_re jsonb;
BEGIN

    SELECT to_jsonb(u)
    into v_re
    FROM auth.v_users_full u
    WHERE username = p_username
    LIMIT 1;

    return v_re;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_permission_theo_code(p_code character varying, p_offset integer DEFAULT 0,
                                                            p_size integer DEFAULT 10)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_total BIGINT;
    v_data  json;
BEGIN
    -- Kiểm tra tồn tại
    SELECT COUNT(*)
    INTO v_total
    FROM auth.permissions
    WHERE code = p_code;

    IF v_total = 0 THEN
        RAISE EXCEPTION 'Permission với mã % không tồn tại.', p_code;
    END IF;

    -- Lấy data
    SELECT COALESCE(json_agg(to_jsonb(p)), '[]'::json)
    INTO v_data
    FROM (SELECT *
          FROM auth.v_permission
          WHERE code = p_code
          OFFSET p_offset LIMIT p_size) p;

    -- Trả JSON phân trang
    RETURN json_build_object(
            'data', v_data,
            'page', p_offset + 1,
            'size', p_size,
            'totalPages', CEIL(v_total::numeric / p_size),
            'totalElements', v_total
           );
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_permission_theo_user(p_user_id bigint)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    result json;
BEGIN
    SELECT COALESCE(
                   json_agg(DISTINCT p.code),
                   '[]'::json
           )
    INTO result
    FROM auth.user_roles ur
             JOIN auth.role_permissions rp ON ur.role_id = rp.role_id
             JOIN auth.permissions p ON rp.permission_id = p.id
    WHERE ur.user_id = p_user_id;

    RETURN result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_quan_tri_theo_username(p_username character varying)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_result jsonb;
BEGIN
    IF NOT EXISTS (select 1 from auth.users u where u.username = p_username) THEN
        RAISE EXCEPTION 'Quản trị viên với tên đăng nhập: % không tồn tại.', p_username;
    END IF;

    SELECT to_jsonb(u)
    into v_result
    FROM auth.v_users_full u
    WHERE u.username = p_username
    LIMIT 1;

    return v_result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_quyen(p_search character varying, p_offset integer DEFAULT 0,
                                                    p_limit integer DEFAULT 10)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
declare
    result json;
BEGIN

    SELECT COALESCE(
                   json_agg(p),
                   '[]'::json
           )
    into result
    FROM auth.v_permission p
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(p.code)) LIKE '%' || unaccent(lower(p_search)) || '%'
    ORDER BY p.code
    OFFSET p_offset LIMIT p_limit;

    return result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_tat_ca_vai_tro(p_search character varying, p_page integer DEFAULT 1,
                                                      p_size integer DEFAULT 10)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_offset      INT := (p_page - 1) * p_size;
    v_total       BIGINT;
    v_total_pages INT;
    v_data        json;
BEGIN
    -- Tổng số bản ghi
    SELECT COUNT(*)
    INTO v_total
    FROM auth.v_role r
    WHERE p_search IS NULL
       OR p_search = ''
       OR unaccent(lower(r.name)) LIKE '%' || unaccent(lower(p_search)) || '%'
       OR unaccent(lower(r.code)) LIKE '%' || unaccent(lower(p_search)) || '%';

    v_total_pages := CEIL(v_total::NUMERIC / p_size);

    -- Dữ liệu trang hiện tại
    SELECT COALESCE(json_agg(to_jsonb(r)), '[]'::json)
    INTO v_data
    FROM (SELECT *
          FROM auth.v_role r
          WHERE p_search IS NULL
             OR p_search = ''
             OR unaccent(lower(r.name)) LIKE '%' || unaccent(lower(p_search)) || '%'
             OR unaccent(lower(r.code)) LIKE '%' || unaccent(lower(p_search)) || '%'
          ORDER BY r.name
          OFFSET v_offset LIMIT p_size) r;

    -- Trả đúng format PageResponse
    RETURN json_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_size,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_thong_tin_auth_theo_id(p_id bigint)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_re jsonb;
BEGIN
    SELECT to_jsonb(u) into v_re FROM auth.v_thong_tin_auth u WHERE u.id = p_id LIMIT 1;
    return v_re;
end;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_thong_tin_auth_theo_username(p_username character varying)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
declare
    v_re jsonb;
BEGIN
    SELECT to_jsonb(u) into v_re FROM auth.v_thong_tin_auth u WHERE u.username = p_username LIMIT 1;
    return v_re;
end;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_lay_vai_tro_theo_ma(p_code character varying)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result json;
BEGIN
    -- 1️⃣ Kiểm tra role tồn tại
    IF NOT EXISTS (SELECT 1
                   FROM auth.roles
                   WHERE code = p_code) THEN
        RAISE EXCEPTION 'Vai trò với mã % không tồn tại', p_code;
    END IF;

    -- 2️⃣ Trả role theo mã (JSON object)
    SELECT to_jsonb(r)
    INTO v_result
    FROM auth.v_role r
    WHERE r.code = p_code
    LIMIT 1;

    RETURN coalesce(v_result, '{}'::json);
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_nguoi_dung_co_vai_tro(p_user_id bigint, p_role_code character varying)
    RETURNS boolean
    LANGUAGE plpgsql
AS
$$
BEGIN
    RETURN EXISTS (SELECT 1
                   FROM auth.user_roles ur
                            JOIN auth.roles r ON r.id = ur.role_id
                   WHERE ur.user_id = p_user_id
                     AND r.code = p_role_code);
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_phan_quyen_cho_vai_tro(p_role_id bigint, p_permission_codes text[])
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_permission_id BIGINT;
    v_code          TEXT;
    v_result        json;
BEGIN
    -- 1️⃣ Kiểm tra role tồn tại
    IF NOT EXISTS (SELECT 1 FROM auth.roles WHERE id = p_role_id) THEN
        RAISE EXCEPTION 'Vai trò với ID % không tồn tại', p_role_id;
    END IF;

    -- 2️⃣ Xóa quyền không còn trong danh sách mới
    DELETE
    FROM auth.role_permissions
    WHERE role_id = p_role_id
      AND permission_id NOT IN (SELECT id
                                FROM auth.permissions
                                WHERE code = ANY (p_permission_codes));

    -- 3️⃣ Thêm quyền mới nếu chưa có
    FOREACH v_code IN ARRAY p_permission_codes
        LOOP
            SELECT id
            INTO v_permission_id
            FROM auth.permissions
            WHERE code = v_code;

            IF v_permission_id IS NULL THEN
                RAISE EXCEPTION 'Permission với mã % không tồn tại', v_code;
            END IF;

            IF NOT EXISTS (SELECT 1
                           FROM auth.role_permissions
                           WHERE role_id = p_role_id
                             AND permission_id = v_permission_id) THEN
                INSERT INTO auth.role_permissions(role_id, permission_id)
                VALUES (p_role_id, v_permission_id);
            END IF;
        END LOOP;

    -- 4️⃣ Trả role dạng JSON
    SELECT to_jsonb(v)
    INTO v_result
    FROM auth.v_role v
    WHERE v.id = p_role_id
    LIMIT 1;

    RETURN v_result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_phan_vai_tro_cho_nguoi_dung(p_user_id bigint, p_role_codes text[])
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_role_id BIGINT;
    v_code    TEXT;
    v_result  jsonb;
BEGIN
    -- Kiểm tra user tồn tại
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'Người dùng với ID % không tồn tại', p_user_id;
    END IF;

    -- 1️⃣ Xóa các role hiện có mà không nằm trong danh sách mới
    DELETE
    FROM auth.user_roles
    WHERE user_id = p_user_id
      AND role_id NOT IN (SELECT id
                          FROM auth.roles
                          WHERE code = ANY (p_role_codes));

    -- 2️⃣ Duyệt từng role code trong danh sách mới để thêm nếu chưa có
    FOREACH v_code IN ARRAY p_role_codes
        LOOP
            -- Kiểm tra role tồn tại
            SELECT id
            INTO v_role_id
            FROM auth.roles
            WHERE code = v_code;

            IF v_role_id IS NULL THEN
                RAISE EXCEPTION 'Role với mã % không tồn tại', v_code;
            END IF;

            -- Thêm role nếu chưa gán cho user
            IF NOT EXISTS (SELECT 1
                           FROM auth.user_roles
                           WHERE user_id = p_user_id
                             AND role_id = v_role_id) THEN
                INSERT INTO auth.user_roles(user_id, role_id)
                VALUES (p_user_id, v_role_id);
            END IF;
        END LOOP;

    -- Trả về thông tin user
    SELECT to_jsonb(u) into v_result FROM auth.v_users_full u WHERE id = p_user_id LIMIT 1;
    return v_result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_sua_vai_tro(p_id bigint, p_name character varying, p_code character varying)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result json;
BEGIN
    -- 1️⃣ Kiểm tra role tồn tại
    IF NOT EXISTS (SELECT 1 FROM auth.roles WHERE id = p_id) THEN
        RAISE EXCEPTION 'Vai trò với ID % không tồn tại', p_id;
    END IF;

    -- 2️⃣ Kiểm tra code trùng role khác
    IF EXISTS (SELECT 1
               FROM auth.roles
               WHERE code = p_code
                 AND id <> p_id) THEN
        RAISE EXCEPTION 'Mã role % đã tồn tại cho role khác', p_code;
    END IF;

    -- 3️⃣ Cập nhật role
    UPDATE auth.roles
    SET name = p_name,
        code = p_code
    WHERE id = p_id;

    -- 4️⃣ Trả role sau cập nhật (JSON)
    SELECT to_jsonb(v)
    INTO v_result
    FROM auth.v_role v
    WHERE v.id = p_id
    LIMIT 1;

    RETURN v_result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_tao_nguoi_dung(p_username text, p_password text, p_ho_ten text,
                                                  p_avatar text DEFAULT NULL::text)
    RETURNS bigint
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_user_id BIGINT;
BEGIN
    IF EXISTS (SELECT 1 FROM auth.users WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % đã tồn tại', p_username;
    END IF;

    INSERT INTO auth.users(username, password, hoTen, avatar)
    VALUES (p_username, p_password, p_ho_ten, p_avatar)
    RETURNING id INTO v_user_id;

    RETURN v_user_id;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_tao_nguoi_dung_quan_tri(p_username character varying, p_ho_ten text, p_password text,
                                                           p_avatar text)
    RETURNS jsonb
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_user_id BIGINT;
    v_result  jsonb;
BEGIN
    /* ===============================
       1️⃣ KIỂM TRA USERNAME
       =============================== */
    IF EXISTS (SELECT 1
               FROM auth.users
               WHERE username = p_username) THEN
        RAISE EXCEPTION 'Username % đã tồn tại', p_username;
    END IF;

    /* ===============================
       4️⃣ TẠO USER
       =============================== */
    INSERT INTO auth.users(username,
                           password,
                           avatar,
                           hoTen)
    VALUES (p_username,
            p_password,
            p_avatar,
            p_ho_ten)
    RETURNING id INTO v_user_id;


    /* ===============================
       6️⃣ TRẢ VỀ RECORD USER TỪ VIEW
       =============================== */

    SELECT to_jsonb(u)
    into v_result
    FROM auth.v_users_full u
    WHERE id = v_user_id;

    return v_result;

END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_tao_permission(p_code character varying)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    result json;
BEGIN
    IF EXISTS (SELECT 1
               FROM auth.permissions
               WHERE code = p_code) THEN
        RAISE EXCEPTION 'Permission với mã % đã tồn tại', p_code;
    END IF;

    INSERT INTO auth.permissions(code)
    VALUES (p_code);

    SELECT to_jsonb(v)
    INTO result
    FROM auth.v_permission v
    WHERE v.code = p_code
    LIMIT 1;

    RETURN coalesce(result, '{}'::json);
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_tao_thong_tin_nguoi_dung(p_user_id bigint, p_ngay_sinh date, p_la_nam boolean)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN


    INSERT INTO auth.thong_tin_nguoi_dung(user_id, ngay_sinh, la_nam)
    VALUES (p_user_id, p_ngay_sinh, p_la_nam);
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_tao_vai_tro(p_name character varying, p_code character varying)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result json;
BEGIN
    -- 1️⃣ Kiểm tra role đã tồn tại chưa
    IF EXISTS (SELECT 1
               FROM auth.roles
               WHERE code = p_code) THEN
        RAISE EXCEPTION 'Vai trò % với mã % đã tồn tại', p_name, p_code;
    END IF;

    -- 2️⃣ Tạo role mới
    INSERT INTO auth.roles(code, name)
    VALUES (p_code, p_name);

    -- 3️⃣ Trả role vừa tạo (JSON)
    SELECT to_jsonb(v)
    INTO v_result
    FROM auth.v_role v
    WHERE v.code = p_code
    LIMIT 1;

    RETURN v_result;
END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_xoa_nguoi_dung(p_user_id bigint)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'User ID % không tồn tại', p_user_id;
    END IF;

    DELETE
    FROM auth.users
    WHERE id = p_user_id;

END;
$$ language plpgsql;


CREATE OR REPLACE FUNCTION auth.fn_xoa_vai_tro(p_id bigint)
    RETURNS void
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_count BIGINT;
BEGIN
    -- Kiểm tra role có tồn tại không
    IF NOT EXISTS (SELECT 1 FROM auth.roles WHERE id = p_id) THEN
        RAISE EXCEPTION 'Vai trò với ID % không tồn tại', p_id;
    END IF;

    -- Kiểm tra role đã được gán cho user chưa
    SELECT COUNT(*)
    INTO v_count
    FROM auth.user_roles
    WHERE role_id = p_id;

    IF v_count > 0 THEN
        RAISE EXCEPTION 'Vai trò đang được sử dụng bởi % người dùng, không thể xóa', v_count;
    END IF;

    -- Xóa role
    DELETE FROM auth.roles WHERE id = p_id;
END;
$$ language plpgsql;
