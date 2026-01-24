drop function if exists config.fn_them_banner;

create function config.fn_them_banner(
    p_hinh_anh varchar,
    p_url varchar,
    p_thu_tu int,
    p_mac_dinh boolean
)
    returns jsonb
as
$$
declare
    v_new_id bigint;
    v_data   jsonb;
begin
    if exists(select 1 from config.banner where "thuTu" = p_thu_tu and id != p_id) then
        raise 'Trùng thứ tự % với banner khác', p_thu_tu;
    end if;

    if p_mac_dinh = true then
        update config.banner
        set "laMacDinh" = false
        where "laMacDinh" = true;
    end if;

    insert into config.banner(url, "thuTu", "hinhAnh", "laMacDinh")
    values (p_url, p_thu_tu, p_hinh_anh, p_mac_dinh)
    returning id into v_new_id;

    select to_jsonb(l) into v_data from config.banner l where id = v_new_id limit 1;

    return v_data;

end;

$$ language plpgsql;

