use poseidon_p7;

insert into `bidlist` (account, type, bid_quantity) values
                                                     ('bid_account_1', 'type_1', 150.60),
                                                     ('bid_account_2', 'type_2', 180.98),
                                                     ('bid_account_3', 'type_3', 147.0);
insert into `bidlist` (account, type, bid_quantity) values
                                                        ('bid_account_4', 'type_4', 150.60),
                                                        ('bid_account_5', 'type_5', 180.98),
                                                        ('bid_account_6', 'type_6', 147.0);

insert into curvepoint (curveid, term, value) values
                                                (2, 169.55, 230),
                                                (6, 185.62, 147),
                                                (9, 169.55, 312);

insert into `rating` (moodys_rating, sand_p_rating, fitch_rating, order_number) values
                                                                  ('moodys_rating_1', 'sandPRating1', 'fitch_rating_1', 325),
                                                                  ('moodys_rating_2','sandPRating2',  'fitch_rating_2', 341),
                                                                  ('moodys_rating_3', 'sandPRating3', 'fitch_rating_3', 850);

insert into `rulename`(name, description, json, template, sql_str, sql_part ) values
                                              ('rule number 1', 'rule must be rule', 'json_1', 'template_1', 'sql_1', 'sql_part1'),
                                              ('rule number 2', 'rule 1 must be rule', 'json_2', 'template_2', 'sql_2', 'sql_part2'),
                                              ('rule number 3', 'rule 2 must be rule 1', 'json_3', 'template_3', 'sql_1', 'sql_part3');

insert into `trade` (account, type, buy_quantity) values
                                                      ('account_1', 'type_1', 652),
                                                      ('account_2', 'type_2', 3522),
                                                      ('account_3', 'type_3', 5542);

insert into `users` (fullname, username, password, role) values
                                                          ('user_fullName_1', 'username1', '12345', 'USER'),
                                                          ('user_fullName_2', 'username2', '12345', 'ADMIN'),
                                                          ('user_fullName_3', 'username3', '12345', 'USER'),
                                                          ('user_fullName_4', 'username4', '12345', 'ADMIN');
--Subhy7!
insert into `users` (fullname, username, password, role) values('Administrator', 'admin', '$2a$12$97hRulpibn96YXW04fWf8e3ywDI9G4bkPXgB3KQUYVLi2g/eGayZS', 'ADMIN');
insert into `users` (fullname, username, password, role) values('User', 'user', '$2a$12$97hRulpibn96YXW04fWf8e3ywDI9G4bkPXgB3KQUYVLi2g/eGayZS','USER');
insert into `users` (fullname, username, password, role) values('User', 'appUser', '$2a$12$97hRulpibn96YXW04fWf8e3ywDI9G4bkPXgB3KQUYVLi2g/eGayZS','USER');
insert into `users` (fullname, username, password, role) values('NewUserAdmin', 'NewAdmin', '$2a$12$97hRulpibn96YXW04fWf8e3ywDI9G4bkPXgB3KQUYVLi2g/eGayZS','ADMIN, USER');
insert into `users` (fullname, username, password, role) values('UserAdmin', 'poseidon', '$2a$12$97hRulpibn96YXW04fWf8e3ywDI9G4bkPXgB3KQUYVLi2g/eGayZS','ADMIN');
insert into `users` (fullname, username, password, role) values('UserName', 'Username', '$2a$12$97hRulpibn96YXW04fWf8e3ywDI9G4bkPXgB3KQUYVLi2g/eGayZS','USER');