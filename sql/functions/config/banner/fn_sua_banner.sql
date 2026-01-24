drop function if exists config.fn_sua_banner;

create function config.fn_sua_banner(
    p_id bigint,
    p_hinh_anh varchar,
    p_url varchar,
    p_thu_tu int,
    p_mac_dinh boolean
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    if exists(select 1 from config.banner where "thuTu" = p_thu_tu and id != p_id) then
        raise 'Trùng thứ tự % với banner khác', p_thu_tu;
    end if;

    if p_mac_dinh = true then
        update config.banner
        set "laMacDinh" = false
        where "laMacDinh" = true;
    end if;

    update config.banner
    set "hinhAnh"   = p_hinh_anh,
        "url"       = p_url,
        "thuTu"     = p_thu_tu,
        "laMacDinh" = p_mac_dinh
    where id = p_id;

    select to_jsonb(l) into v_data from config.banner l where id = p_id limit 1;

    return v_data;

end;

$$ language plpgsql;

