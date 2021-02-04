use tiny_theaters;
-- Find all performances in the last quarter of 2021 (Oct. 1, 2021 - Dec. 31 2021).
select *
from show_performance
where show_date >= '2021-10-01' and show_date <= '2021-12-31';

-- List customers without duplication.
select *
from customer;

-- Find all customers without a .com email address.
select *
from customer
where customer_email not like "%.com";

-- Find the three cheapest shows.
select *
from show_performance
order by ticket_price
limit 3;

-- List customers and the show they're attending with no duplication.
select
	concat(c.first_name, " ", c.last_name) customer_name,
    ts.show_title,
    sp.show_date
from ticket t
inner join customer c on t.customer_id = c.customer_id
inner join show_performance sp on t.performance_id = sp.performance_id
inner join theater_show ts on sp.show_id = ts.show_id
group by c.customer_id, sp.show_date, ts.show_title
order by c.customer_id;

-- List customer, show, theater, and seat number in one query.
select
	concat(c.first_name, " ", c.last_name) customer_name,
    ts.show_title,
    t.theater_name,
    s.seat_number
from ticket ti
inner join customer c on ti.customer_id = c.customer_id
inner join show_performance sp on ti.performance_id = sp.performance_id
inner join seat s on ti.seat_id = s.seat_id
inner join theater_show ts on sp.show_id = ts.show_id
inner join theater t on s.theater_id = t.theater_id;
    
-- Find customers without an address.
select *
from customer
where customer_address = "";

-- Recreate the spreadsheet data with a single query.
select
	c.first_name,
    c.last_name,
    c.customer_email,
    c.customer_phone,
    c.customer_address,
    s.seat_number,
    ts.show_title,
    sp.ticket_price,
    sp.show_date,
    t.theater_name,
    t.theater_address,
    t.theater_phone,
    t.theater_email
from customer c
inner join ticket ti on c.customer_id = ti.customer_id
inner join seat s on ti.seat_id = s.seat_id
inner join theater t on s.theater_id = t.theater_id
inner join show_performance sp on ti.performance_id = sp.performance_id
inner join theater_show ts on sp.show_id = ts.show_id
order by ts.show_id, c.customer_id;
    
-- Count total tickets purchased per customer.
select
	t.customer_id,
    c.first_name,
    c.last_name,
    c.customer_email,
	count(t.customer_id) num_tickets_per_customer
from ticket t
inner join customer c on t.customer_id = c.customer_id
group by t.customer_id;

-- Calculate the total revenue per show based on tickets sold.
select
	ts.show_title,
    sp.show_date,
    (count(t.ticket_number) * sp.ticket_price) total_revenue_per_show
from ticket t
inner join show_performance sp on t.performance_id = sp.performance_id
inner join theater_show ts on sp.show_id = ts.show_id
group by t.performance_id;
    
-- Calculate the total revenue per theater based on tickets sold.
select
	th.theater_name,
    sum(sp.ticket_price) total_revenue
from theater th
inner join show_performance sp on th.theater_id = sp.theater_id
inner join ticket t on sp.performance_id = t.performance_id
group by sp.theater_id;
    
-- Who is the biggest supporter of RCTTC? Who spent the most in 2021?
select
	concat(c.first_name, " ", c.last_name) customer_name,
    count(t.customer_id) num_tickets_per_customer,
    sum(sp.ticket_price) total_spent
from ticket t
inner join customer c on t.customer_id = c.customer_id
inner join show_performance sp on t.performance_id = sp.performance_id
group by t.customer_id
order by total_spent desc
limit 1;    