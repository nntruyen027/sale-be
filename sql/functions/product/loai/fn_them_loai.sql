drop function if exists product.fn_them_loai;

create function product.fn_them_loai(
    p_ten varchar,
    p_parent_id bigint,
    p_hinh_anh varchar,
    p_slug varchar
)
    returns jsonb
as
$$
declare
    v_new_id bigint;
    v_data   jsonb;
begin
    if exists(select 1 from product.loai where slug = p_slug) then
        raise '% đã tồn tại', p_slug;
    end if;

    insert into product.loai(ten, "parentId", "hinhAnh")
    values (p_ten, p_parent_id, p_hinh_anh)
    returning id into v_new_id;

    select to_jsonb(l) into v_data from product.v_loai l where id = v_new_id limit 1;

    return v_data;

end;

$$ language plpgsql;

