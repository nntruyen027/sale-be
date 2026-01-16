DROP FUNCTION IF EXISTS dm_chung.fn_tao_xa;

CREATE OR REPLACE FUNCTION dm_chung.fn_tao_xa(
    p_ten VARCHAR(120),
    p_ghi_chu TEXT,
    p_tinh_id BIGINT
)
    RETURNS jsonb
AS
$$
DECLARE
    new_id BIGINT;
    v_data jsonb;
BEGIN
    INSERT INTO dm_chung.xa (ten,
                             ghiChu,
                             tinhid)
    VALUES (p_ten,
            p_ghi_chu,
            p_tinh_id)
    RETURNING id INTO new_id;

    SELECT to_jsonb(x)
    into v_data
    FROM dm_chung.v_xa x
    WHERE x.id = new_id
    LIMIT 1;

    return v_data;
END;
$$ LANGUAGE plpgsql;
