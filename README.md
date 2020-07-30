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

## Administrator can do:
- CRUD operations with posts, employees, projects, users
- Bind a user with an employee
- Attach & detach employees to a project
- Attach & detach projects to an employee
- Upload main images of posts
- Upload & download photos of employees
- Comment & like posts

## User can do:
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
![Screenshot_20200730_212739](https://user-images.githubusercontent.com/26651009/88971975-8594e100-d2bd-11ea-8dd2-3b057dadd3a3.png)

![Screenshot_20200730_212914](https://user-images.githubusercontent.com/26651009/88971991-8b8ac200-d2bd-11ea-8063-1afef26191ba.png)

![Screenshot_20200730_213135](https://user-images.githubusercontent.com/26651009/88971995-8ded1c00-d2bd-11ea-8adf-91cbc7abe8b9.png)

![Screenshot_20200730_213417](https://user-images.githubusercontent.com/26651009/88971999-8f1e4900-d2bd-11ea-83d8-36c385de5f33.png)

![Screenshot_20200730_213901](https://user-images.githubusercontent.com/26651009/88972006-90e80c80-d2bd-11ea-9908-6a54ad0e7467.png)

![Screenshot_20200730_214751](https://user-images.githubusercontent.com/26651009/88972009-947b9380-d2bd-11ea-801f-200bb7375b0f.png)

![Screenshot_20200730_215155](https://user-images.githubusercontent.com/26651009/88972020-96dded80-d2bd-11ea-9b09-ac93dedb96dd.png)

![Screenshot_20200730_215801](https://user-images.githubusercontent.com/26651009/88972022-980f1a80-d2bd-11ea-859e-9576038c7135.png)

![Screenshot_20200730_220627](https://user-images.githubusercontent.com/26651009/88972029-9cd3ce80-d2bd-11ea-9bcb-875355bceb3e.png)

![Screenshot_20200730_220446](https://user-images.githubusercontent.com/26651009/88972032-9e9d9200-d2bd-11ea-9fce-61c8a6709a69.png)

![Screenshot_20200730_220857](https://user-images.githubusercontent.com/26651009/88972037-a1988280-d2bd-11ea-9434-b57e20d80224.png)

![Screenshot_20200730_222015](https://user-images.githubusercontent.com/26651009/88972041-a2c9af80-d2bd-11ea-8794-2d227dedf46d.png)

![Screenshot_20200730_222351](https://user-images.githubusercontent.com/26651009/88972045-a4937300-d2bd-11ea-981a-40092b2a1169.png)

![Screenshot_20200730_222710](https://user-images.githubusercontent.com/26651009/88972048-a65d3680-d2bd-11ea-991d-6545b149448e.png)

![Screenshot_20200730_222914](https://user-images.githubusercontent.com/26651009/88972054-a78e6380-d2bd-11ea-866e-a1c2ca44fdce.png)

![Screenshot_20200730_234845](https://user-images.githubusercontent.com/26651009/88973065-351e8300-d2bf-11ea-8829-3ca3b3ed2a66.png)






