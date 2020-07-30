# Firm Manager 
## Description:
I created this demo web app originally in 2016 while I was studying at the university, made minor subsequent changes after.
The subject of this demo web application is a closed software development tracking system 
that can be useful for tracking information about employees, projects, employees assignments to the projects. 
There is features such as posts, comments, pagination, likes.

With a conditional rendering and security constraints administrator's and user's abilities are divided. 
Likes feature is implemented with ajax async requests. Comments appear in real-time using WebSocket.

Uploaded photos are stored in the files system, unique names of these files are making with the random string generator. 

On small displays tables are condensing using footable js plugin. Part of an information is hiding in a drop sub-tables. 
Fields that need a lot of input text are made dynamically extended using js.

Administrator can do:
- CRUD operations with posts, employees, projects, users
- Bind a user with an employee
- Attach & detach employees to a project
- Attach & detach projects to an employee
- Upload main images of posts
- Upload & download photos of employees
- Comment & like posts

User can do:
- Retrieve & inspect posts, employees, projects, assignments, users
- Download photos of employees
- Comment & like posts

## I used next technologies during development:
- Java 8
- Spring MVC (4.2.0.RELEASE)
- Spring Security (4.2.0.RELEASE)
- Spring Data JPA (1.8.2.RELEASE)
- Spring WebSocket (4.2.0.RELEASE)
- Hibernate as a JPA provider (4.3.11.Final)
- PostgreSQL (12.2)
- Tomcat (8.5.49)
- Maven (3.3.9)
- Logback (1.1.3)
- Jackson (2.11.1)
- JUnit (4.11)
- Spring Test (4.2.0.RELEASE)
- Mockito (1.10.19)
- Hamcrest (1.3)
- jQuery (3.2.0)
- OWASP encoder (1.2.2)
- Bootstrap
- etc.
   
## Next common tasks facing java web app developer solved:
- CRUD operations
- Authentication and authorization
- Asynchronous requests processing
- Adaptive front-end for all devices with navigation and pagination
- Validation
- Search
- Upload & Download
- Logging
- Meta data & JavaDoc
- Testing
- etc.

## Installation:

Create a new schema in PostgreSQL RDBMS. Execute initialization SQL code stored in: 
`/src/main/resources/database.sql`.

Specify JDBC parameters and root path for storing uploaded files used in the files system in: 
`/src/main/resources/firmmanager.properties`.

The PostgreSQL DB initialization code has inserts with users: admin, user, user1, user2, user3. 
Passwords are the same as usernames.


## Screenshots:
## Screenshots:
![screen](https://www.dropbox.com/s/zlhjauiysjc4q3e/Screenshot_20200730_212739.png?dl=0)
![screen](https://imgur.com/FEWnvyn)
![screen](https://imgur.com/rLptLUd)
![screen](https://imgur.com/kg9Wxfn)
![screen](https://imgur.com/aWmU1lU)
![screen](https://imgur.com/26oRiks)
![screen](https://imgur.com/NQp1hgi)
![screen](https://imgur.com/q8PQh9w)







