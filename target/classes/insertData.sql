/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  UNGERW
 * Created: 02.06.2017
 */
Insert into T_SalesOrder (id, description, order_number) values (1, 'test order', '001');
Insert into T_SalesOrder (id, description, order_number) values (2, 'test order no 2', '002');
Insert into T_SalesOrder (id, description, order_number) values (3, 'test order no 3', '003');

Insert into T_SalesOrderDetail (id, description, detail_number, item, order_id) values (4, 'order detal', 1, 'Laptop', 1);
Insert into T_SalesOrderDetail (id, description, detail_number, item, order_id) values (5, 'order detal', 2, 'Iphone', 1);
Insert into T_SalesOrderDetail (id, description, detail_number, item, order_id) values (6, 'order detal', 3, 'USB Stick', 1);
Insert into T_SalesOrderDetail (id, description, detail_number, item, order_id) values (7, 'order detal', 1, 'Laptop', 2);
Insert into T_SalesOrderDetail (id, description, detail_number, item, order_id) values (8, 'order detal', 2, 'Monitor', 2);

