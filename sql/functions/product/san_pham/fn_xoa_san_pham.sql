drop function if exists product.fn_xoa_san_pham;

create function product.fn_xoa_san_pham(
    pId bigint
)
    returns boolean
as
$$
declare
    v_url_image varchar;
begin
    select "hinhAnh" into v_url_image from product.san_pham where id = pId;

    if not (v_url_image is null or v_url_image = '') then
        delete from files where url = v_url_image;
    end if;


    delete
    from product.san_pham
    where id = pId;

    return FOUND;

end;
$$ language plpgsql;