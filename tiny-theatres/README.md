# Module 6 Assessment: Tiny Theatres
## Task List
### Friday, January 22
* [x] write DDL schema & execute (2:00) (actual 1:00)
### Saturday, January 23
* [x] import csv file (0:15) (actual 0:15)
* [x] populate tables (2:00) (actual 6:30)
* [x] write dml (2:00) (actual 2:00)
* [x] write queries (2:00) (actual 3:20)

estimated: 8:00 
actual: 13:05

### Sunday, January 24
* [ ] rediagram for stretch goals (actual 1:00)
* [ ] edit schema to include stretch goals (2:00)
* [ ] populate tables (2:00)
* [ ] write queries for stretch goals (2:00)

## Approach
Plan before you write code.

### Step 1: DDL
The delimited data is denormalized. It's effectively a single table. Analyze the data. Decide which values belong to an independent concept. Decide how concepts are related. In this initial step, it's very useful to draw diagrams. Show your diagram to your instructor to confirm you're heading in the right direction. Ask a lot of questions.

Once you're satisfied with concepts and relationships, finalize names, keys, and data types. Column names in the data file shouldn't necessarily be used as-is. It's likely you'll need additional columns including, but not limited to, surrogate keys.

Write the DDL and save it in a file named rcttc-schema.sql. Your instructor should be able to execute the script over and over. Each time it starts over and builds the RCTTC database from scratch.

### Step 2: DML
#### Insert
* Insert the delimited data into your database.
* Use MySQL Workbench's Table Data Import Wizard to import all data into a denormalized table. Use SQL to populate the normalized schema. Drop the denormalized table when you're finished with it.
* Scan the data carefully. Repeated data shouldn't be repeated in a normalized database. For example, the customer "Jammie Swindles" is one person. They made reservations for multiple seats and multiple performances. Don't add more than one "Jammie Swindles" to a customer table.

#### Update
* The Little Fitz's 2021-03-01 performance of The Sky Lit Up is listed with a $20 ticket price. The actual price is $22.25 because of a visiting celebrity actor. (Customers were notified.) Update the ticket price for that performance only.
* In the Little Fitz's 2021-03-01 performance of The Sky Lit Up, Pooh Bedburrow and Cullen Guirau seat reservations aren't in the same row. Adjust seating so all groups are seated together in a row. This may require updates to all reservations for that performance. Confirm that no seat is double-booked and that everyone who has a ticket is as close to their original seat as possible.
* Update Jammie Swindles's phone number from "801-514-8648" to "1-801-EAT-CAKE".
#### Delete
* Delete all single-ticket reservations at the 10 Pin. (You don't have to do it with one query.)
* Delete the customer Liv Egle of Germany. It appears their reservations were an elaborate joke.

Save all DML in a file named rcttc-data.sql.

### Step 3: DQL (Data Query Language)
Complete the following queries.

* Find all performances in the last quarter of 2021 (Oct. 1, 2021 - Dec. 31 2021).
* List customers without duplication.
* Find all customers without a .com email address.
* Find the three cheapest shows.
* List customers and the show they're attending with no duplication.
* List customer, show, theater, and seat number in one query.
* Find customers without an address.
* Recreate the spreadsheet data with a single query.
* Count total tickets purchased per customer.
* Calculate the total revenue per show based on tickets sold.
* Calculate the total revenue per theater based on tickets sold.
* Who is the biggest supporter of RCTTC? Who spent the most in 2021?

Save all DQL in a file named rcttc-queries.sql.

## Notes
### High Level Requirements
* Use RCTTC's data to design a multi-table schema with appropriate relationships.
* Build a SQL DDL script to create the schema.
* Populate the database with sample data from a delimited data file. Save the DML SQL so it can be executed whenever needed.
* Write report queries and confirm they're working with sample data.

### Deliverables
Drawing - see rcttc-drawing.jpg
* [ ] Database diagram
* [x] rcttc-schema.sql: re-runnable DDL
* [x] rcttc-data.sql: data populating DML
* [x] rcttc-queries.sql: queries

### Stretch Goals
Add schema for the following concepts:

* [ ] login, so customers can make and check reservations online
* [ ] cast and crew for each performance
* [ ] discounts and promotions
* [ ] payments, payment types
* [ ] Populate new schema with sample data.

Confirm it's working as expected with queries.