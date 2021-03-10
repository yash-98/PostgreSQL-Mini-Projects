SELECT login, name, gender, address, joined
FROM player
WHERE lower(name) ~ CONCAT('.*', lower(login), '.*')
ORDER BY login ASC;