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
SELECT * 
FROM staff
