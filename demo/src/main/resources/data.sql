CREATE TABLE IF NOT EXISTS tasklist(
id varchar(8) primary key,
name varchar(256),
task varchar(256),
deadline varchar(10),
done boolean
);
