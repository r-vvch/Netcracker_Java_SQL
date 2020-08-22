--7-04
/* Для всех сотрудников вывести id отдела, фамилию, специальность(job_id) и
количество людей в данном отделе с такой специальностью. */
SELECT department_id, last_name, job_id, COUNT(employee_id) OVER (PARTITION BY job_id, department_id)
FROM employees;

--7-05
/* Для всех сотрудников вывести фамилию, зарплату, фамилию менеджера 
и максимальную зарплату среди непосредственных подчиненных этого менеджера.
Если у сотрудника менеджер отсутствует, никакой информации для такого сотрудника выводить не нужно. */
SELECT emp.last_name, emp.salary, man.last_name as "MANAGER", MAX(emp.salary) OVER (PARTITION BY emp.manager_id)
FROM employees emp
INNER JOIN employees man ON emp.manager_id = man.employee_id;

--7-06
/* Для каждой локации (таблица LOCATIONS) вывести location_id, postal_code 
и количество локаций с тем же количеством символов в postal_code. */
SELECT location_id, postal_code, COUNT(location_id) OVER (ORDER BY LENGTH(postal_code) RANGE CURRENT ROW)
FROM locations;

--7-09
/* Для каждого сотрудника вывести id отдела, фамилию, дату приема на работу 
и фамилию сотрудника, принятого на работу в этот отдел самым первым.
Если таких несколько (приняты в один день) – вывести фамилию первого из них.
Указание: «первый» определяется функцией first_value. */
SELECT department_id, last_name, hire_date, FIRST_VALUE(last_name) OVER (PARTITION BY department_id ORDER BY hire_date)
FROM employees;

--7-10
/* Для всех сотрудников вывести фамилию(last_name), дату приёма на работу (hire_date), зарплату (salary) и разницу между 
зарплатой данного сотрудника и средней зарплатой всех сотрудников, нанятых за предыдущий год (включая данного сотрудника).
Указание: использовать аналитические функции и NUMTOYMINTERVAL. */
SELECT last_name, hire_date, salary, 
       salary - AVG(salary) OVER (
                                  ORDER BY hire_date DESC
                                  RANGE BETWEEN NUMTOYMINTERVAL(0, 'YEAR') FOLLOWING
                                  AND NUMTOYMINTERVAL(1, 'YEAR') FOLLOWING
                                 )
FROM employees;

--7-11
/* Для всех сотрудников вывести отдел (department_id), фамилию (last_name), зарплату (salary) и количество человек,
которые, работая в этом же отделе, имеют зарплату (строго) больше, чем данный сотрудник. */
SELECT department_id, last_name, salary,
       COUNT(employee_id) OVER (PARTITION BY department_id ORDER BY salary RANGE BETWEEN 1 FOLLOWING AND UNBOUNDED FOLLOWING)
FROM employees;

--7-12
/* Вывести имена сотрудников для второй страницы телефонного справочника при условиях, что:
а) сотрудники в справочнике упорядочены по фамилиям и затем по именам,
б) на одной странице справочника размещается ровно 10 записей.
Имена выводить в одном столбце в формате first_name пробел last_name. Никаких других столбцов выводить не надо.
Указание: использовать аналитическую функцию. */
SELECT NTH_VALUE(first_name || ' ' || last_name, 11) OVER (ORDER BY last_name ROWS BETWEEN 0 PRECEDING AND 11 FOLLOWING) a
FROM employees
FETCH NEXT 10 ROWS ONLY;

SELECT first_name || ' ' || last_name as "name"
FROM
    (
     SELECT first_name, last_name, ROW_NUMBER() OVER (ORDER BY last_name || first_name) as rnm
     FROM employees
     )
WHERE rnm < 21 AND rnm > 10;
