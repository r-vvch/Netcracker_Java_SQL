DROP TABLE Staff;
DROP TABLE Military_units;
DROP TABLE Quarters;
DROP TABLE Ranks;

CREATE TABLE Military_units (
       Unit_id              NUMBER NOT NULL,
       Name                 VARCHAR2(20) NULL,
       Parent_id            NUMBER NULL,
       PRIMARY KEY (Unit_id), 
       FOREIGN KEY (Parent_id)
                             REFERENCES Military_units(Unit_id)
);


CREATE TABLE Quarters (
       Quarter_id           NUMBER NOT NULL,
       Location             VARCHAR2(20) NULL,
       PRIMARY KEY (Quarter_id)
);


CREATE TABLE Ranks (
       Rank_id              CHAR(18) NOT NULL,
       Name                 VARCHAR2(20) NULL,
       Priority             NUMBER NULL,
       PRIMARY KEY (Rank_id)
);


CREATE TABLE Staff (
       Person_id            NUMBER NOT NULL,
       Name                 VARCHAR2(20) NULL,
       Consc_date           DATE NULL,
       Rank_id              CHAR(18) NULL,
       Chief                NUMBER NULL,
       Unit_id              NUMBER NULL,
       Quarter_id           NUMBER NULL,
       PRIMARY KEY (Person_id), 
       FOREIGN KEY (Unit_id)
                             REFERENCES Military_units, 
       FOREIGN KEY (Quarter_id)
                             REFERENCES Quarters, 
       FOREIGN KEY (Rank_id)
                             REFERENCES Ranks, 
       FOREIGN KEY (Chief)
                             REFERENCES Staff(Person_id)
);

insert into Ranks
values (10, 'Marshal', 10);

insert into Ranks
values (20, 'General', 9);

insert into Ranks
values (30, 'Colonel', 8);

insert into Ranks
values (40, 'Major', 7);

insert into Ranks
values (50, 'Leutenant', 5);

insert into Ranks
values (60, 'Sergeant', 3);

insert into Ranks
values (70, 'Private', 1);




insert into quarters
values (100, 'Northern Wing');

insert into quarters
values (110, 'Eastern Wing');

insert into quarters
values (120, 'Southern Wing');

insert into quarters
values (130, 'Western Wing');

insert into quarters
values (140, 'Central Quarters');


insert into military_units
values(200, 'Regiment #1271A', null);

insert into military_units
values(210, 'First Company', 200);

insert into military_units
values(220, 'Second Company', 200);

insert into military_units
values(230, 'Platoon #1', 210);

insert into military_units
values(240, 'Platoon #2', 210);

insert into military_units
values(250, 'Platoon #3', 210);

insert into military_units
values(260, 'Platoon #4', 220);

insert into military_units
values(270, 'Platoon #5', 220);

insert into military_units
values(280, 'Squad #1', 250);

insert into military_units
values(290, 'Squad #2', 250);

insert into military_units
values(300, 'Squad #3', 240);

insert into military_units
values(310, 'Squad #4', 260);

insert into military_units
values(320, 'Squad #5', 260);



insert into staff
values (1000, 'Vasiliev', to_date('17-12-1980', 'DD-MM-YYYY'), 30, null, 200, 140);

insert into staff
values (1010, 'Zakharov', to_date('20-12-1980', 'DD-MM-YYYY'), 40, 1000, 210, 140);

insert into staff
values (1020, 'Alexeev', to_date('21-12-1980', 'DD-MM-YYYY'), 40, 1000, 220, 140);

insert into staff
values (1030, 'Ivanov', to_date('10-01-1981', 'DD-MM-YYYY'), 50, 1010, 240, 140);

insert into staff
values (1040, 'Petrov', to_date('12-01-1981', 'DD-MM-YYYY'), 50, 1010, 250, 140);

insert into staff
values (1050, 'Sidorov', to_date('16-01-1981', 'DD-MM-YYYY'), 50, 1020, 260, 140);

insert into staff
values (1060, 'Bolshov', to_date('12-06-1985', 'DD-MM-YYYY'), 60, 1030, 280, 100);

insert into staff
values (1070, 'Menshov', to_date('15-06-1985', 'DD-MM-YYYY'), 60, 1030, 290, 110);

insert into staff
values (1080, 'Antonov', to_date('10-07-1985', 'DD-MM-YYYY'), 60, 1040, 300, 120);

insert into staff
values (1090, 'Trofimov', to_date('10-06-1985', 'DD-MM-YYYY'), 60, 1050, 310, 130);

insert into staff
values (1100, 'Komolov', to_date('12-09-1986', 'DD-MM-YYYY'), 70, 1060, 280, 100);

insert into staff
values (1110, 'Shevchenko', to_date('11-09-1986', 'DD-MM-YYYY'), 70, 1060, 280, 130);

insert into staff
values (1120, 'Levich', to_date('14-09-1986', 'DD-MM-YYYY'), 70, 1060, 280, 130);

insert into staff
values (1130, 'Kuznetsov', to_date('13-09-1986', 'DD-MM-YYYY'), 70, 1060, 280, 130);

insert into staff
values (1140, 'Korolev', to_date('21-09-1986', 'DD-MM-YYYY'), 70, 1060, 280, 100);

insert into staff
values (1150, 'Leonov', to_date('17-09-1986', 'DD-MM-YYYY'), 70, 1070, 290, 110);

insert into staff
values (1160, 'Chernenko', to_date('19-09-1986', 'DD-MM-YYYY'), 70, 1070, 290, 110);

insert into staff
values (1180, 'Gorshkov', to_date('23-09-1986', 'DD-MM-YYYY'), 70, 1070, 290, 110);

insert into staff
values (1190, 'Abramov', to_date('29-09-1986', 'DD-MM-YYYY'), 70, 1070, 290, 110);

insert into staff
values (1200, 'Larin', to_date('25-09-1986', 'DD-MM-YYYY'), 70, 1070, 290, 110);

insert into staff
values (1210, 'Soloviev', to_date('26-09-1986', 'DD-MM-YYYY'), 70, 1080, 300, 120);

insert into staff
values (1220, 'Ivakhnenko', to_date('30-09-1986', 'DD-MM-YYYY'), 70, 1080, 300, 120);

insert into staff
values (1230, 'Koslov', to_date('20-09-1986', 'DD-MM-YYYY'), 70, 1080, 300, 120);

commit;
