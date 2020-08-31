--6-1
/* Для сотрудника с employee_id = 110 выведите полную строку его подчинения, где менеджеры разделены символом #.
Имя сотрудника следует выводить в формате first_name last_name.
Пример строки подчинения: #employee#manager#manager_of_manager
(первым должен идти сам сотрудник, затем - его непосредственный менеджер, ..., последним - топ-менеджер).
Указание: использование SYS_CONNECT_BY_PATH. */
SELECT SYS_CONNECT_BY_PATH(first_name || ' ' || last_name, '#')
FROM employees
WHERE manager_id IS NULL
START WITH employee_id = 110
CONNECT BY PRIOR manager_id = employee_id;

--6-4
/* Выведите всех имеющихся в таблице employees сотрудников в виде иерархии подчинения,
отступая каждый уровень подчинения от предыдущего на четыре символа '-'.
На каждом уровне сотрудники должны быть отсортированы по last_name. Указание: использование сортировки SIBLINGS BY.
Имя сотрудника следует выводить в формате first_name last_name.
Подсказка: начинать следует с тех сотрудников, у кого не заполнен manager_id.

Пример конечной выборки:
EMP_NAME
------------------------------------
Steven King
----Gerald Cambrault
--------Elizabeth Bates
--------Harrison Bloom */
SELECT LPAD('-------------------------', 4*(level-1)) || first_name || ' ' || last_name as END_NAME
FROM employees
START WITH manager_id IS NULL
CONNECT BY manager_id = PRIOR employee_id
ORDER SIBLINGS BY last_name;

--6-5
/* Вывести сотрудников, находящихся в транзитивном (не прямом) подчинении у сотрудника с id = 102
О каждом сотруднике вывести employee_id, first_name, last_name. */
SELECT employee_id, first_name, last_name
FROM employees
WHERE LEVEL > 2
START WITH employee_id = 102
CONNECT BY manager_id = PRIOR employee_id
ORDER SIBLINGS BY last_name;

--7-01
/* Для всех сотрудников вывести фамилию сотрудника, его комиссионные (commission_pct)
и средние комиссионные по всем сотрудникам компании.
Указание: при расчете средних комиссионных учесть, что некоторые из них могут быть NULL. */
SELECT last_name, commission_pct, AVG(NVL(commission_pct,0)) OVER ()
FROM employees;

--7-03
/* Для каждой страны вывести ее название, название региона, в котором она находится и количество стран, находящихся в этом регионе. */
SELECT country_name, region_name, COUNT(*) OVER (PARTITION BY region_name)
FROM countries, regions
WHERE countries.region_id = regions.region_id;

--7-07
/* Для всех сотрудников вывести фамилию сотрудника, дату устройства в компанию, зарплату и
среднюю зарплату всех сотрудников, нанятых (строго) позже него.
Пример вывода:
LAST_NAME | HIRE_DATE | SALARY | AVERAGE |
Lorentz   | 07-FEB-99 | 4200   | 5100    |
Cabrio    | 07-FEB-99 | 3000   | 5100    |
Smith     | 23-FEB-99 | 7400   | 2800    |
Jones     | 17-MAR-99 | 2800   | (null)  |
(в примере для наглядности предполагается, что в рассмотрении находятся
только эти 4 сотрудника - остальные исключены с помощью WHERE) */
SELECT last_name, hire_date, salary,
       AVG(salary) OVER (
                         ORDER BY hire_date
                         RANGE BETWEEN 1 FOLLOWING
                         AND UNBOUNDED FOLLOWING
                        ) AVERAGE
FROM employees;

--NW-7.1
/* Для каждой делавшей заказы компании из таблицы CUSTOMERS вывести имя заказчика (companyname)
и адрес доставки (shipaddress) первого заказа этой компании из таблицы ORDERS
Указание: «первый» определяется функцией first_value.
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
SELECT DISTINCT Customers.CompanyName, --Orders.ShipAddress, orders.orderid, orders.orderdate,
       FIRST_VALUE(Orders.ShipAddress) OVER(PARTITION BY Orders.CustomerID ORDER BY Orders.OrderDate )
FROM Customers, Orders
WHERE Customers.CustomerID = Orders.CustomerID;

--NW-7.5
/* Для каждого заказа из таблицы ORDERS вывести его номер (orderid), номер территории (territoryid),
и "номер на территории" - число, показывающее, каким по очереди был сделан этот заказ на данной территории.
Заказы с одной датой получают одинаковый "номер на территории", из-за этого в нумерации могут быть "дырки".
Примечание: предполагается, что запрос будет содержать только одно слово SELECT */
SELECT OrderID, TerritoryID, RANK() OVER(PARTITION BY TerritoryID ORDER BY OrderDate)
FROM orders;