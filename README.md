### Deadquotes Android App README

This android application can get you some funny quotes when you're offline real fast.  Search for "free" or "drunk" and you'll get something fast from somebody famous. 

In the assets folder is an sqlite databases whose schema was created like this:
```{sql}
CREATE TABLE deadquotes (quote text default null);
CREATE INDEX idxquote ON deadquotes(quote);
```
Sqlite3 shows us there are 309 quotes:
```{bash}
sqlite> select count(*) from deadquotes;
309
```
