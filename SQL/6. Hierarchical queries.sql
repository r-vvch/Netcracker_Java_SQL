--MS-6.1
/* Выведите иерархию подчинений воинских подразделений сверху вниз, начиная с полка 'Regiment #1271A',
и численность личного состава, приписанного непосредственно к подразделению.
Все подчиненные подразделения должны располагаться "лесенкой" с отступом, равным 3-м пробелам.
О каждом подразделении выводить: название, численность.
   Regiment #1271A  1
     First Company  1
        Platoon #1  0
        Platoon #2  1
        ...         ...  */
SELECT DISTINCT lpad(' ', 3*(level-1)) || MU.name, COUNT(S.person_id) over(partition by S.unit_id)
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id(+)
START WITH MU.name = 'Regiment #1271A'
CONNECT BY MU.parent_id = PRIOR MU.unit_id;


--MS-6.2
/*Выведите иерархию подчинений воинских подразделений сверху вниз, начиная с полка 'Regiment #1271A',
и численность личного состава, приписанного непосредственно к подразделению.
Все подчиненные подразделения должны располагаться "лесенкой" с отступом, равным 3-м пробелам.
О каждом подразделении выводить: название, численность.
Regiment #1271A	1
   First Company	1
      Platoon #1	0
      Platoon #2	1
             ...	...
Дополнительное условие - НЕ выводить те подразделения (а также подчиненные им подразделения), которые не имеют в составе ни одного военнослужащего */
SELECT DISTINCT lpad(' ', 3*(level-1)) || MU.name, COUNT(S.person_id) over(partition by S.unit_id)
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name = 'Regiment #1271A'
CONNECT BY MU.parent_id = PRIOR MU.unit_id;


--MS-6.3
/* Назовем средним сроком службы по подразделению (таблица military_units) среднее число дней службы на текущий момент
всех военнослужащих (таблица staff), приписанных к этому подразделению и ко всем его дочерним подразделениям (до нижнего уровня).
Для каждого из взводов (military_units.name начинается с "Platoon"), к которым приписаны военнослужащие,
вывести имя взвода и средний срок службы по взводу, усеченный до дней (т.е. округленный в меньшую сторону). */
--!! ЭТИ ТЕСТЫ НЕ РАБОТАЮТ !!
SELECT DISTINCT lpad(' ', 3*(level-1)) || MU.name, COUNT(SYSDATE - S.consc_date) over(partition by MU.parent_id)
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name = 'Regiment #1271A'
CONNECT BY MU.parent_id = PRIOR MU.unit_id;

SELECT lpad(' ', 3*(level-1)) || MU.name, MU.unit_id, MU.parent_id,
       SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10) plt,
       S.person_id, S.name, FLOOR(SYSDATE - S.consc_date),
       COUNT(*) over(partition by SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10))
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name LIKE 'Platoon%'
CONNECT BY MU.parent_id = PRIOR MU.unit_id;

SELECT lpad(' ', 3*(level-1)) || MU.name, MU.unit_id, MU.parent_id,
       SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10) plt,
       S.person_id, S.name, FLOOR(SYSDATE - S.consc_date),
       FLOOR(AVG(FLOOR(SYSDATE - S.consc_date)) over(partition by SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10))) DAYS
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name LIKE 'Platoon%'
CONNECT BY MU.parent_id = PRIOR MU.unit_id;

SELECT name, days
FROM(
SELECT MU.name, MU.unit_id, MU.parent_id,
       SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10) plt,
       S.person_id, S.name Staff_Name, FLOOR(SYSDATE - S.consc_date),
       FLOOR(AVG(FLOOR(SYSDATE - S.consc_date)) over(partition by SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10))) DAYS
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name LIKE 'Platoon%'
CONNECT BY MU.parent_id = PRIOR MU.unit_id
)
WHERE name LIKE 'Platoon%';

SELECT SYS_CONNECT_BY_PATH(name, '/')
FROM military_units
START WITH name = 'Regiment #1271A'
CONNECT BY parent_id = PRIOR unit_id;

--MS-6.4
/* Назовем средним сроком службы по подразделению среднее число дней службы на текущий момент всех военнослужащих,
приписанных к этому подразделению и ко всем его дочерним подразделениям (до нижнего уровня).
Вывести название самого "старшего" подразделения, а также средний срок службы по подразделению, округленный до дней.
В случае, если таких подразделений более одного, ограничить вывод первым.
Примечание. Эту задачу можно решить по аналогии с задачей MS-6.3, но типичная ошибка усреднения в MS-6.3 не влияет на результат, а в данной задаче - влияет. */

--MS-6.5
/* Для каждого военнослужащего званием ниже лейтенанта вывести начальника роты (подразделения с названием 'Company'),
к которой приписан данный военнослужащий. В обоих столбцах выводить атрибут name.
Примечание: отношение званий (выше/ниже) хранится в атрибуте priority таблицы ranks.
Для проверки можно использовать тот факт, что начальник роты является майором. */
SELECT REGEXP_SUBSTR(SYS_CONNECT_BY_PATH(staff.name, ' '), '(\S*)', 2), staff.name
FROM staff, ranks
WHERE staff.rank_id = ranks.rank_id
AND ranks.name = 'Major'
START WITH staff.rank_id > 50
CONNECT BY staff.person_id = PRIOR staff.chief;

