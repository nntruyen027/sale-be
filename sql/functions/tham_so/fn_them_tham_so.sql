drop function if exists fn_them_tham_so;

create function fn_them_tham_so(
    p_khoa varchar,
    p_kieu_du_lieu varchar,
    p_gia_tri varchar
)
    returns jsonb
as
$$
declare
    v_new_id bigint;
    v_data   jsonb;
begin
    if exists(select 1 from tham_so where khoa = p_khoa) then
        raise 'Tham số với khóa % đã tồn tại', p_khoa;
    end if;

    insert into tham_so(khoa, "kieuDuLieu", "giaTri")
    values (p_khoa, p_kieu_du_lieu, p_gia_tri)
    returning id into v_new_id;

    select to_jsonb(ts) into v_data from tham_so ts where id = v_new_id;

    return v_data;
end;
$$ language plpgsql;