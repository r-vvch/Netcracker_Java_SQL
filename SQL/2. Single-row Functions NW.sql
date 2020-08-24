--NW-2.2
/* Выведите сегодняшнюю дату в следующих четырех столбцах (все столбцы - строкового типа):
1. Год (4 цифры)
2. Полное название месяца заглавными буквами
3. День месяца (число)
4. День недели словом заглавными буквами
Пример вывода:
| 2011 | OCTOBER | 04 | TUESDAY | */
SELECT to_char(sysdate, 'YYYY'),
       to_char(sysdate, 'MONTH'),
       to_char(sysdate, 'DD'),
       to_char(sysdate, 'DAY')
FROM dual;

--NW-2.3
/* В таблице CUSTOMERS адрес (address) может начинаться или заканчиваться числом.
Необходимо для всех клиентов вывести customerid, address и исправленный address, в котором из начала и конца строки убрано это число (т.е. цифры),
а также пробелы, запятые и точки (т.е. эти 3 символа рассматриваются так же, как цифры).
Например:
| ALFKI | Obere Str. 57  | Obere Str  |
| FRANR | 54, rue Royale | rue Royale | */
SELECT customerid, address,
       REGEXP_REPLACE(REGEXP_REPLACE(REGEXP_REPLACE(address,'^\d*\.*,*\s*',''), '\d*$', ''), '\.*\,*\s*$', '')
FROM customers;

--NW-2.4
/* Для всех имен продуктов (PRODUCTNAME) из таблицы PRODUCTS, которые состоят более чем из одного слова,
выведите первым столбцом само имя, вторым - только первое слово имени. Например:
| Teatime Chocolate Biscuits | Teatime |
Считать, что слова отделяются друг от друга пробелами. */
SELECT productname, regexp_substr(productname, '\S*' )
FROM products
WHERE REGEXP_COUNT(productname, '\s') > 0;

--NW-2.5
/* Вывести имя (PRODUCTNAME) для всех продуктов, у которых хотя бы одно из значений UNITSONORDER, REORDERLEVEL или DISCONTINUED отлично от 0
Grandma's Boysenberry Spread */
SELECT productname
FROM products
WHERE unitsonorder + reorderlevel + discontinued != 0;