-- 2-01
/* Выведите числовое значение (в коде ASCII) символа 'a', символа 'A' и третьей колонкой символ, ASCII код которого равен 42.
Указание: Использование ASCII и CHR. */
SELECT ASCII('a') as "ASCII('a')", ASCII('A'), CHR(42)
FROM dual;

-- 2-02
/* Из таблицы LOCATIONS выведите первым столбцом уникальный идентификатор местоположения (LOCATION_ID),
вторым столбцом street — адрес, образованный усечением слева цифр и пробелов у колонки STREET_ADDRESS.
Указание: Использование LTRIM. */
SELECT location_id, LTRIM(street_address, '0123456798 ') as "street"
FROM locations;

-- 2-06
/* Выведите зарплату сотрудников как строку длины 5 символов; в случае необходимости дополнить строку слева нулями до требуемой длины.
Указание: Использование LPAD. */
SELECT LPAD(salary, 5, '0')
FROM employees;

-- 2-07
/* Выведите имя, фамилию сотрудника и третьей колонкой должность (JOB_ID), заменив в ней встречающиеся символы '_' на '-'.
Отсортировать полученные результаты по суммарной длине имени и фамилии, затем по первому и второму столбцу выборки.
Указание: Использование LENGTH, REPLACE. */
SELECT first_name, last_name, REPLACE(job_id, '_', '-')
FROM employees
ORDER BY LENGTH(first_name || last_name), 1, 2;

-- 2-08
/* Выведите имя, фамилию сотрудника, его email и четвертой колонкой — email сотрудника,
заменив в нем символы по следующему соответствию: A-a, E-e, I-i, O-o, U-u, Y-y.
Указание: Использование TRANSLATE. */
SELECT first_name, last_name, email, TRANSLATE(email, 'AEIOUY', 'aeiouy')
FROM employees;

-- 2-09
/* Выведите имена и фамилии всех сотрудников (first_name, last_name) и в 3-6 столбцах выведите их зарплату
в предположении, что из нее вычли налог 13%, в следующих формах:
- low: округленная до целого снизу,
- up: округленная до целого сверху,
- round_k: округленная до сотен, выраженная в тысячах,
- trunk_k: усеченная до сотен, выраженная в тысячах.
Пример вывода:
| FIRST_NAME | LAST_NAME |  LOW  |  UP   | ROUND_K | TRUNK_K |
|   Arthur   |  Wilson   | 20871 | 20872 |  20.9   | 20.8    |
Указание: Использование ROUND, TRUNC, CEIL, FLOOR. */
SELECT first_name,
       last_name,
       FLOOR(salary*0.87) as "LOW",
       CEIL(salary*0.87) as "UP",
       ROUND(salary*0.87*0.001, 1) as "ROUND_K",
       TRUNC(salary*0.87*0.001, 1) as "TRUNC_K"
FROM employees;

-- 2-10
/* Выведите 'Yes', если в разложении числа 123456789 по степеням двойки существует двойка в степени 10, и 'No' иначе.
Указание: Использование BITAND, POWER. */
SELECT CASE
        WHEN BITAND(123456789, POWER(2,10)) = 1024
        THEN 'Yes'
        ELSE 'No'
       END as "bool"
FROM dual;

-- 2-11
/* Выведите 'Yes', если число 3607 является делителем числа 123456789, и 'No' иначе.
Указание: Использование MOD. */
SELECT CASE
        WHEN MOD(123456789,3607) = 0
        THEN 'Yes'
        ELSE 'No'
       END as "bool"
FROM dual;

-- 2-14
/* Предположим, что значение '$1,250' соответствует одной единице зарплаты
(здесь число в американском формате - запятая отделяет тысячи, а не дробную часть).
Выведите для тех сотрудников, у кого есть комиссионные:
1) числовое значение зарплаты в указанных единицах;
2) сумму комиссионных в виде строки с префиксом '$'
(например, '$3,456'; колонка commission_pct - это доля зарплаты, начисляемая в качестве комиссионных);
3) commission_pct в виде строки в формате '0.12'.
Ведущих и хвостовых пробелов быть не должно.
Для каждого столбца используйте только функцию преобразования типов (для первого столбца - TO_NUMBER). см. также Format Models.

Пример:
| ITEMS | COMM   | COMM_PCT |
| 11.2  | $5,600 | 0.40     |
| 5.76  | $720   | 0.10     | */

SELECT TO_NUMBER(TO_CHAR(salary, '9G999G999', 'NLS_NUMERIC_CHARACTERS = .,')) as "1)",
       TO_CHAR(commission_pct * salary, '999G999', 'NLS_NUMERIC_CHARACTERS = .,') as "2)",
       TO_CHAR(commission_pct, '990D99', 'NLS_NUMERIC_CHARACTERS = .,') as "3)"
FROM employees
WHERE commission_pct IS NOT NULL;

