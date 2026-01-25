DROP FUNCTION IF EXISTS blog.fn_lay_ds_bai_viet;

CREATE FUNCTION blog.fn_lay_ds_bai_viet(
    p_chuyen_muc_id BIGINT DEFAULT NULL,
    p_trang_thai VARCHAR DEFAULT NULL,
    p_search VARCHAR DEFAULT NULL,
    p_page INT DEFAULT 1,
    p_limit INT DEFAULT 10
)
    RETURNS JSONB
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_offset INT := (p_page - 1) * p_limit;
    v_data   JSONB;
    v_total  BIGINT;
BEGIN
    /*
     * 1. Đếm tổng số bản ghi
     */
    SELECT COUNT(*)
    INTO v_total
    FROM blog.bai_viet b
    WHERE
      -- filter trạng thái (nếu có)
        (p_trang_thai IS NULL OR b."trangThai" = p_trang_thai)

      -- filter chuyên mục (nếu có)
      AND (p_chuyen_muc_id IS NULL OR b."chuyenMucId" = p_chuyen_muc_id)

      -- search tiêu đề (nếu có)
      AND (
        p_search IS NULL
            OR p_search = ''
            OR lower(unaccent(b."tieuDe")) LIKE '%' || lower(unaccent(p_search)) || '%'
        );


    /*
     * 2. Lấy dữ liệu phân trang
     */
    SELECT jsonb_agg(to_jsonb(r))
    INTO v_data
    FROM (SELECT *
          FROM blog.v_bai_viet v
          WHERE (p_trang_thai IS NULL OR v."trangThai" = p_trang_thai)
            AND (p_chuyen_muc_id IS NULL OR v."chuyenMucId" = p_chuyen_muc_id)
            AND (
              p_search IS NULL
                  OR p_search = ''
                  OR lower(unaccent(v."tieuDe")) LIKE '%' || lower(unaccent(p_search)) || '%'
              )
          ORDER BY v."ngayTao" DESC
          OFFSET v_offset LIMIT p_limit) r;


    /*
     * 3. Trả về response phân trang
     */
    RETURN util.fn_page_response(
            p_data := COALESCE(v_data, '[]'::jsonb),
            p_page := p_page,
            p_total := v_total,
            p_limit := p_limit
           );
END;
$$;

sel