DROP FUNCTION IF EXISTS dm_chung.fn_tao_tinh;

CREATE OR REPLACE FUNCTION dm_chung.fn_tao_tinh(
    p_ten VARCHAR(120),
    p_ghi_chu TEXT
)
    RETURNS jsonb
AS
$$
DECLARE
    new_id BIGINT;
    v_data jsonb;
BEGIN
    INSERT INTO dm_chung.tinh (ten,
                               ghichu)
    VALUES (p_ten,
            p_ghi_chu)
    RETURNING id INTO new_id;


    SELECT to_jsonb(t)
    into v_data
    FROM dm_chung.v_tinh t
    WHERE t.id = new_id
    LIMIT 1;

    return v_data;
END;
$$ LANGUAGE plpgsql;
