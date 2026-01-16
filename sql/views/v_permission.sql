DROP VIEW IF EXISTS auth.v_permission;

CREATE OR REPLACE VIEW auth.v_permission AS
SELECT id,
       code
FROM auth.permissions