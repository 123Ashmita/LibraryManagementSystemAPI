# Library Management System

This is a **Library Management System** built using **Java**, **Spring Boot**, and **JPA**. It allows patrons to borrow books from a library. The system automatically manages the database and the relationship between the entities: `Book`, `Patron`, and `Borrow`.

##Features of LMS  <br>
- **1.Add Book <br>
- **2.Add Patron  <br>
- **3.delete book  <br>
- **4.delete Patron  <br>
- **5.borrow Book  <br>
- **6.Return Book  <br>
- **7.ListBorrowedBookByPatronID  <br>
- **8.Book List  <br>
- **9.Patron List <br>

## Entities

### 1. **Book**
The `Book` entity represents a book in the library. Each book has the following attributes:
- **bookId** (Primary Key): Unique identifier for each book.
- **title**: Title of the book.
- **author**: Author of the book.
- **quantity**: Number of copies of the book available in the library.
- **available**: Indicates whether the book is available for borrowing.

#### Book Lifecycle:
- The book can be borrowed by a patron, which reduces the available quantity.
- Once the quantity reaches `0`, the book is no longer available for borrowing .

### 2. **Patron**
The `Patron` entity represents a library member who can borrow books. Each patron has:
- **pid** (Primary Key): Unique identifier for the patron.
- **name**: Name of the patron.

A patron can borrow multiple books at once.

### 3. **Borrow**
The `Borrow` entity is the join table between the `Book` and `Patron` entities. It represents a borrow record where a patron borrows a book. Each borrow record includes:
- **id** (Primary Key): Unique identifier for the borrow record.
- **book** (Foreign Key): The book that is borrowed.
- **patron** (Foreign Key): The patron who borrowed the book.

---

## Database Configuration

The system uses **JPA (Java Persistence API)** to automatically create the database schema. Once the application starts, the required tables (`book`, `patron`, and `borrow`) will be created automatically in your configured database.

### Database Setup:

1. **Database Name**: `library` (you can change the database name as per your requirements).
* Steps for creating the database :
Create the library database:
@CREATE DATABASE library;
Switch to the library database:
@USE library;

2. **Tables**: The system will automatically create three tables based on the entities: 
    - **book** (for `Book` entity)
    - **patron** (for `Patron` entity)
    - **borrow** (for `Borrow` entity, as a join table)
      
#Add a New Book Example(using postman) <br>
method type-post <br>
method url-localhost:8081/addBook  <br>
body - {   <br>
        "bookId": 1,   <br>
        "title": "Java",   <br>
        "author":"James Gosling",   <br>
        "quantity":5   <br>
    }




