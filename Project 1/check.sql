select name, address
from Person
intersect
select name, address
from Person;

select name, address
from Person