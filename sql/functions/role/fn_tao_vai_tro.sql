-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_tao_vai_tro;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_tao_vai_tro(
    p_name VARCHAR(500),
    p_code VARCHAR(50)
)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result json;
BEGIN
    -- 1️⃣ Kiểm tra role đã tồn tại chưa
    IF EXISTS (
        SELECT 1
        FROM auth.roles
        WHERE code = p_code
    ) THEN
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
$$;
