--4-01
/* Выведите все данные (*) о сотрудниках с зарплатой, равной максимальной зарплате по всей компании.
Указание: использовать только таблицу employees. */
SELECT *
FROM employees
WHERE salary = (SELECT MAX(salary)
                FROM employees);

--4-02
/* Выведите все данные (*) о сотрудниках с зарплатой, равной максимальной зарплате внутри своего отдела.
Указание: Решите задачу с использованием коррелированного подзапроса. */
SELECT *
FROM employees A
WHERE salary = (SELECT MAX(salary)
                FROM employees
                WHERE department_id = A.department_id);

--4-03
/* Выведите все данные (*) о сотрудниках с зарплатой, равной максимальной зарплате внутри своего отдела.
Указание: Решите задачу с использованием некоррелированного подзапроса во FROM. */
SELECT DISTINCT A1.*
FROM employees "A1"
INNER JOIN employees "A2" ON A1.department_id = A2.department_id
WHERE A1.salary = (SELECT MAX(A2.salary)
                   FROM employees "A2"
                   WHERE A2.department_id = A1.department_id);

--4-04
/* Выведите все данные (*) о сотрудниках с зарплатой, равной максимальной зарплате внутри своего отдела.
Указание: Решите задачу с использованием некоррелированного подзапроса и оператора IN. */
SELECT *
FROM employees A
WHERE salary IN (SELECT MAX(salary)
                FROM employees
                WHERE department_id = A.department_id
                GROUP BY department_id);

--4-08
/* Выведите все данные (*) о тех сотрудниках, зарплаты которых больше, чем средняя зарплата в каждом из отделов.
Указание: Использование оператора сравнения с оператором ALL. */
SELECT *
FROM employees
WHERE salary > ALL (SELECT AVG(salary)
                    FROM employees
                    GROUP BY department_id);

--4-09
/* Выведите все данные (*) о тех сотрудниках, зарплаты которых больше, чем средняя зарплата в хотя бы одном из отделов.
Указание: Использование операторов сравнения с операторами SOME/ANY. */
SELECT *
FROM employees
WHERE salary > ANY (SELECT AVG(salary)
                    FROM employees
                    GROUP BY department_id);

--4-10
/* Выведите все данные (*) о тех сотрудниках, у которых в трудовой истории (таблица job_history) есть запись о работе в должности ST_CLERK.
Указание: Использование оператора EXISTS. */
SELECT *
FROM employees
WHERE EXISTS (SELECT *
              FROM job_history
              WHERE job_history.employee_id = employees.employee_id AND job_id = 'ST_CLERK' AND ROWNUM < 2);
