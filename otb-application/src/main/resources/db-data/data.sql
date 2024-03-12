INSERT INTO users (username, email, password, user_role)
VALUES ('admin', 'admin@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'ADMIN'),
       ('user', 'user@mail.co', '$2a$10$bIjGIiFFRtxMLutKDvuPTeTtpROhRceXImnIqzOUkkwN/aBMJE/hu', 'USER');

INSERT INTO train_tickets (name, from_city, to_city, day_of_departure, arrival_day, time_of_departure, arrival_time, price, seat_number, type_of_train_class, car_number, owner_id)
VALUES
    ('771FDW', 'KYIV', 'KHARKIV', '2024-03-01', '2024-03-02', '08:00:00', '12:00:00', 50.00, 67, 'SECOND_CLASS', 1, null),
    ('772BIT', 'KYIV', 'KHARKIV', '2024-03-02', '2024-03-03', '10:00:00', '14:00:00', 60.00, 86, 'LUXURY', 2, null),
    ('773EKC', 'KYIV', 'KHARKIV', '2024-03-03', '2024-03-04', '12:00:00', '16:00:00', 70.00, 10, 'FIRST_CLASS', 3, null),
    ('774IAR', 'KHARKIV', 'KYIV', '2024-03-04', '2024-03-05', '08:00:00', '12:00:00', 50.00, 35, 'SECOND_CLASS', 1, null),
    ('775PIB', 'KHARKIV', 'KYIV', '2024-03-05', '2024-03-05', '10:00:00', '14:00:00', 60.00, 54, 'LUXURY', 2, null),
    ('776COH', 'KHARKIV', 'KYIV', '2024-03-06', '2024-03-07', '12:00:00', '16:00:00', 70.00, 76, 'SECOND_CLASS', 3, null),
    ('777APL', 'DNIPRO', 'WARSAW', '2024-03-07', '2024-03-07', '08:00:00', '12:00:00', 100.00, 32, 'FIRST_CLASS', 1, null),
    ('778MKR', 'DNIPRO', 'WARSAW', '2024-03-08', '2024-03-09', '10:00:00', '14:00:00', 120.00, 78, 'FIRST_CLASS', 2, null),
    ('779ERY', 'DNIPRO', 'WARSAW', '2024-03-09', '2024-03-09', '12:00:00', '16:00:00', 150.00, 45, 'FIRST_CLASS', 3, null),
    ('800XVN', 'DNIPRO', 'KYIV', '2024-03-10', '2024-03-11', '08:00:00', '12:00:00', 80.00, 89, 'SECOND_CLASS', 1, null),
    ('801FHR', 'DNIPRO', 'KYIV', '2024-03-11', '2024-03-12', '10:00:00', '14:00:00', 90.00, 23, 'LUXURY', 2, null),
    ('802AET', 'DNIPRO', 'KYIV', '2024-03-12', '2024-03-13', '12:00:00', '16:00:00', 100.00, 11, 'SECOND_CLASS', 3, null),
    ('803TJT', 'KYIV', 'DNIPRO', '2024-03-13', '2024-03-13', '08:00:00', '12:00:00', 80.00, 1, 'SECOND_CLASS', 1, null),
    ('804XXB', 'KYIV', 'DNIPRO', '2024-03-14', '2024-03-14', '10:00:00', '14:00:00', 90.00, 5, 'SECOND_CLASS', 2, null),
    ('805HFH', 'KYIV', 'DNIPRO', '2024-03-15', '2024-03-15', '12:00:00', '16:00:00', 100.00, 9, 'SECOND_CLASS', 3, null),
    ('806JTT', 'KHARKIV', 'DNIPRO', '2024-03-16', '2024-03-17', '08:00:00', '12:00:00', 70.00, 17, 'LUXURY', 1, null),
    ('807TJT', 'KHARKIV', 'DNIPRO', '2024-03-16', '2024-03-17', '10:00:00', '14:00:00', 80.00, 99, 'FIRST_CLASS', 2, null),
    ('808NNY', 'KHARKIV', 'DNIPRO', '2024-03-14', '2024-03-15', '12:00:00', '16:00:00', 90.00, 45, 'LUXURY', 3, null),
    ('809KUR', 'DNIPRO', 'KHARKIV', '2024-03-13', '2024-03-13', '08:00:00', '12:00:00', 70.00, 32, 'SECOND_CLASS', 1, null),
    ('810KYT', 'DNIPRO', 'KHARKIV', '2024-03-11', '2024-03-11', '10:00:00', '14:00:00', 80.00, 31, 'SECOND_CLASS', 2, null),
    ('811WRT', 'DNIPRO', 'KHARKIV', '2024-03-11', '2024-03-11', '12:00:00', '16:00:00', 90.00, 30, 'LUXURY', 3, null),
    ('812JFK', 'KHARKIV', 'WARSAW', '2024-03-20', '2024-03-21', '08:00:00', '18:00:00', 120.00, 88, 'SECOND_CLASS', 1, null),
    ('813HSD', 'KHARKIV', 'WARSAW', '2024-03-23', '2024-03-24', '10:00:00', '20:00:00', 130.00, 64, 'FIRST_CLASS', 2, null),
    ('814MHS', 'KHARKIV', 'WARSAW', '2024-03-22', '2024-03-23', '12:00:00', '22:00:00', 140.00, 42, 'SECOND_CLASS', 3, null),
    ('815LHU', 'WARSAW', 'KHARKIV', '2024-03-28', '2024-03-28', '08:00:00', '18:00:00', 120.00, 76, 'LUXURY', 1, null),
    ('816GJF', 'WARSAW', 'KHARKIV', '2024-03-28', '2024-03-29', '10:00:00', '20:00:00', 130.00, 30, 'SECOND_CLASS', 2, null),
    ('817JTR', 'WARSAW', 'KHARKIV', '2024-03-07', '2024-03-07', '12:00:00', '22:00:00', 140.00, 20, 'FIRST_CLASS', 3, null);

INSERT INTO bus_tickets (name, from_city, to_city, day_of_departure, arrival_day, time_of_departure, arrival_time, price, seat_number, owner_id)
VALUES
    ('KD1', 'KYIV', 'DNIPRO', '2024-03-15', '2024-03-15', '08:00:00', '12:00:00', 100.00, 4, null),
    ('KD1', 'KYIV', 'DNIPRO', '2024-03-15', '2024-03-15', '10:00:00', '14:00:00', 100.00, 6, null),
    ('DK2', 'DNIPRO', 'KYIV', '2024-03-16', '2024-03-16', '08:00:00', '12:00:00', 100.00, 8, null),
    ('DK2', 'DNIPRO', 'KYIV', '2024-03-16', '2024-03-16', '10:00:00', '14:00:00', 100.00, 10, null),
    ('KW3', 'KYIV', 'WARSAW', '2024-03-17', '2024-03-17', '08:00:00', '18:00:00', 150.00, 12, null),
    ('KW3', 'KYIV', 'WARSAW', '2024-03-17', '2024-03-17', '10:00:00', '20:00:00', 150.00, 15, null),
    ('WK4', 'WARSAW', 'KYIV', '2024-03-18', '2024-03-18', '08:00:00', '18:00:00', 150.00, 12, null),
    ('WK4', 'WARSAW', 'KYIV', '2024-03-18', '2024-03-18', '10:00:00', '20:00:00', 150.00, 11, null),
    ('KK5', 'KYIV', 'KHARKIV', '2024-03-19', '2024-03-19', '08:00:00', '14:00:00', 120.00, 16, null),
    ('KK5', 'KYIV', 'KHARKIV', '2024-03-19', '2024-03-19', '10:00:00', '16:00:00', 120.00, 12, null),
    ('KKY6', 'KHARKIV', 'KYIV', '2024-03-20', '2024-03-20', '08:00:00', '14:00:00', 120.00, 6, null),
    ('KKY6', 'KHARKIV', 'KYIV', '2024-03-20', '2024-03-20', '10:00:00', '16:00:00', 120.00, 8, null),
    ('WD6', 'WARSAW', 'DNIPRO', '2024-03-21', '2024-03-21', '08:00:00', '16:00:00', 200.00, 9, null),
    ('WD7', 'WARSAW', 'DNIPRO', '2024-03-21', '2024-03-21', '10:00:00', '18:00:00', 200.00, 4, null),
    ('DW8', 'DNIPRO', 'WARSAW', '2024-03-22', '2024-03-22', '08:00:00', '16:00:00', 200.00, 3, null),
    ('DWW2', 'DNIPRO', 'WARSAW', '2024-03-22', '2024-03-22', '10:00:00', '18:00:00', 200.00, 2, null);
