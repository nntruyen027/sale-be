drop function if exists blog.fn_lay_ds_catalog;

create function blog.fn_lay_ds_catalog(
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
    from blog.catalog
    where p_search is null
       or p_search = ''
       or lower(unaccent(catalog."tieuDe")) like '%' || p_search || '%';


    select jsonb_agg(to_jsonb(r))
    into v_data
    from (select *
          from blog.catalog l
          where p_search is null
             or p_search = ''
             or lower(unaccent(l."tieuDe")) like '%' || p_search || '%'
          offset v_offset limit p_limit) r;


    return util.fn_page_response
           (p_data := coalesce(v_data, '[]'::jsonb), p_page := p_page, p_total := v_total, p_limit := p_limit);
end;
$$ language plpgsql;