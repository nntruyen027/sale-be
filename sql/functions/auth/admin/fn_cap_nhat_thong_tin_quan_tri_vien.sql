DROP FUNCTION IF EXISTS auth.fn_cap_nhat_thong_tin_quan_tri_vien;

CREATE OR REPLACE FUNCTION auth.fn_cap_nhat_thong_tin_quan_tri_vien(
    p_id BIGINT,
    p_avatar TEXT,
    p_ho_ten TEXT
)
    RETURNS jsonb
AS
$$
    declare
        v_re jsonb;
BEGIN
    IF NOT EXISTS (SELECT 1
                   FROM auth.v_users_full a
                   WHERE a.id = p_id) THEN
        RAISE EXCEPTION 'Quản trị viên với id % không tồn tại', p_id;
    END IF;

    UPDATE auth.users
    SET avatar = p_avatar,
        ho_ten = p_ho_ten
    WHERE id = p_id;

        SELECT to_jsonb(a) into v_re
        FROM auth.v_users_full a
        WHERE a.id = p_id
        LIMIT 1;

    return v_re;
END;
$$ LANGUAGE plpgsql;
