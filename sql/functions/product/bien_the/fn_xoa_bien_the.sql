drop function if exists product.fn_xoa_bien_the;

create function product.fn_xoa_bien_the(
    pId bigint,
    pSpId bigint
)
    returns boolean
as
$$
declare
    v_url_image varchar;
begin
    select "hinhAnh" into v_url_image from product.bien_the where id = pId;

    if not (v_url_image is null or v_url_image = '') then
        delete from files where url = v_url_image;
    end if;

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