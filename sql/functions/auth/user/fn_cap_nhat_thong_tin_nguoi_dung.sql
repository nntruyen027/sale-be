DROP FUNCTION IF EXISTS auth.fn_cap_nhat_thong_tin_nguoi_dung;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_thong_tin_nguoi_dung(
    p_user_id BIGINT,
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_result JSONB;
BEGIN
    -- Kiểm tra user tồn tại
    IF NOT EXISTS (SELECT 1 FROM auth.users WHERE id = p_user_id) THEN
        RAISE EXCEPTION 'User ID % không tồn tại', p_user_id;
    END IF;

    -- Update hoặc Insert
    IF EXISTS (
        SELECT 1
        FROM auth.thong_tin_nguoi_dung
        WHERE user_id = p_user_id
    ) THEN
        UPDATE auth.thong_tin_nguoi_dung
        SET ngay_sinh = p_ngay_sinh,
            la_nam    = p_la_nam
        WHERE user_id = p_user_id;
    ELSE
        INSERT INTO auth.thong_tin_nguoi_dung(user_id, ngay_sinh, la_nam)
        VALUES (p_user_id, p_ngay_sinh, p_la_nam);
    END IF;

    -- Trả về user full dạng JSON
    SELECT to_jsonb(v)
    INTO v_result
    FROM auth.v_users_full v
    WHERE v.id = p_user_id
    LIMIT 1;

    RETURN v_result;
END;
$$;
