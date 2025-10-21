Library Management System (Java + MySQL, JDBC)
A console-based Java application to manage books in a library.  
Perform add, view, issue, return, and delete operations using MySQL and JDBC.

Quickstart

1. Create database and table in MySQL
2. Add MySQL Connector JAR to project classpath (lib folder → Referenced Libraries in VS Code).
3. Run the project:
   javac -cp ".;lib/mysql-connector-j-9.0.0.jar" src/*.java
   java -cp ".;lib/mysql-connector-j-9.0.0.jar;src" LibraryManagement

Make sure MySQL is running before starting the program.

Features
Add, view, issue, return, and delete books
Each book has a unique ID for easy management

Tech Stack
Java 17
MySQL 8,JDBC

Note
DO NOT commit DBConnection as it contains credentials.

