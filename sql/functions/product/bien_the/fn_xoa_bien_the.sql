drop function if exists product.fn_xoa_bien_the;

create function product.fn_xoa_bien_the(
    pId bigint,
    pSpId bigint
)
    returns boolean
as
$$
begin
    if not exists(select 1
                  from product.bien_the
                  where id = pId
                    and pSpId = "sanPhamId") then
        raise 'Không tồn tại biến thể % của sản phẩm %', pId, pSpId;
    end if;

    delete
    from product.bien_the
    where id = pId
      and pSpId = "sanPhamId";

    return FOUND;

end;
$$ language plpgsql;