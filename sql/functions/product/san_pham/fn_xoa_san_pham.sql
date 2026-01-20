drop function if exists product.fn_xoa_san_pham;

create function product.fn_xoa_san_pham(
    pId bigint
)
    returns boolean
as
$$
begin
    delete
    from product.san_pham
    where id = pId;

    return FOUND;

end;
$$ language plpgsql;