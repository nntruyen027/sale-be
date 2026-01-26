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
    if exists(select 1 from config.thong_tin_he_thong where "thuTu" = p_thu_tu and id != p_id and ten = 'banner') then
        raise 'Trùng thứ tự % với banner khác', p_thu_tu;
    end if;

    if p_mac_dinh = true then
        update config.thong_tin_he_thong
        set "laMacDinh" = false
        where "laMacDinh" = true
          and ten = 'banner';
    end if;

    update config.thong_tin_he_thong
    set "hinhAnh"   = p_hinh_anh,
        "url"       = p_url,
        "thuTu"     = p_thu_tu,
        "laMacDinh" = p_mac_dinh
    where id = p_id
      and ten = 'banner';

    select to_jsonb(l) into v_data from config.thong_tin_he_thong l where id = p_id and ten = 'banner' limit 1;

    return v_data;

end;

$$ language plpgsql;

