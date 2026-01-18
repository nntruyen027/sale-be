drop function if exists fn_lay_ds_tham_so;

create function fn_lay_ds_tham_so(
    p_search varchar(500),
    p_page int default 1,
    p_limit int default 10
)
    returns jsonb
as
$$
declare
    v_total       bigint;
    v_total_pages int;
    v_offset      int := (p_page - 1) * p_limit;
    v_data        jsonb;
begin
    select count(*) into v_total from tham_so;
    v_total_pages := util.fn_tinh_total_page(v_total, p_limit);

    select coalesce(jsonb_agg(
                            to_jsonb(r)
                    ), '[]'::jsonb)
    into v_data
    from (select *
          from tham_so ts
          where p_search is null
             or p_search = ''
             or lower(unaccent(ts.khoa)) like '%' || p_search || '%'
          offset v_offset limit p_limit) r;

    RETURN jsonb_build_object(
            'data', v_data,
            'page', p_page,
            'size', p_limit,
            'totalPages', v_total_pages,
            'totalElements', v_total
           );
end;

$$
    language plpgsql
