[![Build Status](https://api.travis-ci.org/amberlight303/FirmManager.svg?branch=master)](https://travis-ci.org/github/amberlight303/FirmManager)
# Firm Manager 
## Description:
I made this demo web app originally in 2016 while I was studying at the university, made minor subsequent changes after.
The subject of this monolithic demo web application is a closed software development tracking system 
that can be useful for storing information about employees, projects, employees assignments to projects. 
There are features such as posts, comments, pagination, likes.

With a conditional rendering and security constraints administrator's and user's abilities are divided. 
Likes feature is implemented with ajax async requests. Comments appear in real-time using WebSocket. Uploaded photos are stored in the files system, unique names of these files made with a random string generator. 
Tables on small screens are condensing using footable js plugin. Part of information is hiding in a drop sub-tables. 
Fields that hold a lot of input text are made dynamically extended using js. Comments have XSS protection provided by OWASP lib. Core technologies are: Java, Spring, JSP, PostgreSQL, Spring Data JPA, Tomcat, a few JS plugins for UI, stateful auth.

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

## Used next technologies during development:
- Java (8)
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
- Bootstrap (3.3.7)
   
## Next common tasks facing a java web app developer solved:
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

## Installation:

Create a new schema in PostgreSQL RDBMS. Execute initialization SQL code stored in: 
`/src/main/resources/database.sql`

Specify JDBC parameters and root path for storing uploaded files used in the files system in: 
`/src/main/resources/firmmanager.properties`

PostgreSQL DB initialization code has inserts with users: admin, user, user1, user2, user3. 
Passwords are the same as usernames.

## Screenshots:

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88971975-8594e100-d2bd-11ea-8dd2-3b057dadd3a3.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88971991-8b8ac200-d2bd-11ea-8063-1afef26191ba.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/89167421-156bb100-d584-11ea-9392-2c2f5c9c534d.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/89167429-17357480-d584-11ea-9290-ef7479e163c9.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/89167434-18ff3800-d584-11ea-8963-8d14b901343e.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/89167442-1ac8fb80-d584-11ea-8fc8-e556d85a0602.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/89167453-1bfa2880-d584-11ea-8dc4-bba351a6c75d.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/89167464-1dc3ec00-d584-11ea-9cb1-cfab3f2786e4.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/89167469-1ef51900-d584-11ea-993d-97625471afac.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88972029-9cd3ce80-d2bd-11ea-9bcb-875355bceb3e.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88972032-9e9d9200-d2bd-11ea-9fce-61c8a6709a69.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88972037-a1988280-d2bd-11ea-9434-b57e20d80224.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88972041-a2c9af80-d2bd-11ea-8794-2d227dedf46d.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88972045-a4937300-d2bd-11ea-981a-40092b2a1169.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88972048-a65d3680-d2bd-11ea-991d-6545b149448e.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88972054-a78e6380-d2bd-11ea-866e-a1c2ca44fdce.png" />
</p>

<p align="center">
  <img src="https://user-images.githubusercontent.com/26651009/88973065-351e8300-d2bf-11ea-8829-3ca3b3ed2a66.png" />
</p>



