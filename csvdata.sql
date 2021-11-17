-- Drop table

-- DROP TABLE public.csvdata

CREATE TABLE public.csvdata (
	id int8 NULL,
	region varchar(100) NULL,
	country varchar(100) NULL,
	item_type varchar(100) NULL,
	sales_channel varchar(100) NULL,
	order_priority varchar(20) NULL,
	order_date varchar(30) NULL,
	ship_date varchar(20) NULL,
	orderid varchar(20) NULL,
	units_sold varchar(10) NULL,
	unit_price varchar(20) NULL,
	unit_cost varchar(20) NULL,
	total_revenue varchar(20) NULL,
	total_cost varchar(20) NULL,
	total_profit varchar(20) NULL
);
