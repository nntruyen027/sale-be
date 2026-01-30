drop function if exists product.fn_sua_loai;

create function product.fn_sua_loai(
    p_id bigint,
    p_ten varchar,
    p_parent_id bigint,
    p_hinh_anh varchar,
    p_slug varchar
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    if exists(select 1 from product.loai where slug = p_slug and id != p_id) then
        raise '% đã tồn tại', p_slug;
    end if;

    update product.loai
    set ten        = p_ten,
        "parentId" = p_parent_id,
        "hinhAnh"  = p_hinh_anh
    where id = p_id;

    select to_jsonb(l) into v_data from product.v_loai l where id = p_id limit 1;

    return v_data;

end;

$$ language plpgsql;

