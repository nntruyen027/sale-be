drop function if exists product.fn_them_bien_the;

create function product.fn_them_bien_the(
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
    v_data   jsonb;
    v_new_id bigint;
begin
    insert into product.bien_the("sanPhamId", sku, "hinhAnh", "mauSac", "kichCo", gia, "tonKho")
    values (pSanPhamId, pSku, pHinhAnh, pMauSac, pKichCo, pGia, pTonKho)
    returning id into v_new_id;

    select to_jsonb(bt)
    into v_data
    from product.v_bien_the bt
    where id = v_new_id
    limit 1;

    return v_data;

end;
$$ language plpgsql;