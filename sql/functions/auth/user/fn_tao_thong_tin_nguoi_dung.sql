DROP FUNCTION IF EXISTS auth.fn_tao_thong_tin_nguoi_dung;
CREATE FUNCTION auth.fn_tao_thong_tin_nguoi_dung(
    p_user_id BIGINT,
    p_ngay_sinh DATE,
    p_la_nam BOOLEAN
)
    RETURNS VOID
    LANGUAGE plpgsql
AS
$$
BEGIN


    INSERT INTO auth.thong_tin_nguoi_dung(user_id, ngay_sinh, la_nam)
    VALUES (p_user_id, p_ngay_sinh, p_la_nam);
END;
$$;
