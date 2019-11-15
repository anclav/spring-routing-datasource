CREATE TABLE warehouse
(
    id      BIGSERIAL PRIMARY KEY,
    name    TEXT,
    address TEXT
);

CREATE TABLE warehouse_schedule
(
    id           BIGSERIAL PRIMARY KEY,
    day          INT,
    time_from    TIME,
    time_to      TIME,
    warehouse_id BIGINT REFERENCES warehouse (id)
);

INSERT INTO warehouse (name, address)
VALUES ('warehouse12', 'address12'),
       ('warehouse22', 'address22');


INSERT INTO warehouse_schedule (day, time_from, time_to, warehouse_id)
VALUES (1, '10:00', '11:00', 1),
       (2, '10:00', '12:00', 1),
       (3, '10:00', '13:00', 1),
       (4, '10:00', '14:00', 1),
       (5, '17:00', '18:00', 1),
       (1, '11:00', '12:00', 2),
       (2, '11:00', '13:00', 2),
       (3, '11:00', '14:00', 2),
       (4, '11:00', '15:00', 2),
       (5, '18:00', '19:00', 2);