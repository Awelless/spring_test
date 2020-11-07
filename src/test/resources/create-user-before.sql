delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$t048ULuy6VzQ1SfZwM56i.hzQBqCRyT4TBtXs8ReYz9EcFqvl3J32', 'admin'),
(2, true, '$2a$08$t7WcvVkJDPSv/31ce5p4b.94cIUzrDWxsA0Nwu16LRgna8wOTTxk2', 'catlover');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');