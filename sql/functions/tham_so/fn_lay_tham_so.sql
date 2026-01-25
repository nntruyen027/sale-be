drop function if exists fn_lay_tham_so;

create function fn_lay_tham_so(
    p_khoa varchar
)
    returns varchar
as
$$
declare
    v_data varchar;
begin
    if not exists(select 1 from tham_so where khoa = p_khoa and "isEnable" = true) then
        return null;
    end if;

    select "giaTri" into v_data from tham_so where khoa = p_khoa and "isEnable" = true;

    return v_data;
end;


$$ language plpgsql;