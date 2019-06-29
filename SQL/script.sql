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
	password text not null,
	reports_to integer references employee (employee_id)
);

create table reimbursement_type(
	reimbursement_type_id serial primary key,
	event_type text unique not null,
	payback_percentage integer not null
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
	denied boolean default false,
	grading_format text not null,
	passing_grade text not null
);

insert into request(employee_id, reimbursement_type_id, approval_reference_id, cost, location, date_of_event, time_of_event, description)
	values (1,1,1,100,'heart', '12/12/2011', '8:30','cool event');

insert into request(employee_id, reimbursement_type_id, approval_reference_id, cost, location, date_of_event, description)
	values (?,?,?,?,?,?,?);

update request
set employee_id = 7
where cost = 0;


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
insert into reimbursement_type(event_type, payback_percentage)
	values ('University Course', 80);

insert into reimbursement_type(event_type, payback_percentage)
	values ('Seminar', 60);

insert into reimbursement_type(event_type, payback_percentage)
	values ('Certification Preparation Classes', 75);

insert into reimbursement_type(event_type, payback_percentage)
	values ('Certification', 100);

insert into reimbursement_type(event_type, payback_percentage)
	values ('Technical Training', 90);

insert into reimbursement_type(event_type, payback_percentage)
	values ('Other', 30);
--!



--
insert into approval_reference(description)
	values ('Needs Supervisor Approval');

insert into approval_reference(description)
	values ('Needs Dept Head Approval');
--


select * from employee;

insert into employee(employee_type_id, username, password, reports_to)
	values (1, 'test', '123', 3);

insert into employee(employee_type_id, username, password, reports_to)
	values (1, 'associate', '123', 3);
	
	insert into employee(employee_type_id, username, password, reports_to)
	values (1, 'low-level', '123', 2);

insert into employee(employee_type_id, username, password, reports_to)
	values (2, 'supervisor', '456', 2);

insert into employee(employee_type_id, username, password)
	values (3, 'department head', '789');

insert into employee(employee_type_id, username, password, reports_to)
	values (4, 'benco', '321', 3);

select * from employee;
select * from request where employee_id = 5;

-- head to supervisor
select m.employee_id
from employee m
inner join employee h
on h.employee_id = m.reports_to
where h.employee_id = 2;

-- supervisor to associate
select e.employee_id, m.employee_id
from employee e
inner join employee m 
on m.employee_id = e.reports_to
where m.employee_id = 3;

--head to employee
select e.employee_id
from employee e
inner join employee m 
on m.employee_id = e.reports_to
where m.employee_id = (select m.employee_id
from employee m
inner join employee h
on h.employee_id = m.reports_to
where h.employee_id = 2)




--request for employee for head!
select * from request where employee_id in (select e.employee_id
from employee e
inner join employee m 
on m.employee_id = e.reports_to
where m.employee_id in (select m.employee_id
from employee m
inner join employee h
on h.employee_id = m.reports_to
where h.employee_id = 2)) and denied = false and approval_reference_id = 2;



select * from request where employee_id = 5 and denied = false;

-- requests for employee as supervisor
select * from request where employee_id in (select e.employee_id
from employee e
inner join employee m 
on m.employee_id = e.reports_to
where m.employee_id = 3) and denied = false and approval_reference_id = 1;

select * from employee where employee_id = 4;
select * from reimbursement_type where reimbursement_type_id = 4;


select * from request;

update request
set approval_reference_id = approval_reference_id + 1
where request_id = ?;

