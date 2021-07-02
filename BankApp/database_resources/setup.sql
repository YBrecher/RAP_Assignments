drop table if exists customers cascade;
drop table if exists employees cascade;
drop table if exists accounts cascade;

create table if not exists customers(
	customer_id serial primary key,
	username text not null unique,
	password text not null,
	first_name text not null,
	last_name text not null

);

create table if not exists employees(
	employee_id serial primary key,
	username text not null unique,
	password text not null,
	first_name text not null,
	last_name text not null


);

create table if not exists accounts(
	account_id serial primary key,
	customer_id integer references customers(customer_id) not null,
	balance decimal not null default (0)

);