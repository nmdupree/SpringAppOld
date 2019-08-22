--------------------------------------------------------------
-- Filename:  R__data.sql
--------------------------------------------------------------

DELETE FROM reports;
DELETE FROM indicators;
DELETE FROM ind_types;
DELETE FROM link_reports_indicators;

INSERT INTO reports(id, display_name, created_date, reviewed)
    VALUES  (1, 'BadGuys.txt', '2018-11-01', true);

INSERT INTO reports(id, display_name, created_date, reviewed)
    VALUES  (2, 'Domains.txt', '2019-01-02', null);

INSERT INTO reports(id, display_name, created_date, reviewed)
    VALUES  (3, 'AdamsStuff.txt', '2019-01-02', true);

INSERT INTO reports(id, display_name, created_date, reviewed)
    VALUES  (4, 'KevinsStuff.txt', '2019-02-04', true);

INSERT INTO reports(id, display_name, created_date, reviewed)
    VALUES  (5, 'KatsIPs.pdf', '2019-03-13', null);

INSERT INTO indicators(id, value, ind_type)
    VALUES  (11, 'badguy.com', 1);

INSERT INTO indicators(id, value,  ind_type)
    VALUES  (12, 'google.com', 1);

INSERT INTO indicators(id, value,  ind_type)
    VALUES  (13, 'yahoo.com', 1);

INSERT INTO indicators(id, value,  ind_type)
    VALUES  (14, '127.0.0.1', 2);

INSERT INTO indicators(id, value,  ind_type)
    VALUES  (15, '128.0.0.1', 2);

INSERT INTO indicators(id, value,  ind_type)
    VALUES  (16, 'bing.com', 1);

INSERT INTO ind_types(id, name)
    VALUES  (1, 'domain');

INSERT INTO ind_types(id, name)
    VALUES  (2, 'ip');

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (21, 1, 11);

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (22, 2, 12);

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (23, 2, 13);

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (24, 4, 14);

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (25, 4, 15);

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (26, 4, 16);

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (27, 5, 14);

INSERT INTO link_reports_indicators(id, report, indicator)
    VALUES   (28, 5, 15);
