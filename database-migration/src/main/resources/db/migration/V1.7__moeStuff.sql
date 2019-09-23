--------------------------------------------------------------
-- Filename:  V1.7__moreStuff.sql
--------------------------------------------------------------

ALTER TABLE reports
    DROP COLUMN last_modified_date;

ALTER TABLE reports
    DROP COLUMN created_date;

ALTER TABLE reports_aud
    ADD COLUMN username VARCHAR(100);

ALTER TABLE reports_aud
    DROP COLUMN last_modified_date;

ALTER TABLE reports_aud
    DROP COLUMN created_date;

ALTER TABLE indicators
    DROP COLUMN created_by;

ALTER TABLE indicators
    DROP COLUMN created_date;

CREATE TABLE indicators_aud
        (
        id                  INTEGER PRIMARY KEY NOT NULL,
        value               VARCHAR(100),
        ind_type            INTEGER,
        rev                 INTEGER, -- txn id
        rev_type            INTEGER, -- O create, 1 update, 2 delete
        timestamp           TIMESTAMP,
        username            VARCHAR(100)
        );