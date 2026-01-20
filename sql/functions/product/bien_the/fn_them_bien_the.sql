drop function if exists product.fn_them_san_pham;

create function product.fn_them_san_pham(
    pLoaiSp bigint,
    pTen varchar,
    pHinhAnh varchar,
    pMoTa varchar
)
    returns jsonb
as
$$
declare
    v_new_id bigint;
    v_data   jsonb;
begin
    insert into product.san_pham("loaiId", ten, "hinhAnh", "moTa")
    values (pLoaiSp, pTen, pHinhAnh, pMoTa)
    returning id into v_new_id;

    select to_jsonb(sp)
    into v_data
    from product.v_san_pham sp
    where id = v_new_id
    limit 1;

    return v_data;

end;
$$ language plpgsql;