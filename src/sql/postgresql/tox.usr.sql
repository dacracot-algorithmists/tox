set echo on
spool tox.usr.sql.err

/*------------------------------------------------------------------------*/

CREATE USER tox WITH ENCRYPTED PASSWORD 't0xb8by';

CREATE SCHEMA AUTHORIZATION tox;

/*------------------------------------------------------------------------*/

spool off
