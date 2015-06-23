configuration
-------------
Skip if you using pre-configured zip archive
- go to project root
- build: mvn clean install
- copy target/*jar to lib folder in hsqldb folder

sql queries to create db
------------------------
CREATE MEMORY TABLE PUBLIC.PERSONS(PERSONID INTEGER,LASTNAME VARCHAR(255),FIRSTNAME VARCHAR(255),ADDRESS VARCHAR(255),CITY VARCHAR(255))

drop trigger persons_insert_trigger;
drop trigger persons_update_trigger;
drop trigger persons_update_delete;

CREATE TRIGGER persons_insert_trigger AFTER INSERT ON persons
	FOR EACH ROW
	CALL "eu.appbucket.monitor.trigger.PersonsTableTrigger";

CREATE TRIGGER persons_update_trigger BEFORE UPDATE ON persons
	FOR EACH ROW
	CALL "eu.appbucket.monitor.trigger.PersonsTableTrigger";

CREATE TRIGGER persons_update_delete BEFORE DELETE ON persons
	FOR EACH ROW
	CALL "eu.appbucket.monitor.trigger.PersonsTableTrigger";

sql queries to interaction with db:
-----------------------------------
- to insert new rows execute following queries in db console:
INSERT INTO PERSONS VALUES(1,'l1','f1','a1','c1');
INSERT INTO PERSONS VALUES(2,'l2','f2','a2','c2');
INSERT INTO PERSONS VALUES(3,'l3','f3','a3','c3');
INSERT INTO PERSONS VALUES(4,'l4','f4','a4','c4');
INSERT INTO PERSONS VALUES(5,'l5','f5','a5','c5');
- to update existing row execute following queries in db console:
UPDATE PERSONS set LASTNAME = 'l5-changed' where PERSONID = 5;
- to delete existing row execute following queries in db console:
delete from PERSONS where PERSONID = 5;

