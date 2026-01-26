drop function if exists config.fn_xoa_banner;

create function config.fn_xoa_banner(
    p_id bigint
)
    returns boolean
as
$$
declare
    v_url_image varchar;
begin
    select "hinhAnh" into v_url_image from config.thong_tin_he_thong where id = p_id and ten = 'banner';

    if not (v_url_image is null or v_url_image = '') then
        delete from files where url = v_url_image;
    end if;

    delete
    from config.thong_tin_he_thong
    where id = p_id
      and ten = 'banner';

    return FOUND;
end;

$$ language plpgsql;

