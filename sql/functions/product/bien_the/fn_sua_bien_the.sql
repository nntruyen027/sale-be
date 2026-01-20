drop function if exists product.fn_sua_bien_the;

create function product.fn_sua_bien_the(
    pId bigint,
    pSanPhamId bigint,
    pSku varchar,
    pHinhAnh varchar,
    pMauSac varchar,
    pKichCo varchar,
    pGia numeric(12, 2),
    pTonKho int
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    if not exists(select 1
                  from product.bien_the
                  where id = pId
                    and pSanPhamId = "sanPhamId") then
        raise 'Không tồn tại biến thể % của sản phẩm %', pId, pSanPhamId;
    end if;

    update product.bien_the
    set gia       = pGia,
        sku       = pSku,
        "tonKho"  = pTonKho,
        "kichCo"  = pKichCo,
        "mauSac"  = pMauSac,
        "hinhAnh" = pHinhAnh
    where id = pId
      and pSanPhamId = "sanPhamId";

    select to_jsonb(bt)
    into v_data
    from product.v_bien_the bt
    where id = pId
      and pSanPhamId = "sanPhamId"
    limit 1;

    return v_data;

end;
$$ language plpgsql;