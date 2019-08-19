--------------------------------------------------------------
-- Filename:  V1.2__tables.sql
--------------------------------------------------------------

-- Create the reports_aud table
        CREATE TABLE reports_aud
        (
          rev                 INTEGER, -- txn id
          rev_type            INTEGER, -- O create, 1 update, 2 delete
          id                  INTEGER PRIMARY KEY NOT NULL,
          version             INTEGER,
          description         TEXT,
          display_name        VARCHAR(255),
          reviewed            BOOLEAN,
          reference_source    INTEGER,
          priority            INTEGER,
          created_date        TIMESTAMP,
          last_modified_date  TIMESTAMP,
          is_custom_report    BOOLEAN DEFAULT FALSE,
          reserved            BOOLEAN,
          reserved_by         VARCHAR(255)
        );