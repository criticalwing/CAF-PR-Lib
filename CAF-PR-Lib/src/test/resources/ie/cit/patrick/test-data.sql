INSERT INTO book (id,title,author,publisher,publication_date,isbn,available) 
VALUES ('3421','Raspberry Pi User Guide', 'Eben Upton', 'John Wiley & Sons', '2012-09-14', '111846446X', false);
INSERT INTO book (id,title,author,publisher,publication_date,isbn,available) 
VALUES ('1236','HTML & CSS: Design and Build Web Sites', 'Jon Duckett', 'John Wiley & Sons', '2011-11-18', '123456789B',false);
INSERT INTO book (id,title,author,publisher,publication_date,isbn, available) 
VALUES ('627','Python Programming for the Absolute Beginner', 'Mike Dawson', 'Course Technology PTR', '2010-01-30', '1435445002', false);
INSERT INTO book (id,title,author,publisher,publication_date,isbn, available) 
VALUES ('835','Digital Marketing Strategy Implementation and Practice', 'Dave Chaffey', 'Pearson', '2012-07-12', '0273746103', false);
INSERT INTO book (id,title,author,publisher,publication_date,isbn, available) 
VALUES ('4454','Dancing at Lughnasa', 'A N Other', 'Faber and Faber', '2012-07-12', '0571144799', false);
INSERT INTO book (id,title,author,publisher,publication_date,isbn, available) 
VALUES ('746','Best Story ever', 'A N Other', 'publisher.com', '2012-07-12', '0571144899', true);

INSERT INTO Member (id, name,address1,address2,town,contact_number,balance, active) 
VALUES ('1234','Bertie Ahern', 'Big House', 'Rich Street', 'Dublin', '018001231235','1500.00', true);
INSERT INTO Member (id, name,address1,address2,town,contact_number,balance, active) 
VALUES ('4567','Chris Stephenson', 'New House','Stephen Street', 'Boyle', '0331234567', '0.00', false);
INSERT INTO Member (name,address1,address2,town,contact_number,balance) 
VALUES ('Peter Smith', '18', 'St Johns Close', 'Macroom', '02212344567', '10.00');
INSERT INTO Member (name,address1,address2,town,contact_number,balance) 
VALUES ('Vera Saunders', '24A', 'Elysian', 'Cork', '0211234667', '20.00');
INSERT INTO Member (id,name,address1,address2,town,contact_number,balance) 
VALUES ('232','Stuart Little', '100 Boardwalk', 'Cork', 'Cork', '0211234667', '20.00');
INSERT INTO Member (id, name,address1,address2,town,contact_number,balance) 
VALUES ('9332','Steven Saunders', '24A', 'Elysian', 'Cork', '0211234667', '10.00');
INSERT INTO Member (id, name,address1,address2,town,contact_number,balance) 
VALUES ('543','Logan Robertson', 'Hjaltland', 'Pullerick', 'Cork', '0211234667', '10.00');
INSERT INTO Member (id, name,address1,address2,town,contact_number,balance) 
VALUES ('3425','Janet Leigh', 'Hjaltland', 'Pullerick', 'Cork', '0211234667', '12.00');

INSERT INTO Member_loans_Book (Member_id, Book_id, loan_date, return_date, fine) 
VALUES ('3425','3421', '2012-10-25', '2012-11-08', '0.00');
INSERT INTO Member_loans_Book (Member_id, Book_id, loan_date, return_date) 
VALUES ('543','4454', '2012-10-14', '2012-10-28');
INSERT INTO Member_loans_Book (Member_id, Book_id, loan_date, return_date) 
VALUES ('9332','1236', '2012-06-25',  '2012-07-08');
INSERT INTO Member_loans_Book (Member_id, Book_id, loan_date) 
VALUES ('9332','835', '2012-08-10');
INSERT INTO Member_loans_Book (Member_id, Book_id, loan_date) 
VALUES ('9332','1236', '2012-08-06');
INSERT INTO Member_loans_Book (Member_id, Book_id, loan_date) 
VALUES ('1234','627', '2012-10-10');


