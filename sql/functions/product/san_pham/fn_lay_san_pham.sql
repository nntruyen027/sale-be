drop function if exists product.fn_lay_san_pham;

create function product.fn_lay_san_pham(
    p_id bigint
)
    returns jsonb
as
$$
declare
    v_data jsonb;
begin
    if not exists(select * from product.san_pham where id = p_id) then
        raise 'Sản phẩm với id % không tồn tại.', p_id;
    end if;

    select to_jsonb(r)
    into v_data
    from (select *
          from product.v_san_pham s
          where id = p_id
          limit 1) r;

    return v_data;
end;
$$ language plpgsql;

