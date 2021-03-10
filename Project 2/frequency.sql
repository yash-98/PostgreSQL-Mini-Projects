WITH    quested (login, realm, visits) as 
        (SELECT login, realm, count(*)
        FROM visit
        GROUP BY login, realm
        HAVING count(*) > 1
        ORDER BY login, realm),

        questdays (login, realm, frequency) as 
        (SELECT login, realm, (MAX(day) - MIN(day))::NUMERIC
        FROM actor
        GROUP BY login, realm
        ORDER BY login, realm)

SELECT q.login, q.realm, q.visits, (qd.frequency/(q.visits - 1))::NUMERIC(5,2) as frequency
FROM    quested as q, questdays as qd
WHERE   q.login = qd.login
AND     q.realm = qd.realm
ORDER BY q.login, q.realm