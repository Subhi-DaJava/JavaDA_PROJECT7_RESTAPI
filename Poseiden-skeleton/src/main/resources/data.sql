insert into `bidlist` (account, type, bid_quantity) values
                                                     ('bid_account_1', 'type_1', 150.60),
                                                     ('bid_account_2', 'type_2', 180.98),
                                                     ('bid_account_3', 'type_3', 147.0);

insert into `curvepoint` (curveid, term, value) values
                                                (2, 169.55, 230),
                                                (6, 185.62, 147),
                                                (9, 169.55, 312);

insert into `rating` (moddys_rating, fitch_rating, order_number) values
                                                                  ('moddys_rating_1', 'fitch_rating_1', 325),
                                                                  ('moddys_rating_2', 'fitch_rating_2', 341),
                                                                  ('moddys_rating_3', 'fitch_rating_3', 850);

insert into `rulename`(name, description) values
                                              ('rule number 1', 'rule must be rule'),
                                              ('rule number 2', 'rule 1 must be rule'),
                                              ('rule number 3', 'rule 2 must be rule 1');

insert into `trade` (account, type, status, book) values
                                                      ('account_1', 'type_1', 'status1', 'yes'),
                                                      ('account_2', 'type_2', 'status1', 'yes'),
                                                      ('account_3', 'type_3', 'status1', 'No');

insert into Users(fullname, username, password, role) values
                                                          ('user_fullName_1', 'username1', '12345', 'USER'),
                                                          ('user_fullName_2', 'username2', '12345', 'ADMIN'),
                                                          ('user_fullName_3', 'username3', '12345', 'USER'),
                                                          ('user_fullName_4', 'username4', '12345', 'ADMIN');