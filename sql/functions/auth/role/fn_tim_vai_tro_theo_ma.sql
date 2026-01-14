-- Xóa function nếu đã tồn tại
DROP FUNCTION IF EXISTS auth.fn_lay_vai_tro_theo_ma;

-- Tạo function mới
CREATE OR REPLACE FUNCTION auth.fn_lay_vai_tro_theo_ma(
    p_code VARCHAR(50)
)
    RETURNS json
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result json;
BEGIN
    -- 1️⃣ Kiểm tra role tồn tại
    IF NOT EXISTS (
        SELECT 1
        FROM auth.roles
        WHERE code = p_code
    ) THEN
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
$$;
