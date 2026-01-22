drop function if exists blog.fn_xoa_chuyen_muc;

create function blog.fn_xoa_chuyen_muc(
    p_id bigint
)
    returns boolean
as
$$

begin

    delete
    from blog.chuyen_muc
    where id = p_id;

    return FOUND;
end;

$$ language plpgsql;

