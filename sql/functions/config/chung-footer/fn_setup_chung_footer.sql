DROP FUNCTION IF EXISTS config.fn_setup_chung_footer;

CREATE FUNCTION config.fn_setup_chung_footer(
    p_noi_dung varchar
)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
BEGIN


    IF EXISTS (SELECT 1
               FROM config.thong_tin_he_thong
               WHERE ten = 'chung_footer') THEN
        UPDATE config.thong_tin_he_thong
        SET "noiDung" = p_noi_dung
        WHERE ten = 'chung_footer';
    ELSE
        INSERT INTO config.thong_tin_he_thong (ten, "noiDung")
        VALUES ('chung_footer', p_noi_dung);
    END IF;

    RETURN p_noi_dung;
END;
$$;
