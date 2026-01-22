DROP TABLE IF EXISTS blog.bai_viet;

CREATE TABLE blog.bai_viet
(
    id            BIGSERIAL PRIMARY KEY,
    "tieuDe"      VARCHAR(500)        NOT NULL,
    slug          VARCHAR(500) UNIQUE NOT NULL,
    "tomTat"      TEXT,
    "noiDung"     TEXT                NOT NULL,
    "chuyenMucId" BIGINT REFERENCES blog.chuyen_muc (id),
    "tacGiaId"    VARCHAR(500),
    "trangThai"   VARCHAR(20) DEFAULT 'DRAFT', -- DRAFT / PUBLIC / HIDDEN
    "luotXem"     BIGINT      DEFAULT 0,
    "ngayTao"     TIMESTAMP   DEFAULT now(),
    "ngayCapNhat" TIMESTAMP   DEFAULT now(),
    "nguoiDang"   BIGINT REFERENCES auth.users (id)
);


alter table blog.bai_viet
    add column "tacGia" varchar(500)