drop function if exists blog.fn_xoa_chuyen_muc;

create function blog.fn_xoa_chuyen_muc(
    p_id bigint
)
    returns boolean
as
$$
declare
    v_cm varchar;
begin
    select ten into v_cm from blog.chuyen_muc where id = p_id;

    if v_cm is null or v_cm = '' then
        raise 'Không tồn tại chuyên mục có id %.', p_id;
    end if;

    if exists(select * from blog.bai_viet where "chuyenMucId" = p_id) then
        raise 'Tồn tại bài viết thuộc chuyên mục %.', v_cm;
    end if;

    delete
    from blog.chuyen_muc
    where id = p_id;

    return FOUND;
end;

$$ language plpgsql;

