DROP FUNCTION IF EXISTS blog.fn_lay_bai_viet_lien_quan;

CREATE FUNCTION blog.fn_lay_bai_viet_lien_quan(
    p_id BIGINT
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
declare
    v_so_bai_viet varchar;
BEGIN
    select coalesce(ts."giaTri"::int, 5) into v_so_bai_viet from tham_so ts where ts.khoa = 'SO_BAI_LIEN_QUAN';

    RETURN (SELECT COALESCE(jsonb_agg(to_jsonb(v)), '[]'::jsonb)
            FROM blog.v_bai_viet v
            WHERE v.id <> p_id
              AND v."trangThai" = 'PUBLIC'
              AND (
                v."chuyenMucId" = (SELECT "chuyenMucId"
                                   FROM blog.bai_viet
                                   WHERE id = p_id)
                )
            ORDER BY v."ngayTao" DESC
            LIMIT v_so_bai_viet);
END;
$$;
