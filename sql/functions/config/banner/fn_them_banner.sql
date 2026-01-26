drop function if exists config.fn_them_banner;

create function config.fn_them_banner(
    p_hinh_anh varchar,
    p_url varchar,
    p_thu_tu int,
    p_mac_dinh boolean default false
)
    returns jsonb
    language plpgsql
as
$$
declare
    v_new_id     bigint;
    v_data       jsonb;
    v_max_thu_tu int;
begin
    -- 🔹 nếu không truyền thứ tự → lấy max + 1
    if p_thu_tu is null then
        select coalesce(max("thuTu"), 0) + 1
        into v_max_thu_tu
        from config.thong_tin_he_thong;

        p_thu_tu := v_max_thu_tu;
    else
        -- 🔹 có truyền thứ tự → check trùng
        if exists (select 1
                   from config.thong_tin_he_thong
                   where "thuTu" = p_thu_tu) then
            raise exception 'Trùng thứ tự % với banner khác', p_thu_tu;
        end if;
    end if;

    -- 🔹 nếu set mặc định → bỏ mặc định cũ
    if p_mac_dinh = true then
        update config.thong_tin_he_thong
        set "laMacDinh" = false
        where "laMacDinh" = true;
    end if;

    -- 🔹 insert banner mới
    insert into config.thong_tin_he_thong(url, "thuTu", "hinhAnh", "laMacDinh", ten)
    values (p_url, p_thu_tu, p_hinh_anh, p_mac_dinh, 'banner')
    returning id into v_new_id;

    -- 🔹 trả về bản ghi vừa tạo
    select to_jsonb(b)
    into v_data
    from config.thong_tin_he_thong b
    where b.id = v_new_id
      and ten = 'banner';

    return v_data;
end;
$$;
