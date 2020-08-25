--NW-3.3
/* Вывести заказы, стоимость которых выше 5000 (с учетом скидки).
Для каждого заказа вывести orderid и стоимость (с учетом скидки), для отображения стоимости использовать форматирование:
5 знаков до запятой и 2 знака после запятой (всего 8 символов, считая десятичный разделитель, а также лидирующие пробелы для чисел, меньших 10000):
| ORDERID |   SUM    |
|  10360  |  7390.20 |
|  10817  | 10952.85 |
|   ...   |   ...    |
Таблица orderdetails:
unitprice - цена за единицу товара
discount - скидка в долях от единицы
quantity - количество товара
Подсказка: обратите внимание на то, что функция TO_CHAR c тем форматом, где число знаков до запятой фиксировано,
выдает дополнительный лидирующий пробел для положительных чисел. */
SELECT OrderId, TO_CHAR(UnitPrice * Quantity * (1 - Discount), '99999.99') as SUM
FROM OrderDetails
WHERE UnitPrice * Quantity * (1 - Discount) > 5000;

--NW-3.5
/* Найти заказчиков (customers), сделавших заказы в тот день или те дни (orderdate), в которые было совершено наибольшее число заказов.
Вывести: дату (orderdate) и название компании-заказчика (companyname). */
SELECT O.orderdate, C.companyname
FROM orders O,
     (
     SELECT orderdate, COUNT(*)
     FROM orders
     GROUP BY orderdate
     HAVING COUNT(*) = (SELECT MAX(COUNT(orderid))
                        FROM orders
                        GROUP BY orderdate)
     ) D,
     customers C
WHERE O.orderdate = D.orderdate AND O.customerid = C.customerid;

--NW-3.7
/* Выведите идентификатор (supplierid) всех поставщиков (suppliers), поставляющих продукцию (products) только одной категории (categories),
а также имя этой категории (categoryname) */
SELECT S.supplierid, C.categoryname
FROM suppliers S,
     (SELECT supplierid, MAX(categoryid) "CAT", COUNT(DISTINCT categoryid)
      FROM products
      GROUP BY supplierid
      HAVING COUNT(DISTINCT categoryid) = 1) P,
      categories C
WHERE S.supplierid = P.supplierid AND P.CAT = C.categoryid;

--NW-3.8
/* Выведите имена (companyname) и адреса (address) заказчиков (customers), не сделавших ни одного заказа (orders) */
SELECT companyname, address
FROM customers, orders
WHERE customers.customerid = orders.customerid(+)
AND orderid IS NULL;
