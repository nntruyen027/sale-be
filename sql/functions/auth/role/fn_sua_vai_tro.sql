-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_sua_vai_tro;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_sua_vai_tro(
    p_id   BIGINT,        -- ID vai trò
    p_name VARCHAR(500),  -- Tên mới
    p_code VARCHAR(50)    -- Mã mới
)
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
    IF EXISTS (
        SELECT 1
        FROM auth.roles
        WHERE code = p_code
          AND id <> p_id
    ) THEN
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
    WHERE v.out_id = p_id
    LIMIT 1;

    RETURN v_result;
END;
$$;
