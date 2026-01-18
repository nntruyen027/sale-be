drop function if exists auth.fn_sua_dia_chi_ca_nhan;

create function auth.fn_sua_dia_chi_ca_nhan(
    p_id bigint,
    p_user_id bigint
)
    returns boolean
as
$$
begin
    delete from auth.dia_chi where id = p_id and "userId" = p_user_id;

    return FOUND;

end;
$$ language plpgsql;