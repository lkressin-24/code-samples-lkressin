use tiny_theaters;

-- populate customers
select * from customer; -- 74 rows

select distinct customer_first, customer_last, customer_email, customer_phone, customer_address
from rcttc_data;

insert into customer(first_name, last_name, customer_email, customer_phone, customer_address)
	select distinct customer_first, customer_last, customer_email, customer_phone, customer_address
	from rcttc_data;

-- populate theater
select * from theater;

select distinct theater, theater_address, theater_phone, theater_email
from rcttc_data;

insert into theater(theater_name, theater_address, theater_phone, theater_email)
	select distinct theater, theater_address, theater_phone, theater_email
	from rcttc_data;

-- populate seat
select * from seat; -- 53 rows

select distinct r.seat, t.theater_id
from rcttc_data r
inner join theater t on r.theater = t.theater_name
order by t.theater_id, r.seat;

insert into seat(seat_number, theater_id)
	select distinct r.seat, t.theater_id
	from rcttc_data r
	inner join theater t on r.theater = t.theater_name
	order by t.theater_id, r.seat;

-- populate theater_show
select * from theater_show; -- 9 rows

select distinct `show`
from rcttc_data;

insert into theater_show(show_title)
    select distinct `show`
	from rcttc_data;

-- populate show_performance
select * from show_performance; -- 12 rows

select distinct r.ticket_price, r.`date`, t.theater_id, ts.show_id
from theater t
inner join rcttc_data r on r.theater = t.theater_name
inner join theater_show ts on r.`show` = ts.show_title;

insert into show_performance(ticket_price, show_date, theater_id, show_id)
	select distinct r.ticket_price, str_to_date(r.`date`, '%Y-%m-%d'), t.theater_id, ts.show_id
	from theater t
	inner join rcttc_data r on r.theater = t.theater_name
	inner join theater_show ts on r.`show` = ts.show_title;

-- populate ticket    
select * from ticket;

-- rows in csv file = 194 - must be 194 tickets
select distinct sp.performance_id, c.customer_id, s.seat_id 
from rcttc_data r
inner join customer c on r.customer_email = c.customer_email
inner join seat s on r.seat = s.seat_number
inner join show_performance sp on r.ticket_price = sp.ticket_price
	and r.`date` = sp.show_date and s.theater_id = sp.theater_id;

insert into ticket(performance_id, customer_id, seat_id)
    select distinct sp.performance_id, c.customer_id, s.seat_id 
	from rcttc_data r
	inner join customer c on r.customer_email = c.customer_email
	inner join seat s on r.seat = s.seat_number
	inner join show_performance sp on r.ticket_price = sp.ticket_price
		and r.`date` = sp.show_date and s.theater_id = sp.theater_id;

-- update price of The Little Fitz's 2021-03-10 performance of The Sky Lit Up   
select * from show_performance;

update show_performance set
	ticket_price = '22.25'
where performance_id = 5;

-- update seating for The Little Fitz's 2021-03-10 performance of The Sky Lit Up
select *
from customer
where customer_id between 35 and 40;

select *
from ticket
where performance_id = 5;

select *
from seat;

update ticket set
	seat_id = 29
where ticket_number = 101;

update ticket set
	seat_id = 35
where ticket_number = 100;

update ticket set
	seat_id = 33
where ticket_number = 98;	

-- update Jammie Swindles's phone number
select
	customer_id,
    first_name,
    last_name,
    customer_phone
from customer
where first_name = 'Jammie' and last_name = 'Swindles';

update customer set
	customer_phone = '1-801-EAT-CAKE'
where customer_id = 48;

-- delete single ticket reservations at The 10 pin -- total of 9 tickets
select
	customer_id,
    performance_id,
	count(customer_id) num_tickets_per_customer
from ticket
where performance_id between 1 and 4
group by customer_id, performance_id
having num_tickets_per_customer = 1;
-- (ci=7, pi=1), (ci=8, pi=2), (ci=10, pi=2), (ci=15, pi=2), (ci=18, pi=3)
-- (ci=19, pi=3), (ci=22, pi=3), (ci=25, pi=3), (ci=26, pi=4)

select *
from ticket
where performance_id between 1 and 4;

select
	ticket_number,
    customer_id,
    performance_id
from ticket
where customer_id in (7, 8, 10, 15, 18, 19, 22, 25, 26);

select
	ticket_number,
    customer_id,
    performance_id
from ticket
where ticket_number in (25, 26, 29, 41, 50, 51, 59, 67, 68);

delete from ticket
where ticket_number in (25, 26, 29, 41, 50, 51, 59, 67, 68);

-- delete reservations and customer Liv Egle of Germany
select
	t.ticket_number,
    c.first_name,
    c.last_name
from ticket t
inner join customer c on t.customer_id = c.customer_id
where c.first_name = 'Liv' and c.last_name = 'Egle of Germany';

delete from ticket
where ticket_number in (173, 174);

select
	customer_id,
	first_name,
    last_name
from customer
where first_name = 'Liv' and last_name = 'Egle of Germany';

delete from customer
where customer_id = 65;

select * from customer;

drop table rcttc_data;     