drop function if exists auth.fn_them_dia_chi_ca_nhan;

create function auth.fn_them_dia_chi_ca_nhan(
    p_xa_id bigint,
    p_chi_tiet varchar,
    p_user_id bigint,
    p_dinh_vi varchar default null,
    p_is_default boolean default false
)
    returns jsonb
as
$$
declare
    v_data   jsonb;
    v_new_id bigint;
begin
    insert into auth.dia_chi("xaId", "chiTiet", "userId", "dinhVi", "isDefault")
    values (p_xa_id, p_chi_tiet, p_user_id, p_dinh_vi, p_is_default)
    returning id into v_new_id;


    select to_jsonb(a) into v_data from auth.v_dia_chi a where a.id = v_new_id;

    return v_data;

end;
$$ language plpgsql;