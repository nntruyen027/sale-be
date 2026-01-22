CREATE FUNCTION blog.fn_lay_ds_bai_viet_theo_hashtag(
    p_slug VARCHAR,
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
    SELECT count(*)
    into v_total
    FROM blog.v_bai_viet v
             JOIN blog.bai_viet_hashtag bvh ON v.id = bvh."baiVietId"
             JOIN blog.hashtag h ON h.id = bvh."hashtagId"
    WHERE h.slug = p_slug
      AND v."trangThai" = 'PUBLIC';


    /*
     * 2. Lấy dữ liệu phân trang
     */
    SELECT jsonb_agg(to_jsonb(v))
    into v_data
    FROM blog.v_bai_viet v
             JOIN blog.bai_viet_hashtag bvh ON v.id = bvh."baiVietId"
             JOIN blog.hashtag h ON h.id = bvh."hashtagId"
    WHERE h.slug = p_slug
      AND v."trangThai" = 'PUBLIC'
    ORDER BY v."ngayTao" DESC
    LIMIT p_limit offset v_offset;


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