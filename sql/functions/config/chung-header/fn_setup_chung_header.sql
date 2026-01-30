DROP FUNCTION IF EXISTS config.fn_setup_chung_header;

CREATE FUNCTION config.fn_setup_chung_header(
    p_noi_dung varchar
)
    RETURNS varchar
    LANGUAGE plpgsql
AS
$$
BEGIN


    IF EXISTS (SELECT 1
               FROM config.thong_tin_he_thong
               WHERE ten = 'chung_header') THEN
        UPDATE config.thong_tin_he_thong
        SET "noiDung" = p_noi_dung
        WHERE ten = 'chung_header';
    ELSE
        INSERT INTO config.thong_tin_he_thong (ten, "noiDung")
        VALUES ('chung_header', p_noi_dung);
    END IF;

    RETURN p_noi_dung;
END;
$$;
