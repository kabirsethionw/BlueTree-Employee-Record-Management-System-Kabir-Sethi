create database employeedb;
use employeedb;

create table employee(
	id int auto_increment primary key,
    name varchar(255) not null,
    email varchar(255) not null,
    profile enum ('SALES_MANAGER', 'SALESMEN', 'PROGRAMMER', 'ANALYST', 'TESTER', 'PROJECT_MANAGER',
 		'OFFICE_ MANGER', 'NETWORK_ADMIN'),
    status enum('WORKING', 'FIRED'),
     joinedAt timestamp ,
     leftAt timestamp
);
select * from employee;
