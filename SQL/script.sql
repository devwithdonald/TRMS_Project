--drop table helper
drop table employee_type cascade;
drop table employee cascade;
drop table reimbursement_type cascade;
drop table approval_reference cascade;
drop table request cascade;


--select * helpers
select * from employee_type;
select * from employee;
select * from reimbursement_type;
select * from approval_reference;
select * from request;

select emp.*, emp_type.employee_type 
from employee emp
inner join employee_type emp_type on emp.employee_type_id = emp_type.employee_type_id; 


-- create tables
create table employee_type(
	employee_type_id serial primary key,
	employee_type text not null
);

create table employee (
	employee_id serial primary key,
	employee_type_id integer references employee_type (employee_type_id) not null,
	username text unique not null,
	password text not null
);

create table reimbursement_type(
	reimbursement_type_id serial primary key,
	event_type text unique not null,
	payback_percentage integer not null,
	grading_format text not null
)

create table approval_reference (
	approval_reference_id serial primary key,
	description text
)

create table request (
	request_id serial primary key,
	employee_id integer references employee (employee_id) not null,
	reimbursement_type_id integer references reimbursement_type (reimbursement_type_id) not null,
	approval_reference_id integer references approval_reference (approval_reference_id) not null, 
	cost integer not null,
	location text not null,
	date_of_event date not null,
	time_of_event time not null,
	description text not null, 
	denied boolean default false
);

insert into request(employee_id, reimbursement_type_id, approval_reference_id, cost, location, date_of_event, time_of_event, description)
	values (1,1,1,100,'heart', '12/12/2011', '8:30','cool event');

insert into request(employee_id, reimbursement_type_id, approval_reference_id, cost, location, date_of_event, description)
	values (?,?,?,?,?,?,?);


-- insert default items
!-- employee types
insert into employee_type(employee_type)
	values ('associate');

insert into employee_type(employee_type)
	values ('supervisor');

insert into employee_type(employee_type)
	values ('department head');

insert into employee_type(employee_type)
	values ('benefits coordinator');
--!

!--need these
insert into reimbursement_type(event_type, payback_percentage, grading_format)
	values ('University Course', 80, 'grade');

insert into reimbursement_type(event_type, payback_percentage, grading_format)
	values ('Seminar', 60, 'grade');

insert into reimbursement_type(event_type, payback_percentage, grading_format)
	values ('Certification Preparation Classes', 75, 'grade');

insert into reimbursement_type(event_type, payback_percentage, grading_format)
	values ('Certification', 100, 'grade');

insert into reimbursement_type(event_type, payback_percentage, grading_format)
	values ('Technical Training', 90, 'grade');

insert into reimbursement_type(event_type, payback_percentage, grading_format)
	values ('Other', 30, 'grade');
--!




insert into approval_reference(description)
	values ('needs supervisor approval');





insert into employee(employee_type_id, username, password)
	values (1, 'test', '123');

insert into employee(employee_type_id, username, password)
	values (2, 'supervisor', '456');

insert into employee(employee_type_id, username, password)
	values (3, 'department head', '789');

insert into employee(employee_type_id, username, password)
	values (4, 'benco', '321');



