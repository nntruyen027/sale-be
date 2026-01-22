drop function if exists blog.fn_them_chuyen_muc;

create function blog.fn_them_chuyen_muc(
    p_ten varchar,
    p_parent_id bigint,
    p_slug varchar
)
    returns jsonb
as
$$
declare
    v_new_id bigint;
    v_data   jsonb;
begin
    insert into blog.chuyen_muc(ten, "parentId", slug)
    values (p_ten, p_parent_id, p_slug)
    returning id into v_new_id;

    select to_jsonb(l) into v_data from blog.v_chuyen_muc l where id = v_new_id limit 1;

    return v_data;

end;

$$ language plpgsql;

