trancate cascade TABLE approver;
trancate cascade TABLE item;
INSERT INTO approver(id, email, name) values (1, 'EMAIL', 'Nick2');
INSERT INTO approver(id, email, name) values (2, 'EMAIL', 'Nick2');
INSERT INTO approver(id, email, name) values (3, 'EMAIL', 'Nick2');
INSERT INTO approver(id, email, name) values (4, 'EMAIL', 'Nic2k');
INSERT INTO item(itemId, dtype, addeddate, rating, status, text, approver_id) values (1, 'Quote', '2015-01-14', '1', '0', 'text1', '1');
INSERT INTO item(itemId, dtype, addeddate, rating, status, text, approver_id) values (2, 'Quote', '2015-01-14', '1', '1', 'text2', '2');
INSERT INTO item(itemId, dtype, addeddate, rating, status, text, approver_id) values (3, 'Quote', '2015-01-14', '1', '2', 'text3', '3');
