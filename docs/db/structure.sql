create table color
(
    id         int auto_increment
        primary key,
    color_name text null
);

create table cat
(
    id       int auto_increment
        primary key,
    name     text not null,
    color_id int  null,
    constraint cat_color_id_fk
        foreign key (color_id) references color (id)
);

INSERT INTO color (color_name) VALUES ('Black');
INSERT INTO color (color_name) VALUES ('White');
INSERT INTO color (color_name) VALUES ('Orange');