--MS-6.6
/* Вывести название самого малочисленного взвода (взвод - это подразделение, название которого начинается с 'Platoon') и его численность.
При подсчетах численности следует учитывать состав подразделений (отделений), подчиненных данному взводу.
Если во взводе нет ни одного военнослужащего, он выводиться не должен. */
--!! ЭТИ ТЕСТЫ НЕ РАБОТАЮТ !!
SELECT MIN(BOIIS)
FROM(
SELECT MU.name, MU.unit_id, MU.parent_id,
       SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10) plt,
       S.person_id, S.name Staff_Name, FLOOR(SYSDATE - S.consc_date),
       COUNT(S.person_id) over(partition by SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10)) BOIIS
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name LIKE 'Platoon%'
CONNECT BY MU.parent_id = PRIOR MU.unit_id
)
WHERE name LIKE 'Platoon%';

SELECT name, BOIIS - 1
FROM(
SELECT MU.name, MU.unit_id, MU.parent_id,
       SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10) plt,
       S.person_id, S.name Staff_Name, FLOOR(SYSDATE - S.consc_date),
       COUNT(S.person_id) over(partition by SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10)) BOIIS
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name LIKE 'Platoon%'
CONNECT BY MU.parent_id = PRIOR MU.unit_id
)
WHERE name LIKE 'Platoon%'
AND BOIIS = (SELECT MIN(BOIIS)
FROM(
SELECT MU.name, MU.unit_id, MU.parent_id,
       SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10) plt,
       S.person_id, S.name Staff_Name, FLOOR(SYSDATE - S.consc_date),
       COUNT(S.person_id) over(partition by SUBSTR(SYS_CONNECT_BY_PATH(MU.name, '/'), 2, 10)) BOIIS
FROM military_units MU, staff S
WHERE MU.unit_id = S.unit_id
START WITH MU.name LIKE 'Platoon%'
CONNECT BY MU.parent_id = PRIOR MU.unit_id
)
WHERE name LIKE 'Platoon%');

--MS-6.7
/* Для военнослужащего с именем "Vasiliev" вывести всех его подчиненных (прямых и непрямых), у которых, в свою очередь, нет собственных подчиненных.
Подчинение определяется колонкой chief в таблице staff.
Вывод: имя военнослужащего, его звание, название подразделения и ID военнослужащего. */
SELECT staff.name, ranks.name rank, military_units.name unit, person_id
FROM staff, military_units, ranks
WHERE staff.unit_id = military_units.unit_id AND staff.rank_id = ranks.rank_id
AND CONNECT_BY_ISLEAF = 1
START WITH staff.name = 'Vasiliev'
CONNECT BY PRIOR staff.person_id = staff.chief;

--MS-6.8
/* Перечислить в одной строчке через запятую (без пробелов) весь личный состав первого отделения
(т.е. подразделения с именем "Squad #1"), упорядочив там имена военнослужащих (name) по алфавиту.
Учитывать только военнослужащих, приписанных непосредственно к отделению. */
SELECT LISTAGG(S.name, ',') WITHIN GROUP (ORDER BY S.name) "Personnel"
FROM staff S, military_units MU
WHERE S.unit_id = MU.unit_id
AND MU.name = 'Squad #1';

--MS-6.8*
/* Для каждого отделения (отделение - это подразделение, название которого начинается со слова "Squad")
перечислить через запятую в одной строчке весь личный состав, упорядочив военнослужащих по алфавиту.
Учитывать только военнослужащих, приписанных непосредственно к отделению.
Вывод: первой колонкой - ID подразделения, второй - список имен военнослужащих (name) через запятую (без пробелов) */
SELECT DISTINCT S.unit_id, LISTAGG(S.name, ',') WITHIN GROUP (ORDER BY S.name) OVER(PARTITION BY S.unit_id) "Personnel"
FROM staff S, military_units MU
WHERE S.unit_id = MU.unit_id
AND MU.name LIKE 'Squad%';

--MS-6.9
/* Вывести, какие уникальные иерархии подчинения (от самого старшего командира до младшего подчиненного) присутствуют в таблице staff.
Под элементом иерархии понимаются не имя военнослужащего, а его воинское звание (таблица ranks).
Элементы разделяются символом ">", а упорядочиваются от старшего к младшему. Например:
Colonel>Major>Leutenant>Sergeant */
--!! ЭТИ ТЕСТЫ НЕ РАБОТАЮТ !!
SELECT "Hierarchy"
FROM
(
SELECT DISTINCT SUBSTR(SYS_CONNECT_BY_PATH(ranks.name, '>'), 2) "Hierarchy",
                REGEXP_COUNT(SUBSTR(SYS_CONNECT_BY_PATH(ranks.name, '>'), 2), '>') "Number"
FROM staff, ranks
WHERE staff.rank_id = ranks.rank_id
START WITH staff.rank_id = 30
CONNECT BY PRIOR staff.person_id = staff.chief
)
WHERE "Number" > 0;

--Иерархия подразделений
SELECT lpad(' ', 3*(level-1)) || name, unit_id, parent_id, level
FROM military_units
START WITH name = 'Regiment #1271A'
CONNECT BY parent_id = PRIOR unit_id;

--Иерархия служащих
SELECT lpad(' ', 3*(level-1)) || staff.name, ranks.name
FROM staff, ranks
WHERE staff.rank_id = ranks.rank_id
START WITH staff.rank_id = 30
CONNECT BY PRIOR staff.person_id = staff.chief;

SELECT *
FROM staff, military_units
WHERE staff.unit_id = military_units.unit_id(+);
SELECT * from staff;
SELECT * from military_units;
SELECT * FROM ranks;