drop function if exists product.fn_sua_san_pham;

create function product.fn_sua_san_pham(
    pId bigint,
    pLoaiSp bigint,
    pTen varchar,
    pHinhAnh varchar,
    pMoTa varchar,
    pSlug varchar
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    if exists(select 1 from product.san_pham where slug = pSlug and id != pId) then
        raise '% đã tồn tại', pSlug;
    end if;

    update product.san_pham
    set ten       = pTen,
        "hinhAnh" = pHinhAnh,
        "moTa"    = pMoTa,
        "loaiId"  = pLoaiSp,
        "slug"    = pSlug
    where id = pId;

    select to_jsonb(sp)
    into v_data
    from product.v_san_pham sp
    where id = pId
    limit 1;

    return v_data;

end;
$$ language plpgsql;