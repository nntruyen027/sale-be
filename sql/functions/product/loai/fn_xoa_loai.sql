drop function if exists product.fn_xoa_loai;

create function product.fn_xoa_loai(
    p_id bigint
)
    returns boolean
as
$$
begin
    delete
    from product.loai
    where id = p_id;

    return FOUND;
end;

$$ language plpgsql;

