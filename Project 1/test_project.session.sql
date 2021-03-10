insert into Person(sin, name, address, phone) values
('023422789', 'Neha', '27 Keele St', '4162585962'),
('123452549', 'Damon', '50 Keele St', '4161520052'),
('112856789', 'Stefan', '60 Dundas St', '416278022'),
('120202789', 'Elena', '77 King St', '4162026962'),
('120002559', 'Catherine', '52 Keele St', '4168585888'), 
('023152789', 'Suzy', '52 Keele St', '4168585888'),
('123466649', 'Mike', '60 Queens Park', '6478525920'),
('000056789', 'Nick','99 Queens Park', '6478500020'),
('129999989', 'Nikki', '1 Queens Avenue', '6470005920'),
('165182609', 'Jasmine', '45 Spadina Avenue', '6478111120'),
('122222129', 'Rubina','60 King Street', '4160525920'),
('118848419', 'Abhinav', '10 Cedarglen Rs', '4165859425'),
('625202789', 'Rahul', '100 Finch Avenue', '2895658255'),
('166666659', 'Vishwas', '25 Younge Street', '6478252222');

insert into Time_Slot(time) values
('2020-10-15 6:00:00'),
('2020-11-12 9:00:00'),
('2020-12-12 5:00:00'),
('2020-10-20 4:00:00'),
('2020-11-11 1:00:00'),
('2020-10-30 3:00:00'),
('2020-10-15 7:00:00'),
('2020-11-12 10:00:00'),
('2020-12-12 2:00:00'),
('2020-10-20 2:00:00'),
('2020-10-30 4:00:00'),
('2020-11-11 6:00:00'),
('2020-10-15 6:30:00'), 
('2020-11-12 9:15:00'), 
('2020-12-12 4:30:00'), 
('2020-10-20 3:30:00'), 
('2020-11-11 2:15:00'),
('2020-10-30 3:45:00'),
('2020-10-16 2:00:00'),
('2020-11-24 6:00:00'),
('2020-11-18 9:00:00'),
('2020-10-22 2:00:00'),
('2020-10-19 12:00:00'),
('2020-10-17 7:00:00'),
('2020-12-13 10:00:00'),
('2020-12-14 11:00:00'), 
('2020-12-12 8:00:00');


insert into Method(method) values
('registry sign in'),
('registry sign out'),
('surveillance camera'),
('contact-tracing phone app');


insert into Place(name, gps, description, address) values
('York University', point(47.32432, 38.23442), 'University', '47 Keele St'),
('Woodlands School', point(23.12312, 21.12312), 'High School', '3225 Erindale Rd'),
('Eaton Centre', point(23.15341, 45.13455), 'Shopping Centre', '26 Queen St'),
('Square One', point(45.13452, 12.54314), 'Shopping Centre', '100 City Centre'),
('Osmows', point(72.45725, 82.54335), 'Restaurant', '47 Keele St'),
('CreditView Hospital', point(57.45373, 54.24562), 'Hospital', '11 CreditView Rd'),
('Trillium Hospital', point(18.36725, 74.23664), 'Hospital', '120 Queens St'),
('Queensway Hospital', point(35.64563, 34.25636), 'Hospital', '106 Mavis Rd');

insert into Recon(method,sin,placename,time) values
('registry sign in','023422789','Trillium Hospital' , '2020-10-15 6:00:00' ),
('registry sign in','123452549','CreditView Hospital', '2020-11-12 9:00:00'),
('registry sign in','112856789','Queensway Hospital', '2020-12-12 2:00:00'),
('registry sign in','120202789','Trillium Hospital', '2020-10-20 2:00:00'),
('registry sign in','120002559', 'CreditView Hospital', '2020-11-11 1:00:00'),
('registry sign in','166666659', 'Queensway Hospital', '2020-10-30 3:00:00'),
('registry sign out','023422789','Trillium Hospital' , '2020-10-15 7:00:00' ),
('registry sign out','123452549','CreditView Hospital', '2020-11-12 10:00:00'),
('registry sign out','112856789','Queensway Hospital', '2020-12-12 5:00:00'),
('registry sign out','120202789','Trillium Hospital', '2020-10-20 4:00:00'),
('registry sign out','120002559','CreditView Hospital', '2020-11-11 6:00:00'),
('registry sign out','166666659','Queensway Hospital', '2020-10-30 4:00:00'),
('surveillance camera','023152789','York University' , '2020-11-18 9:00:00' ),
('surveillance camera','123466649','Woodlands School', '2020-12-12 8:00:00'),
('contact-tracing phone app','000056789','Eaton Centre', '2020-12-14 11:00:00'),
('contact-tracing phone app','129999989','Square One', '2020-12-13 10:00:00'),
('surveillance camera','122222129', 'Osmows', '2020-10-17 7:00:00'),
('surveillance camera','165182609', 'York University', '2020-11-24 6:00:00'),
('contact-tracing phone app','118848419','Eaton Centre', '2020-10-22 2:00:00'),
('contact-tracing phone app','625202789','Square One', '2020-10-19 12:00:00');


insert into Test_Centre(name) values 
('CreditView Hospital'),
('Trillium Hospital'),
('Queensway Hospital');

insert into Action(action) values
('Self Isolation'),
('Admit in hospital'),
('Quarantine');

insert into Test_Type(testtype) values
('Covid19'),
('Flu'),
('Swine Flu');

insert into Offer(testtype, testcentre) values
('Flu','Trillium Hospital'),
('Covid19','CreditView Hospital'),
('Covid19','Queensway Hospital'),
('Flu', 'CreditView Hospital'),
('Swine Flu','Queensway Hospital');

insert into Test(sin, time, testtype, testcentre, action) values
('023422789', '2020-10-15 6:30:00', 'Flu','Trillium Hospital','Self Isolation'),
('123452549', '2020-11-12 9:15:00', 'Covid19','CreditView Hospital','Admit in hospital'),
('112856789', '2020-12-12 4:30:00', 'Covid19','Queensway Hospital','Admit in hospital'),
('120202789', '2020-10-20 3:30:00', 'Flu', 'Trillium Hospital','Quarantine'),
('120002559', '2020-11-11 2:15:00', 'Flu', 'CreditView Hospital', 'Quarantine'),
('166666659', '2020-10-30 3:45:00', 'Swine Flu','Queensway Hospital','Self Isolation');