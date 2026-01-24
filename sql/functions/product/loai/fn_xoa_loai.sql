drop function if exists product.fn_xoa_loai;

create function product.fn_xoa_loai(
    p_id bigint
)
    returns boolean
as
$$
declare
    v_url_image varchar;
    v_loai      varchar := '';
begin
    select ten into v_loai from product.loai where id = p_id;

    if v_loai is null or v_loai = '' then
        raise 'Không tồn tại loại sản phẩm có id %.', p_id;
    end if;

    if exists(select 1 from product.san_pham where "loaiId" = p_id) then
        raise 'Tồn tại sản phẩm thuộc loại %.', v_loai;
    end if;

    select "hinhAnh" into v_url_image from product.loai where id = p_id;

    if not (v_url_image is null or v_url_image = '') then
        delete from files where url = v_url_image;
    end if;

    delete
    from product.loai
    where id = p_id;

    return FOUND;
end;

$$ language plpgsql;

