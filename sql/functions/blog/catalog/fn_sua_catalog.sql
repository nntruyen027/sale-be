drop function if exists blog.fn_sua_catalog;

create function blog.fn_sua_catalog(
    p_id bigint,
    p_tieu_de varchar,
    p_url varchar
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    update blog.catalog
    set "tieuDe" = p_tieu_de,
        url      = p_url
    where id = p_id;

    select to_jsonb(l) into v_data from blog.catalog l where id = p_id limit 1;

    return v_data;
end;

$$ language plpgsql;

