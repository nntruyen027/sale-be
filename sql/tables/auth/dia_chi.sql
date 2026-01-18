-- Xóa bảng nếu đã tồn tại
DROP TABLE IF EXISTS auth.dia_chi CASCADE;

-- Tạo bảng địa chỉ
CREATE TABLE auth.dia_chi
(
    id          BIGSERIAL PRIMARY KEY,
    "xaId"      BIGINT REFERENCES dm_chung.xa (id) ON DELETE CASCADE,
    "chiTiet"   VARCHAR(500),
    "userId"    BIGINT REFERENCES auth.users (id) ON DELETE CASCADE,
    "dinhVi"    VARCHAR(100),
    "isDefault" BOOLEAN DEFAULT FALSE
);

-- ======================================================
-- 1 USER CHỈ CÓ 1 ĐỊA CHỈ MẶC ĐỊNH
-- (Partial Unique Index)
-- ======================================================
CREATE UNIQUE INDEX ux_dia_chi_user_default
    ON auth.dia_chi ("userId")
    WHERE "isDefault" = true;

-- ======================================================
-- FUNCTION: tự động bỏ default cũ khi set default mới
-- ======================================================
CREATE OR REPLACE FUNCTION auth.fn_set_single_default_address()
    RETURNS trigger AS
$$
BEGIN
    IF NEW."isDefault" = true THEN
        UPDATE auth.dia_chi
        SET "isDefault" = false
        WHERE "userId" = NEW."userId"
          AND id <> COALESCE(NEW.id, -1);
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- ======================================================
-- TRIGGER
-- ======================================================
CREATE TRIGGER trg_single_default_address
    BEFORE INSERT OR UPDATE OF "isDefault"
    ON auth.dia_chi
    FOR EACH ROW
EXECUTE FUNCTION auth.fn_set_single_default_address();
