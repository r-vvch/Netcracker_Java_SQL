--NW-7.2
/* Для каждой делавшей заказы компании из таблицы CUSTOMERS вывести имя заказчика (companyname)
и количество территорий (territoryid из таблицы ORDERS), на которых данный заказчик работает.
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
SELECT DISTINCT Customers.CompanyName, COUNT(DISTINCT Orders.TerritoryID) OVER(PARTITION BY Orders.CustomerID)
FROM Customers, Orders
WHERE Customers.CustomerID = Orders.CustomerID;

--NW-7.3
/* Для каждого заказа из таблицы ORDERS вывести customerid, orderid и суммарную стоимость всех компонентов этого заказа (с учетом скидки).
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
SELECT DISTINCT CustomerID, Orders.OrderID, SUM(UnitPrice * Quantity * (1 - Discount)) OVER (PARTITION BY Orders.CustomerID, Orders.OrderId) AS SUM
FROM Orders, OrderDetails
WHERE Orders.OrderID = OrderDetails.OrderID;

--NW-7.4
/* Для каждого заказа из таблицы ORDERS вывести порядковый номер (orderid) и отклонение стоимости его фрахта (freight)
от средней стоимости фрахта в пределах его территории. Отклонение должно быть неотрицательным.
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
SELECT OrderId, ABS(Freight - AVG(Freight) OVER (PARTITION BY TerritoryID))
FROM Orders;

--NW-7.7
/* Для каждой делавшей заказы компании из таблицы CUSTOMERS вывести имя заказчика (companyname), номер заказа (orderid) из таблицы ORDERS
и время (в днях), прошедшее с момента совершения предыдущего заказа этим же заказчиком (orderdate). Для первого заказа последний столбец равен 0.
Для заказов, совершенных заказчиком в один и тот же день, понятие "предыдущий заказ" определяется номером заказа (orderid).
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
SELECT Customers.CompanyName, Orders.OrderID,
       NVL(Orders.OrderDate - MAX(OrderDate) OVER(PARTITION BY Orders.CustomerID ORDER BY Orders.OrderDate, Orders.OrderID ROWS BETWEEN UNBOUNDED PRECEDING AND 1 PRECEDING), 0) DATEDIFF
FROM Customers, Orders
WHERE Customers.CustomerID = Orders.CustomerID;

--NW-7.8
/* Для каждой делавшей заказы компании из таблицы CUSTOMERS вывести имя заказчика (companyname), а тремя следующими столбцами -
минимальную, максимальную и среднюю стоимость заказов этого заказчика с учетом скидки (данные из таблиц ORDERS и ORDERDETAILS).
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
--????????
SELECT DISTINCT C.CompanyName, O.OrderID, SUM(UnitPrice * Quantity * (1 - Discount)) OVER (PARTITION BY O.CustomerID, O.OrderId) AS SUM
FROM Customers "C", Orders "O", Orderdetails "OD"
WHERE C.CustomerId = O.CustomerID AND O.OrderId = OD.OrderID;

SELECT DISTINCT C.CompanyName, O.OrderID, SUM(UnitPrice * Quantity * (1 - Discount)) OVER (PARTITION BY O.CustomerID, O.OrderId) AS SUM
FROM Customers "C", Orders "O", Orderdetails "OD"
WHERE C.CustomerId = O.CustomerID AND O.OrderId = OD.OrderID;

--WRONG
SELECT DISTINCT Customers.CompanyName,
       MIN(UnitPrice * Quantity * (1 - Discount)) OVER (PARTITION BY Orders.CustomerID) AS "MIN",
       MAX(UnitPrice * Quantity * (1 - Discount)) OVER (PARTITION BY Orders.CustomerID) AS "MAX",
       AVG(UnitPrice * Quantity * (1 - Discount)) OVER (PARTITION BY Orders.CustomerID) AS "AVG"
FROM Customers, Orders, OrderDetails
WHERE Customers.CustomerId = Orders.CustomerID AND Orders.OrderID = OrderDetails.OrderID;

--NW-7.9
/* Для каждой делавшей заказы компании из таблицы CUSTOMERS вывести имя заказчика (companyname), номер заказа (orderid) из таблицы ORDERS,
который имел наибольшую стоимость, и саму стоимость этого заказа с учетом скидки (из таблицы ORDERDETAILS).
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
--????????
SELECT DISTINCT CompanyName, Orders.OrderId, SUM(UnitPrice * Quantity * (1 - Discount)) OVER (PARTITION BY Orders.CustomerID, Orders.OrderId) AS SUM
FROM Customers, Orders, OrderDetails
WHERE Customers.CustomerID = Orders.CustomerID AND Orders.OrderID = OrderDetails.OrderID;