delete from message;

insert into message(id, text, tag, user_id) values
(1, 'first', 'msg', 1),
(2, 'second', 'test', 1),
(3, 'third', 'msg', 1),
(4, 'fourth', 'test', 1);

alter sequence hibernate_sequence restart with 10;