--drop table helper
drop table employee_type cascade;
drop table reimbursement_type cascade;
drop table approval_reference cascade;

drop table employee cascade;
drop table request cascade;
drop table reimbursement_award cascade;


--select * helpers
select * from employee_type;
select * from reimbursement_type;
select * from approval_reference;

select * from employee;
select * from request;
select * from reimbursement_award;


--select emp.*, emp_type.employee_type 
--from employee emp
--inner join employee_type emp_type on emp.employee_type_id = emp_type.employee_type_id; 


-- create tables
create table employee_type(
	employee_type_id serial primary key,
	employee_type text not null
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

create table employee (
	employee_id serial primary key,
	employee_type_id integer references employee_type (employee_type_id) not null,
	username text unique not null,
	password text not null,
	reports_to integer references employee (employee_id),
	available_balance integer default 1000,
	pending_balance integer default 0
);

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
	passing_grade text not null,
	grade_received text,
	award_given boolean default false,
	need_additional_info boolean default false,
	requested_additional_info text
);


create table reimbursement_award(
	reimbursement_award_id serial primary key,
	employee_id integer references employee (employee_id) not null,
	reimbursement_type_id integer references reimbursement_type (reimbursement_type_id) not null,
	date_gifted timestamp default current_timestamp,
	amount_gifted integer not null
);



-- insert default employee types
insert into employee_type(employee_type)
	values ('associate');

insert into employee_type(employee_type)
	values ('supervisor');

insert into employee_type(employee_type)
	values ('department head');

insert into employee_type(employee_type)
	values ('benefits coordinator');


-- insert default reimbursement types
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



--insert default approval references
insert into approval_reference(description)
	values ('Needs Supervisor Approval');

insert into approval_reference(description)
	values ('Needs Dept Head Approval');

insert into approval_reference(description)
	values ('Needs BenCo Approval');

insert into approval_reference(description)
	values ('Presentation - Needs Supervisor Approval');

insert into approval_reference(description)
	values ('Grade - Needs BenCo Approval');


--dummy date for employees (two depts)
insert into employee(employee_type_id, username, password)
	values (3, 'depthead_1', 'd1');

insert into employee(employee_type_id, username, password, reports_to)
	values (2, 'supervisor_1', 's1', 1);

insert into employee(employee_type_id, username, password, reports_to)
	values (1, 'associate_1', 'a1', 2);

insert into employee(employee_type_id, username, password, reports_to)
	values (4, 'benco', 'b', 2);

insert into employee(employee_type_id, username, password)
	values (3, 'depthead_2', 'd2');

insert into employee(employee_type_id, username, password, reports_to)
	values (2, 'supervisor_2', 's2', 5);

insert into employee(employee_type_id, username, password, reports_to)
	values (1, 'associate_2', 'a2', 6);

insert into employee(employee_type_id, username, password, reports_to)
	values (1, 'associate_2_1', 'a21', 6);






--run first (demo)
select * from reimbursement_award;

--run second (demo)
insert into request (employee_id, reimbursement_type_id, approval_reference_id, cost, location, date_of_event, time_of_event, description, grading_format, passing_grade)
	values(7,2,1,500,'Los Angeles', '2019-08-15', '09:30:00', 'Leadership Seminar', '0-100',70),
			(7,4,1,100,'New York', '2019-09-24', '10:25:00', 'OCA', 'Pass/Fail', 'Pass'), 
				(8,1,1,600,'Oregon', '2020-03-06', '09:25:00', 'Java Classes', 'A-F', 'B'),
					(3,3,1,100,'Arizona', '2019-08-23', '07:30:00', 'OCA Prep', '0-100', 85);
