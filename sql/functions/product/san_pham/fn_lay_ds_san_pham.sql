drop function if exists product.fn_lay_ds_san_pham;

create function product.fn_lay_ds_san_pham(
    p_loai_id bigint,
    p_search varchar,
    p_page int default 1,
    p_limit int default 10
)
    returns jsonb
as
$$
declare
    v_offset int := (p_page - 1) * p_limit;
    v_data   jsonb;
    v_total  bigint;
begin
    select count(*)
    into v_total
    from product.san_pham s
    where (
        p_search is null
            or p_search = ''
            or lower(unaccent(s.ten)) like '%' || lower(unaccent(p_search)) || '%'
        )
      and (
        p_loai_id is null
            or p_loai_id = 0
            or p_loai_id = s."loaiId"
        );

    select jsonb_agg(to_jsonb(r))
    into v_data
    from (select *
          from product.v_san_pham s
          where (
              p_search is null
                  or p_search = ''
                  or lower(unaccent(s.ten)) like '%' || lower(unaccent(p_search)) || '%'
              )
            and (
              p_loai_id is null
                  or p_loai_id = 0
                  or p_loai_id = s."loaiId"
              )
          limit p_limit offset v_offset) r;

    return util.fn_page_response(
            p_data := v_data, p_page := p_page, p_limit := p_limit, p_total := v_total
           );
end;
$$ language plpgsql;

