CREATE TABLE IF NOT EXISTS book (
  id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
  title VARCHAR(100) NOT NULL ,
  author VARCHAR(100) NOT NULL ,
  publisher VARCHAR(100) NOT NULL,
  publication_date DATE ,
  isbn VARCHAR(20) NULL,
  available boolean DEFAULT true) ;

CREATE TABLE IF NOT EXISTS Member (
    id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address1 VARCHAR(100) NOT NULL,
    address2 VARCHAR(100) NULL,
    town VARCHAR(50) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    book_allowance integer DEFAULT 3,
    balance DOUBLE DEFAULT 0.00 ,
    active boolean DEFAULT true);

CREATE TABLE IF NOT EXISTS Member_loans_Book (
    Member_id integer NOT NULL,
    Book_id integer NOT NULL,
    loan_date DATE NOT NULL,
    return_date DATE NULL,
    fine DOUBLE DEFAULT 0.00,
    PRIMARY KEY (Member_id, Book_id, loan_date),
        
    CONSTRAINT fk_Member_has_Book_Member
    FOREIGN KEY (Member_id)
    REFERENCES Member (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
    
    CONSTRAINT fk_Member_has_Book_Book1
    FOREIGN KEY (Book_id)
    REFERENCES Book (id)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);