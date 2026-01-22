DROP FUNCTION IF EXISTS blog.fn_gan_hashtag_cho_bai_viet;

CREATE FUNCTION blog.fn_gan_hashtag_cho_bai_viet(
    p_bai_viet_id BIGINT,
    p_hashtags TEXT[]
)
    RETURNS BOOLEAN
    LANGUAGE plpgsql
AS
$$
DECLARE
    v_tag        TEXT;
    v_hashtag_id BIGINT;
BEGIN
    -- Xóa hashtag cũ
    DELETE
    FROM blog.bai_viet_hashtag
    WHERE "baiVietId" = p_bai_viet_id;

    -- Thêm hashtag mới
    FOREACH v_tag IN ARRAY p_hashtags
        LOOP
            -- Tạo hashtag nếu chưa có
            INSERT INTO blog.hashtag (ten, slug)
            VALUES (v_tag, lower(unaccent(v_tag)))
            ON CONFLICT (slug) DO NOTHING;

            -- Lấy id hashtag
            SELECT id
            INTO v_hashtag_id
            FROM blog.hashtag
            WHERE slug = lower(unaccent(v_tag));

            -- Gán cho bài viết
            INSERT INTO blog.bai_viet_hashtag("baiVietId", "hashtagId")
            VALUES (p_bai_viet_id, v_hashtag_id)
            ON CONFLICT DO NOTHING;
        END LOOP;

    return true;
END;
$$;
