drop function if exists fn_xoa_tham_so;

create function fn_xoa_tham_so(
    p_id bigint
)
    returns boolean
as
$$
begin
    delete from tham_so where id = p_id;

    return FOUND;
end;

$$ language plpgsql;