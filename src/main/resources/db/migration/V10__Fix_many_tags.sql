drop table message_tag;

create table message_tag (
    message_id int8 not null,
    tags varchar(255)
);

alter table if exists message_tag
    add constraint message_tag_message_fk
    foreign key (message_id) references message;