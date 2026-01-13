DROP FUNCTION IF EXISTS auth.fn_cap_nhat_thong_tin_nguoi_dung;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_thong_tin_nguoi_dung(
    p_user_id BIGINT,
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN
)
    RETURNS SETOF auth.v_users_full
    LANGUAGE plpgsql
AS
$$
BEGIN
    IF EXISTS (SELECT 1 FROM auth.thong_tin_nguoi_dung WHERE user_id = p_user_id) THEN
        UPDATE auth.thong_tin_nguoi_dung
        SET ngay_sinh        = p_ngay_sinh,
            la_nam           = p_la_nam
        WHERE user_id = p_user_id;
    ELSE
        INSERT INTO auth.thong_tin_nguoi_dung(user_id, ngay_sinh, la_nam)
        VALUES (p_user_id, p_ngay_sinh);
    END IF;

    RETURN QUERY
        SELECT *
        FROM auth.v_users_full
        WHERE id = p_user_id;
END;
$$;
