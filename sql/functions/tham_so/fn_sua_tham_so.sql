drop function if exists fn_sua_tham_so;

create function fn_sua_tham_so(
    p_id varchar,
    p_khoa varchar,
    p_kieu_du_lieu varchar,
    p_gia_tri varchar,
    p_enable boolean
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    update tham_so
    set khoa         = p_khoa,
        "giaTri"     = p_gia_tri,
        "kieuDuLieu" = p_kieu_du_lieu,
        "isEnable"   = p_enable
    where id = p_id;

    select to_jsonb(ts) into v_data from tham_so ts where id = p_id;

    return v_data;
end;
$$ language plpgsql;