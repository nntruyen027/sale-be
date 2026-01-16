DROP FUNCTION IF EXISTS dm_chung.fn_sua_xa;

CREATE OR REPLACE FUNCTION dm_chung.fn_sua_xa(
    p_id BIGINT,
    p_ten VARCHAR(120),
    p_ghi_chu TEXT,
    p_tinh_id BIGINT
)
    RETURNS jsonb
AS
$$
declare
    v_data jsonb;
BEGIN
    IF NOT EXISTS (SELECT 1 FROM dm_chung.xa where id = p_id) THEN
        RAISE EXCEPTION 'Xã với id % không tồn tại', p_id;
    END IF;

    IF NOT EXISTS (SELECT 1 FROM dm_chung.tinh where id = p_tinh_id) THEN
        RAISE EXCEPTION 'Tỉnh với id % không tồn tại', p_tinh_id;
    END IF;

    UPDATE dm_chung.xa
    SET ten    = p_ten,
        ghiChu = p_ghi_chu,
        tinhid = p_tinh_id
    WHERE id = p_id;

    SELECT to_jsonb(x)
    into v_data
    FROM dm_chung.v_xa x
    WHERE x.id = p_id
    LIMIT 1;

    return v_data;

END;
$$ LANGUAGE plpgsql;
