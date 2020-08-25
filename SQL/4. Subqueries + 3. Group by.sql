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
SELECT OrderId, TO_CHAR(SUM(UnitPrice * Quantity * (1 - Discount)), '99999.99') as SUM
FROM OrderDetails
GROUP BY OrderId
HAVING SUM(UnitPrice * Quantity * (1 - Discount)) > 5000;

--NW-3.5
/* Найти заказчиков (customers), сделавших заказы в тот день или те дни (orderdate), в которые было совершено наибольшее число заказов.
Вывести: дату (orderdate) и название компании-заказчика (companyname). */
SELECT O.OrderDate, C.CompanyName
FROM Orders O,
     (
     SELECT OrderDate, COUNT(*)
     FROM Orders
     GROUP BY OrderDate
     HAVING COUNT(*) = (SELECT MAX(COUNT(OrderID))
                        FROM Orders
                        GROUP BY OrderDate)
     ) D,
     Customers C
WHERE O.OrderDate = D.OrderDate AND O.CustomerID = C.CustomerID;

--NW-3.7
/* Выведите идентификатор (supplierid) всех поставщиков (suppliers), поставляющих продукцию (products) только одной категории (categories),
а также имя этой категории (categoryname) */
SELECT S.SupplierID, C.CategoryName
FROM Suppliers S,
     (SELECT SupplierID, MAX(CategoryID) "CAT", COUNT(DISTINCT CategoryID)
      FROM Products
      GROUP BY SupplierID
      HAVING COUNT(DISTINCT CategoryID) = 1) P,
      Categories C
WHERE S.SupplierID = P.SupplierID AND P.CAT = C.CategoryID;

--NW-3.8
/* Выведите имена (companyname) и адреса (address) заказчиков (customers), не сделавших ни одного заказа (orders) */
SELECT CompanyName, Address
FROM Customers, Orders
WHERE Customers.CustomerID = Orders.CustomerID(+)
AND OrderID IS NULL;
