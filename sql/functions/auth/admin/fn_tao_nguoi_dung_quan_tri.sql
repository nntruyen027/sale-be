DROP FUNCTION IF EXISTS auth.fn_tao_nguoi_dung_quan_tri;

CREATE OR REPLACE FUNCTION auth.fn_tao_nguoi_dung_quan_tri(
    p_username VARCHAR(120),
    p_ho_ten TEXT,
    p_password TEXT,
    p_avatar TEXT,
    p_email VARCHAR
)
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
                           email,
                           hoTen)
    VALUES (p_username,
            p_password,
            p_avatar,
            p_email,
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
$$;
