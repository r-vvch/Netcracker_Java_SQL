-- 3-01
/* Выведите в одну строчку: максимальную, среднюю и минимальную зарплату, суммарную зарплату,
количество сотрудников и количество отделов, в которых состоят сотрудники.
Все значения вычисляйте по всем сотрудникам таблицы employees */
SELECT MAX(salary), AVG(salary), MIN(salary), SUM(salary),
       COUNT(employee_id), COUNT(DISTINCT department_id)
FROM employees;

-- 3-02
/* Посчитайте количество сотрудников, у кого есть комиссионные, средние комиссионные среди всех сотрудников
и средние комиссионные среди получающих их сотрудников.
Под комиссионными здесь понимается процент комиссионных - commission_pct */
SELECT COUNT(commission_pct), SUM(commission_pct)/COUNT(employee_id), AVG(commission_pct)
FROM employees;

-- 3-04
/* Выведите все номера отделов, в которых состоят сотрудники, и максимальную зарплату по каждому из них.
Максимальная зарплата по сотрудникам, не состоящим ни в каком отделе, тоже должна выводиться (номер отдела - null). */
SELECT department_id, MAX(salary)
FROM employees
GROUP BY department_id;

-- 3-05
/* Для отдела с id = 50 выведите должности, занимаемые его сотрудниками.
В каждой строке выведите: должность (job_id) и максимальную зарплату сотрудников отдела в этой должности.
Результат отсортируйте по максимальной зарплате в порядке возрастания.
Указание: используйте только таблицу employees */
SELECT job_id, MAX(salary)
FROM employees
WHERE department_id = 50
GROUP BY job_id
ORDER BY MAX(salary);

-- 3-06
/* Для отделов с номером, не большим 50, выведите должности, занимаемые их сотрудниками.
В каждой строке выведите номер отдела, должность (job_id), максимальную и минимальную зарплату сотрудников отдела в этой должности.
В выборку включайте только те строки, где минимальная зарплата по должности больше 5000.
Результат отсортируйте по максимальной зарплате в порядке возрастания. Указание: используйте только таблицу employees */
SELECT department_id, job_id, MAX(salary), MIN(salary)
FROM employees
GROUP BY job_id, department_id
HAVING MIN(salary) > 5000 AND department_id <= 50
ORDER BY MAX(salary);

-- 3-07
/* Выведите максимальное значение средней зарплаты по отделам. Результат округлите до ближайшего целого. */
SELECT ROUND(MAX(AVG(salary)))
FROM employees
GROUP BY department_id;

-- 3-10
/* Выведите название отдела и среднюю зарплату сотрудников в отделе,
причем только для отделов, где средняя зарплата больше 5000. */
SELECT department_name, AVG(salary)
FROM employees
INNER JOIN departments ON employees.department_id = departments.department_id
GROUP BY department_name
HAVING AVG(salary) > 5000;

