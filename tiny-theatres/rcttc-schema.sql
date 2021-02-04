drop database if exists tiny_theaters;
create database tiny_theaters;
use tiny_theaters;

create table theater (
	theater_id int primary key auto_increment,
    theater_name varchar(100) not null,
    theater_address varchar(100) not null,
    theater_city varchar(50) not null,
    theater_state varchar(5) not null,
    theater_zipcode varchar(5) not null,
    theater_phone varchar(25) not null,
    theater_email varchar(100) not null
);

create table customer (
	customer_id int primary key auto_increment,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    customer_email varchar(100) not null,
    customer_phone varchar(25) null,
    customer_address varchar(100) null
);

create table theater_show (
	show_id int primary key auto_increment,
    show_title varchar(50) not null
);

create table show_performance (
	performance_id int primary key auto_increment,
    ticket_price decimal(8,2) not null,
    show_date date not null,
    theater_id int,
    show_id int,
    constraint fk_show_performance_theater_id
		foreign key (theater_id)
        references theater(theater_id),
    constraint fk_show_performance_show_id
		foreign key (show_id)
        references theater_show(show_id)
);

create table seat (
	seat_id int primary key auto_increment,
    seat_number varchar(2) not null,
    theater_id int,
    constraint fk_seat_theater_id
		foreign key (theater_id)
        references theater(theater_id)
);

create table ticket (
	ticket_number int primary key auto_increment,
    performance_id int,
    customer_id int,
    seat_id int,
    constraint fk_ticket_performance_id
		foreign key (performance_id)
        references show_performance(performance_id),
    constraint fk_ticket_customer_id
		foreign key (customer_id)
        references customer(customer_id),
    constraint fk_ticket_seat_id
		foreign key (seat_id)
        references seat(seat_id)
);

alter table theater
	drop column theater_city,
    drop column theater_state,
    drop column theater_zipcode;
        