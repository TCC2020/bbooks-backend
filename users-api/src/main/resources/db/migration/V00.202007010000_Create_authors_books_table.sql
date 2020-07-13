CREATE TABLE authors_books (
    BOOK_ID foreign key (BOOKS) references BOOKS(ID),
    AUTHOR_ID foreign key (AUTHORS) references AUTHORS(ID)
)