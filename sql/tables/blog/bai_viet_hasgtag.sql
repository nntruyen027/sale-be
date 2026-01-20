DROP TABLE IF EXISTS blog.bai_viet_hashtag;

CREATE TABLE blog.bai_viet_hashtag
(
    "baiVietId" BIGINT REFERENCES blog.bai_viet (id) ON DELETE CASCADE,
    "hashtagId" BIGINT REFERENCES blog.hashtag (id) ON DELETE CASCADE,
    PRIMARY KEY ("baiVietId", "hashtagId")
);
