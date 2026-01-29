DROP FUNCTION IF EXISTS config.fn_setup_home_sanphamgoiy;

CREATE FUNCTION config.fn_setup_home_sanphamgoiy(
    p_noi_dung varchar
)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
BEGIN


    IF EXISTS (SELECT 1
               FROM config.thong_tin_he_thong
               WHERE ten = 'home_sanphamgoiy') THEN
        UPDATE config.thong_tin_he_thong
        SET "noiDung" = p_noi_dung
        WHERE ten = 'home_sanphamgoiy';
    ELSE
        INSERT INTO config.thong_tin_he_thong (ten, "noiDung")
        VALUES ('home_sanphamgoiy', p_noi_dung);
    END IF;

    RETURN p_noi_dung;
END;
$$;
