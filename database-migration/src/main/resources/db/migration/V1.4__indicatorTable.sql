--------------------------------------------------------------
-- Filename:  V1.4__indicatorTable.sql
--------------------------------------------------------------

-- Create the reports_aud table
        CREATE TABLE indicators
        (
          id                  INTEGER PRIMARY KEY NOT NULL,
          value               VARCHAR(255),
          created_date        TIMESTAMP,
          created_by          VARCHAR(255)
        );