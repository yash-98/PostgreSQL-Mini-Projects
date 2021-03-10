INSERT INTO Person(sin, name, address, phone) VALUES
    ('999999999', 'Yash', '10 Malibu Point, Cali, Ontario, CA - A0B1C2', 9909761133),
    ('000010000', 'Rex', '12 Strain cres, Ajax, Ontario, CA - L1C3K4', 9825954970),
    ('000020000', 'Tex', '12 Strain cres, Ajax, Ontario, CA - L1C3K4', 9825698434),
    ('000030000', 'Mex', '12 Strain cres, Ajax, Ontario, CA - L1C3K4', 9124042012),
    ('000040000', 'Lex', '12 Strain cres, Ajax, Ontario, CA - L1C3K4', 9222333444),
    ('000050000', 'Fax', '12 Strain cres, Ajax, Ontario, CA - L1C3K4', 9427718721),
    ('000060000', 'Tony Stark', '10880 Malibu Point, Cali, Ontario, CA - A1B4C7', 9239501838),
    ('000070000', 'Steven Rogers', '569 Leaman Place, New York, Ontario, CA - A2B8L1', 9198284191),
    ('000080000', 'Natasha Romanoff', '10880 Malibu Point, Cali, Ontario, CA - A1B4C7', 9018280182),
    ('000090000', 'Clinton Barton', '72 Leaman Place, New York, Ontario, CA - A2B8L1', 9125024518),
    ('000100000', 'Bruce Banner', '88 Dayton Ave Toronto, Ontario, CA - A8J4L2', 9405210581),
    ('000110000', 'Thor Odinson', '1 Asgard Ave, Saturn, Ontario, CA - A0P2F9', 9193850583);

INSERT INTO place(name, address, gps, description) VALUES
    ('Frankies Tomato', '124 Leslie St., York, Ontario, CA - M3J4K7', point(47.32432, 38.23442), 'This is an amazing restaurant for classical italian cusine.'),
    ('Jane Medical Centre', '27 Jane St., Toronto, Ontario, CA - M1K0X2', point(64.45334, 59.68768), 'Janes one and only Family Clinic'),
    ('One Health Medical Centre', '84 Bayly Ave, Ajax, Ontario, CA - L1Z0A1', point(64.34432, 58.23442), 'One Health Clinic, you wont need to come again.'),
    ('Cedabre Medical Centre', '1200 Markham Rd., Markham, Ontario, CA - M1S3H5', point(42.23616, 63.12563), 'Cedabre Clinic, the only Clinic in Markham'),
    ('Oshawa Health Centre', '67 Taunton Rd., Oshawa, Ontario, CA - L1K0V0', point(12.41241, 32.12523), 'Oshawa Clinic, your first choice in Durham'),
    ('York University', '15 Keele St., York, Ontario, CA - M1S5E8', point(89.48981, 94.89732), 'If you can fork, you can go to york.'),
    ('The Cheesecake Factory', '1 Yorkdale Ave, Yorkdale, Ontario, CA - M5S3T4', point(35.23511, 94.13523), 'Its not a date without a cheese cake.'),
    ('Avengers Headquarters', '890 Fifth Avenue, New York, Ontario, CA - M4P7D5', point(31.23521, 56.34221), 'Avengers Assemble'),
    ('Vaughan Mills', '100 Vaughan Mills, Vaughan, Ontario, CA - L1K1C1', point(35.13523, 34.23515), 'One-stop-shop for all brands and ammenities');


INSERT INTO Time_Slot(time) VALUES
    ('2020-10-02 05:00:00'),
    ('2020-10-02 08:30:00'),
    ('2020-10-03 03:00:00'),
    ('2020-10-04 06:30:00'),
    ('2020-10-05 03:00:00'),
    ('2020-10-05 04:45:00'),
    ('2020-10-05 22:00:00'),
    ('2020-10-09 23:00:00'),
    ('2020-10-13 19:15:00'),
    ('2020-10-13 23:15:00'),
    ('2020-10-13 23:45:00'),
    ('2020-10-15 06:00:00'),
    ('2020-10-15 15:45:00'),
    ('2020-10-15 17:00:00'),
    ('2020-10-15 17:15:00'),
    ('2020-10-15 19:00:00'),
    ('2020-10-15 20:00:00'),
    ('2020-10-16 01:15:00'),
    ('2020-10-19 08:00:00'),
    ('2020-10-21 13:00:00'),
    ('2020-10-21 15:30:00'),
    ('2020-10-26 08:00:00'),
    ('2020-10-27 17:45:00'),
    ('2020-10-29 14:30:00'),
    ('2020-10-29 22:00:00'),
    ('2020-10-31 09:00:00'),
    ('2020-10-31 19:00:00'),
    ('2020-10-31 19:15:00'),
    ('2020-11-01 22:15:00'),
    ('2020-11-02 08:00:00'),
    ('2020-11-03 23:00:00');

INSERT INTO Method(method) VALUES
    ('registry sign in'),
    ('registry sign out'),
    ('surveillance camera'),
    ('contact-tracing phone app');

