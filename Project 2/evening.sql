SELECT theme, day, realm, succeeded
FROM quest
WHERE   succeeded >= '20:00:00'
OR      succeeded IS NULL
ORDER BY theme, day, realm;