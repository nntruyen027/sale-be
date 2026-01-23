drop function if exists blog.fn_them_catalog;

create function blog.fn_them_catalog(
    p_tieu_de varchar,
    p_url varchar
)
    returns jsonb
as
$$
declare
    v_new_id bigint;
    v_data   jsonb;
begin
    insert into blog.catalog("tieuDe", url)
    values (p_tieu_de, p_url)
    returning id into v_new_id;

    select to_jsonb(l) into v_data from blog.catalog l where id = v_new_id limit 1;

    return v_data;
end;

$$ language plpgsql;

