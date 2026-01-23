drop function if exists blog.fn_sua_chuyen_muc;

create function blog.fn_sua_chuyen_muc(
    p_id bigint,
    p_ten varchar,
    p_parent_id bigint,
    p_slug varchar
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    update blog.chuyen_muc
    set ten        = p_ten,
        "parentId" = p_parent_id,
        slug       = p_slug
    where id = p_id;

    select to_jsonb(l) into v_data from blog.v_chuyen_muc l where id = p_id limit 1;

    return v_data;
EXCEPTION
    WHEN unique_violation THEN
        RAISE EXCEPTION 'Slug "% " đã tồn tại', p_slug
            USING ERRCODE = '23505';
end;

$$ language plpgsql;

