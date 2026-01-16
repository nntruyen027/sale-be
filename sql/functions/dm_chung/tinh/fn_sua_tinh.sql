DROP FUNCTION IF EXISTS dm_chung.fn_sua_tinh;

CREATE OR REPLACE FUNCTION dm_chung.fn_sua_tinh(
    p_id BIGINT,
    p_ten VARCHAR(120),
    p_ghi_chu TEXT
)
    RETURNS jsonb
AS
$$
declare
    v_data jsonb;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM dm_chung.v_tinh t WHERE t.id = p_id) THEN
        RAISE EXCEPTION USING MESSAGE = format('Tỉnh với id %s không tồn tại', p_id);
    END IF;

    UPDATE dm_chung.tinh
    SET ten    = p_ten,
        ghiChu = p_ghi_chu
    WHERE id = p_id;


    SELECT to_jsonb(t)
    into v_data
    FROM dm_chung.v_tinh t
    WHERE t.id = p_id;

    return v_data;
END;
$$ LANGUAGE plpgsql;