-- 2-15
/* Выведите всю информацию о сотрудниках, фамилии которых начинаются с H и K,
содержат 2 одинаковые буквы подряд, а оставшиеся после повторяющихся букв часть фамилии не заканчивается на букву s.
Указание: Использование REGEXP_LIKE */
SELECT *
FROM employees
WHERE REGEXP_LIKE(last_name, '(\w)\1{1,}') AND REGEXP_LIKE(last_name, '(H|K)')
        AND REGEXP_LIKE(last_name, '\w*[a-z][^s]$') AND REGEXP_REPLACE(last_name, ' ', '') = last_name;

-- 2-16
/* Из таблицы LOCATIONS выведите адрес (STREET_ADRESS) и позицию второго буквенного символа, входящего в ту же строку (адрес).
Указание: Использование REGEXP_INSTR */
SELECT street_address, REGEXP_INSTR(street_address, '[A-z]', 1, 2)
FROM locations

-- 2-17
/* Выведите все страны из таблицы COUNTRIES: первым столбцом выведите название страны (COUNTRY_NAME), 
вторым столбцом — ту же строку (называние страны), в которой исключены гласные буквы со всех позиций, кроме первой позиции в строке.
Гласными считаются буквы A,E,I,O,U,Y */
SELECT country_name, REGEXP_REPLACE(country_name, 'A*E*I*O*U*Y*a*e*i*o*u*y*', '', 2)
FROM countries;

-- 2-19
/* Выведите текущую дату, время и часовой пояс в формате: <ГОД-МЕС-ДЕНЬ ЧАС24:МИН:СЕК.МИЛЛИСЕК ПОЯС>,
где миллисекунды — это обязательно 3 цифры, а часовой пояс включат минуты (часы:минуты).
Например: 2010-10-02 17:51:32.369 +03:00
Указание: Использование SYSTIMESTAMP. */
    --DATEFORMAT = '<MTEXTL>- <D>-<YYYY>'
    --SELECT SYSTIMESTAMP
    --FROM dual

-- 2-21a
/* Для всех сотрудников выведите дату приёма на работу (hire_date), имя сотрудника, его фамилию и текущий стаж, 
т.е. число полных лет и полных месяцев, которые он отработал с даты приёма.
Число лет и месяцев следует выводить даже в том случае, если это число 0.
Пример вывода стажа:
12 Years, 1 Months
Указание: Использовать MONTHS_BETWEEN и числовые функции.
Примечание: следует обратить внимание на сотрудника, которому отдел кадров ошибочно указал дату приёма в будущем.
В этом случае число месяцев (и лет) будет отрицательным. Например:
0 Years, -6 Months */
SELECT hire_date,
       first_name,
       last_name,
       FLOOR(FLOOR(MONTHS_BETWEEN(SYSTIMESTAMP, hire_date)) / 12) || ' Years, ' ||
       (FLOOR(MONTHS_BETWEEN(SYSTIMESTAMP, hire_date)) - FLOOR(FLOOR(MONTHS_BETWEEN(SYSTIMESTAMP, hire_date)) / 12) * 12) || 
       ' Months' AS "EXPERIENCE"
FROM employees;

-- 2-22
/* Предположим, что в компании зарплата начисляется с пятого числа каждого месяца, 
и зарплату сотрудник начинает получать, начиная со следующего месяца приёма на работу.
Вывести для всех сотрудников: дату приёма на работу и дату, когда сотруднику была выплачена первая зарплата.
Подсказка: перед добавлением к дате одного месяца лучше использовать функцию TRUNC */
SELECT hire_date, ADD_MONTHS(TRUNC(hire_date, 'MM'), 1) + 4
FROM employees

-- 2-28
/* Выведите сотрудников, у которых комиссионные меньше 20% или не указаны.
Задачу решите с применением только одного условия (без OR).
Указание: использование LNNVL. */
SELECT *
FROM employees
WHERE LNNVL(commission_pct >= 0.2);

-- 2-29
/* Выведите имена и фамилии всех сторудников (first_name, last_name) 
и в 3-7 столбцах выведите размер их комиссионных (commission_pct * salary) или 0, если они отсутствуют, — 
пятью различными способами: с помощью функций NVL, COALESCE, NVL2, DECODE, CASE. */
SELECT first_name,
       last_name,
       NVL(commission_pct * salary, 0) as "First",
       COALESCE(commission_pct * salary, 0) as "Second",
       NVL2(commission_pct * salary, commission_pct * salary, 0) as "Third",
       DECODE(commission_pct * salary, NULL, 0,
                                      commission_pct * salary) as "Forth",
       CASE
         WHEN commission_pct * salary IS NULL
         THEN 0
         ELSE commission_pct * salary
       END as "Fifth"
FROM employees;

-- NW-2.2

-- NW-2.3

-- NW-2.4

-- NW-2.5