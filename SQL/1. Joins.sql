-- 1-20
/* ������� ���������� ��� ���� ������� � ��������� � ��� �����������.
� ������ ������� - id ������, �� ������ - id ����������.
���� � ������ ��� �� ������ ����������, �� ������ ������� � ������ ������ ������ ������ ��������� NULL.
������ ������, ��������� ��������� Oracle */
SELECT departments.department_id, employees.employee_id
FROM departments
LEFT OUTER JOIN employees ON departments.department_id = employees.department_id;

-- 1-21
/* ������� ����������� (employees), � ������� �������� (manager_id) �� ��������� � ���������� ������,
��� �������� ��������� (department_id, ��. ����� ������� manager_id � ������� departments).
������� ����� 3 �������, � ������ �� ������� - ������� (last_name): ����������, ��������� ����������, ��������� ������.
��������: ������������ ����� ���������� �������� �������������� ������� (aliases) */
SELECT A1.last_name as "EMPLOYEE", A2.last_name as "EMP_MANAGER", A3.last_name as "DEP_MANAGER"
FROM employees A1
INNER JOIN departments ON A1.department_id = departments.department_id
INNER JOIN employees A2 ON A1.manager_id = A2.employee_id
INNER JOIN employees A3 ON departments.manager_id = A3.employee_id
WHERE A1.manager_id != A3.employee_id;

-- 1-22
/* ��� ���� �������, ��������������� � ������ 'Seattle', ������� �������� ������ � ����� �������� ��� ���������.
���� ����� �� ����� ���������, ������ ������� ������ ��������� NULL. */
SELECT departments.department_name, employees.phone_number
FROM departments
LEFT JOIN employees ON departments.manager_id = employees.employee_id
INNER JOIN locations ON departments.location_id = locations.location_id
WHERE locations.city = 'Seattle';

-- 1-31
/* ������� �������� ���� �������, ��������������� �� ������ '2004 Charade Rd'. */
SELECT department_name
FROM departments
INNER JOIN locations ON departments.location_id = locations.location_id
WHERE street_address = '2004 Charade Rd';

-- 1-32
/* ������� � ������ ������� �������� ���� ������� ��������, � ������ - �������� �����, ��� ��� �������������. */
SELECT department_name, country_name
FROM departments
INNER JOIN locations ON departments.location_id = locations.location_id
INNER JOIN countries ON locations.country_id = countries.country_id;

-- 1-33
/* ������� ������� ��������� ���������� �� ������� 'Chen'. */
SELECT A2.phone_number
FROM employees A1
LEFT JOIN employees A2 ON A1.manager_id = A2.employee_id
WHERE A1.last_name = 'Chen';

-- 1-34
/* ��� ���� ������� �������� ������� �������� ������ � ������� (last_name) ��� ���������.
���� ����� �� ����� ���������, ������ ������� ������ ��������� NULL. */
SELECT department_name, last_name
FROM departments
LEFT JOIN employees ON departments.manager_id = employees.employee_id;

-- 1-35
/* ������� �������� ��� ������, � ������� �������� ��������� �� ������� 'Sully'. */
SELECT postal_code
FROM employees
INNER JOIN departments ON employees.department_id = departments.department_id
INNER JOIN locations ON departments.location_id = locations.location_id
WHERE last_name = 'Sully'