-- 1. Create and populate this table:  users

       DROP table if exists users;
       CREATE TABLE users
       (
           id                INTEGER        PRIMARY KEY NOT NULL,
           user_id           VARCHAR (256)  NOT NULL,
           full_name         VARCHAR(150)       NULL,
           email_address     VARCHAR(256)       NULL,
           phone_number      VARCHAR(30)        NULL,
           last_login_date   TIMESTAMP          NULL
       );

       insert into users(id, user_id, full_name) values(1, 'jsmith', 'John L Smith');
       insert into users(id, user_id, full_name) values(2, 'indyjones', 'Indina Jones');
       insert into users(id, user_id, full_name) values(3, 'hsolo', 'Han Solo');


-- 2. Create and populate this table:  sales

       DROP table if exists sales;
       CREATE TABLE sales
       (
           id                INTEGER       PRIMARY KEY NOT NULL,
           salesperson_id    INTEGER       NOT NULL,
           value             NUMERIC(12,2) NOT NULL,
           sell_date         TIMESTAMP     NOT NULL
       );

       insert into sales(id, salesperson_id, value, sell_date) values(100, 1, 5000, '2019-03-01');
       insert into sales(id, salesperson_id, value, sell_date) values(102, 1, 30000, '2019-03-02');
       insert into sales(id, salesperson_id, value, sell_date) values(104, 1, 10000, '2019-03-03');
       insert into sales(id, salesperson_id, value, sell_date) values(106, 2, 500, '2019-04-15');
       insert into sales(id, salesperson_id, value, sell_date) values(108, 2, 300, '2019-05-01');
       insert into sales(id, salesperson_id, value, sell_date) values(110, 56, 1000, '2019-06-01');

-- 3. Create the audit tables
       DROP table if exists users_aud;
       CREATE TABLE users_aud
       (
           id                INTEGER        NULL,
           user_id           VARCHAR (256)  NOT NULL,
           full_name         VARCHAR(150)       NULL,
           email_address     VARCHAR(256)       NULL,
           phone_number      VARCHAR(30)        NULL,
           rev               INTEGER        NOT NULL,   -- Transaction ID
           rev_type          INTEGER        NOT NULL,   -- 0 for create, 1 for update, 2 for delete
           timestamp         TIMESTAMP      NOT NULL,   -- Date/Time of the insert/update/delete operation
           username          VARCHAR(100)   NOT NULL    -- Person who performed insert/update/delete operation
       );

       DROP table if exists sales_aud;
       CREATE TABLE sales_aud
       (
           id                INTEGER        NULL,
           salesperson_id    INTEGER        NULL,
           value             NUMERIC(12,2)  NULL,
           sell_date         TIMESTAMP      NOT NULL,
           rev               INTEGER        NOT NULL,   -- Transaction ID
           rev_type          INTEGER        NOT NULL,   -- 0 for create, 1 for update, 2 for delete
           timestamp         TIMESTAMP      NOT NULL,   -- Date/Time of the insert/update/delete operation
           username          VARCHAR(100)   NOT NULL    -- Person who performed insert/update/delete operation
       );

