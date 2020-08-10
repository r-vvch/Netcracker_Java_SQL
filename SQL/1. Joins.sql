-- 1-20
/* Вывести информацию обо всех отделах и состоящих в них сотрудниках.
В первом столбце - id отдела, во втором - id сотрудника.
Если в отделе нет ни одного сотрудника, то второй столбец в строке такого отдела должен содержать NULL.
Решите задачу, используя синтаксис Oracle */
SELECT departments.department_id, employees.employee_id
FROM departments
LEFT OUTER JOIN employees ON departments.department_id = employees.department_id;

-- 1-21
/* Вывести сотрудников (employees), у которых менеджер (manager_id) не совпадает с менеджером отдела,
где работает сотрудник (department_id, см. также атрибут manager_id в таблице departments).
Вывести нужно 3 столбца, в каждом из которых - фамилия (last_name): сотрудника, менеджера сотрудника, менеджера отдела.
Указание: использовать явное именование столбцов результирующей выборки (aliases) */
SELECT A1.last_name as "EMPLOYEE", A2.last_name as "EMP_MANAGER", A3.last_name as "DEP_MANAGER"
FROM employees A1
INNER JOIN departments ON A1.department_id = departments.department_id
INNER JOIN employees A2 ON A1.manager_id = A2.employee_id
INNER JOIN employees A3 ON departments.manager_id = A3.employee_id
WHERE A1.manager_id != A3.employee_id;

-- 1-22
/* Для всех отделов, располагающихся в городе 'Seattle', вывести название отдела и номер телефона его менеджера.
Если отдел не имеет менеджера, второй столбец должен содержать NULL. */
SELECT departments.department_name, employees.phone_number
FROM departments
LEFT JOIN employees ON departments.manager_id = employees.employee_id
INNER JOIN locations ON departments.location_id = locations.location_id
WHERE locations.city = 'Seattle';

-- 1-31
/* Вывести названия всех отделов, располагающихся по адресу '2004 Charade Rd'. */
SELECT department_name
FROM departments
INNER JOIN locations ON departments.location_id = locations.location_id
WHERE street_address = '2004 Charade Rd';

-- 1-32
/* Вывести в первом столбце названия всех отделов компании, а втором - названия стран, где они располагаются. */
SELECT department_name, country_name
FROM departments
INNER JOIN locations ON departments.location_id = locations.location_id
INNER JOIN countries ON locations.country_id = countries.country_id;

-- 1-33
/* Вывести телефон менеджера сотрудника по фамилии 'Chen'. */
SELECT A2.phone_number
FROM employees A1
LEFT JOIN employees A2 ON A1.manager_id = A2.employee_id
WHERE A1.last_name = 'Chen';

-- 1-34
/* Для всех отделов компании вывести название отдела и фамилию (last_name) его менеджера.
Если отдел не имеет менеджера, второй столбец должен содержать NULL. */
SELECT department_name, last_name
FROM departments
LEFT JOIN employees ON departments.manager_id = employees.employee_id;

-- 1-35
/* Вывести почтовый код отдела, в котором работает сотрудник по фамилии 'Sully'. */
SELECT postal_code
FROM employees
INNER JOIN departments ON employees.department_id = departments.department_id
INNER JOIN locations ON departments.location_id = locations.location_id
WHERE last_name = 'Sully'