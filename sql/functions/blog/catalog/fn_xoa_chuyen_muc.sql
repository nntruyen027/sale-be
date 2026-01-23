drop function if exists blog.fn_xoa_catalog;

create function blog.fn_xoa_catalog(
    p_id bigint
)
    returns boolean
as
$$

begin

    delete
    from blog.catalog
    where id = p_id;

    return FOUND;
end;

$$ language plpgsql;

