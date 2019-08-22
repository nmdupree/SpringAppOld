--------------------------------------------------------------
-- Filename:  V1.5__moreTables.sql
--------------------------------------------------------------

CREATE TABLE ind_types(
    id          INTEGER,
    name        VARCHAR(255)
);

CREATE TABLE link_reports_indicators(
    id          INTEGER,
    report      INTEGER ,
    indicator   INTEGER
);

ALTER TABLE indicators ADD COLUMN ind_type INTEGER null;