INSERT INTO Action(action) VALUES
    ('Self-Isolate'),
    ('Admit-To-ICU'),
    ('Hospitalize'),
    ('Qurantine'),
    ('Take-Medicine');

INSERT INTO Test_Type(testtype) VALUES
    ('Cold Flu'),
    ('Covid-19-Full'),
    ('Influenza'),
    ('Covid-19-Fast');

INSERT INTO Test_Centre(name) VALUES
    ('Jane Medical Centre'),
    ('One Health Medical Centre'),
    ('Cedabre Medical Centre'),
    ('Oshawa Health Centre');

INSERT INTO Recon(sin, method, time, placename) VALUES
    ('999999999', 'contact-tracing phone app', '2020-10-02 05:00:00', 'The Cheesecake Factory'),
    ('000080000', 'contact-tracing phone app', '2020-10-02 08:30:00', 'The Cheesecake Factory'),
    ('000030000', 'surveillance camera',  '2020-10-03 03:00:00', 'Vaughan Mills'),
    ('000080000', 'surveillance camera', '2020-10-04 06:30:00', 'Avengers Headquarters'),
    ('000090000', 'surveillance camera', '2020-10-05 03:00:00', 'Avengers Headquarters'),
    ('999999999', 'surveillance camera', '2020-10-15 6:00:00', 'York University'),
    ('000050000', 'contact-tracing phone app', '2020-10-15 6:00:00', 'York University'),
    ('000060000', 'surveillance camera', '2020-10-15 15:45:00', 'York University'),
    ('000070000', 'contact-tracing phone app', '2020-10-15 17:00:00', 'York University'),
    ('000080000', 'contact-tracing phone app', '2020-10-15 17:15:00', 'York University'),
    ('000090000', 'surveillance camera', '2020-10-15 19:00:00', 'York University'),
    ('000030000', 'registry sign in', '2020-10-05 03:00:00', 'Cedabre Medical Centre'),
    ('000030000', 'registry sign out', '2020-10-05 04:45:00', 'Cedabre Medical Centre'),
    ('000020000', 'registry sign in', '2020-10-13 23:15:00', 'Oshawa Health Centre'),
    ('000020000', 'registry sign out', '2020-10-13 23:45:00', 'Oshawa Health Centre'),
    ('000010000', 'registry sign in', '2020-10-21 13:00:00', 'Cedabre Medical Centre'),
    ('000010000', 'registry sign out', '2020-10-21 15:30:00', 'Cedabre Medical Centre'),
    ('000040000', 'registry sign in', '2020-10-31 19:00:00', 'Jane Medical Centre'),
    ('000040000', 'registry sign out', '2020-10-31 19:15:00', 'Jane Medical Centre'),
    ('000050000', 'registry sign in', '2020-10-29 14:30:00', 'One Health Medical Centre'),
    ('000050000', 'registry sign out', '2020-10-29 22:00:00','One Health Medical Centre');

INSERT INTO Test(sin, time, testtype, action, testcentre) VALUES
    ('000030000', '2020-10-05 03:00:00', 'Covid-19-Full', 'Hospitalize', 'Cedabre Medical Centre'),
    ('000020000', '2020-10-13 23:15:00', 'Cold Flu', 'Take-Medicine', 'Oshawa Health Centre'),
    ('000010000', '2020-10-21 13:00:00', 'Covid-19-Full', 'Admit-To-ICU', 'Cedabre Medical Centre'),
    ('000040000', '2020-10-31 19:00:00', 'Covid-19-Fast', 'Self-Isolate', 'Jane Medical Centre'),
    ('000050000', '2020-10-29 14:30:00', 'Influenza', 'Qurantine', 'One Health Medical Centre');

INSERT INTO Bubble(p1, p2) VALUES
    ('000010000', '000020000'),
    ('000010000', '000030000'),
    ('000010000', '000040000'),
    ('000010000', '000050000'),
    ('000020000', '000010000'),
    ('000020000', '000030000'),
    ('000020000', '000040000'),
    ('000020000', '000050000'),
    ('000030000', '000020000'),
    ('000030000', '000010000'),
    ('000030000', '000040000'),
    ('000030000', '000050000'),
    ('000040000', '000010000'),
    ('000040000', '000030000'),
    ('000040000', '000020000'),
    ('000040000', '000050000'),
    ('999999999', '000060000'),
    ('999999999', '000080000'),
    ('000060000', '000080000'),
    ('000070000', '000090000');

INSERT INTO Offer(testtype, testcentre) VALUES
    ('Cold Flu', 'Oshawa Health Centre'),
    ('Covid-19-Full', 'Oshawa Health Centre'),
    ('Influenza', 'Oshawa Health Centre'),
    ('Covid-19-Fast', 'Oshawa Health Centre'),
    ('Influenza', 'One Health Medical Centre'),
    ('Cold Flu', 'One Health Medical Centre'),
    ('Covid-19-Full', 'Cedabre Medical Centre'),
    ('Covid-19-Fast', 'Jane Medical Centre'),
    ('Influenza', 'Jane Medical Centre');