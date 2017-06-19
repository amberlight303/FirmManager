# Firm Manager 
# Description:
It's a web app for a study. The subject of the web application is a closed software development monitoring system, which can be useful for 
tracking information about employees, projects, assignments employees to the projects, posts with comments and likes. 
With conditional rendering and security constraints administrator's and user's abilities are divided. 
Likes functional is implemented with ajax. Comments appear in real-time mode by using WebSocket. 
Comment object is passing from the server in JSON format, javascript in browser of the client accepts it, 
parses and displays. The uploaded photos are storing in files system, names of these files are making 
with the custom random string generator. Employees and projects have fields (age, experience, days left), 
which are changing in the course of the time. So for always fresh data, once per day with the first request for objects 
containing these fields, all respective sensitive fields are updating in the database. With all subsequent same requests 
the server checks whether day has passed. On small displays tables are condensing using footable js plugin. 
Part of an information is hiding in a drop sub-tables. The fields which need a lot of text input i 
made as dynamically extended with js.

Administrator can:
- CRUD posts, employees, projects, users
- Bind a user with an employee
- Attach/Detach employees to a project
- Attach/Detach projects to an employee
- Upload main images of posts
- Upload & Download photos of employees
- Comment & like posts

User can:
- Retrieve and inspect posts, employees, projects, assingments between them, users
- Download photos of employees
- Comment & like posts

# I used next technologies during development:
- Java 8
- Spring MVC (4.2.0.RELEASE)
- Spring Security (4.2.0.RELEASE)
- Spring Data JPA (1.8.2.RELEASE)
- Spring WebSocket (4.2.0.RELEASE)
- Hibernate as a JPA provider (4.3.11.Final)
- MySQL (5.7.17)
- Tomcat (8.0.32)
- Maven (3.3.9)
- Logback (1.1.3)
- Jackson (2.8.6)
- JUnit (4.11)
- Spring Test (4.2.0.RELEASE)
- Mockito (1.10.19)
- Hamcrest (1.3)
- jQuery (3.2.0)
- Bootstrap
- etc.
   
# Next common tasks facing java web app developer were solved:
- CRUD operations
- Authentication and authorization
- AJAX
- Asynchronous requests processing
- Modern adaptive front-end for all devices with navigation and pagination
- Validation
- Search
- Upload & Download functions
- Logging
- Meta data & JavaDoc
- Testing
- etc.

# Installation:

Create a new schema in MySQL RDBMS. Execute MySQL DB initialization SQL code stored in `/src/main/resources/database.sql`.

Specify JDBC parameters and root path for storing uploaded files used in the files system in 
`/src/main/resources/firmmanager.properties`.

The MySQL DB initialization code has inserts with users: admin, user, user1, user2, user3. Passwords are the same as logins.


# Screenshots:
![screen](https://pp.userapi.com/c639717/v639717440/136ac/n2C4ZzBXmec.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/135d7/4V2boJfr4lg.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/135c3/ZEkJ0UjB0_M.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/13609/hq_dFvi9NuQ.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/13613/T0wTKA-vH2Y.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/13627/XH0Mp_EUB3M.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/1364d/RP1S5hqSfA0.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/136b3/PLfRpOrL9yU.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/136cf/TPPywfdSfUc.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/136c1/QBmtdbKPxNg.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/13931/NxoJv-b4yoA.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/138f4/cIMiWaG7ivk.jpg)
![screen](https://pp.userapi.com/c639717/v639717440/138fb/Ts5naZgozx4.jpg)







