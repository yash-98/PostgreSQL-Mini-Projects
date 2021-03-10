WITH    quest_char (login, name, race, day, realm, theme) AS
    (SELECT a.login, a.name, a.race, q.day, q.realm, q.theme
    FROM    avatar as a, visit as v, actor as ac, quest as q
    WHERE   a.login = v.login
    AND     v.login = ac.login
    AND     a.name = v.name
    AND     v.day = ac.day
    AND     ac.day = q.day
    AND     v.realm = ac.realm
    AND     ac.realm = q.realm
    AND     ac.theme = q.theme
    AND     q.succeeded IS NOT NULL
    GROUP BY a.login, a.name, a.race, q.day, q.realm, q.theme
    ORDER BY login, name),

    quest_max(login, name, race, total) as
    (SELECT login, name, race, COUNT(*)
    FROM    quest_char
    GROUP BY login, name, race),

    loot_max(realm, day, theme, value) AS
    (SELECT l.realm, l.day, l.theme, MAX(t.sql)
    FROM    treasure as t, loot as l
    WHERE   l.treasure = t.treasure
    GROUP BY l.realm, l.day, l.theme
    ORDER BY l.realm, l.day, l.theme),

    loot_total(login, name, total) AS
    (SELECT qc.login, qc.name, sum(lm.value)
    FROM loot_max as lm, quest_char as qc
    WHERE   lm.day = qc.day
    AND     lm.realm = qc.realm
    AND     lm.theme = qc.theme
    GROUP BY login, name)

SELECT a.login, a.name, a.race, 
COALESCE((
    SELECT lt.total
    FROM loot_total as lt
    WHERE lt.login = a.login
    AND lt.name = a.name
), 0) as earned, 
COALESCE((
    SELECT qm.total
    FROM quest_max as qm
    WHERE qm.login = a.login
    AND qm.name = a.name
), 0) as quests
FROM avatar as a
ORDER BY a.login, a.name