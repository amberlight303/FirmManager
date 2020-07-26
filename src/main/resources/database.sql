CREATE TABLE genders  (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL
);


CREATE TABLE working_positions (
                                   id SERIAL PRIMARY KEY,
                                   name VARCHAR(255) NOT NULL
);


CREATE TABLE employees (
                           id SERIAL PRIMARY KEY,
                           first_name VARCHAR(255),
                           last_name VARCHAR(255),
                           working_position_id INT NOT NULL,
                           gender_id INT NOT NULL,
                           telephone VARCHAR(255),
                           email VARCHAR(255),
                           city VARCHAR(255),
                           country VARCHAR(255),
                           address VARCHAR(255),
                           birth_date DATE,
                           hire_date DATE,
                           photo_filename VARCHAR(255),
                           is_fired BOOLEAN,
                           FOREIGN KEY (working_position_id) REFERENCES working_positions(id),
                           FOREIGN KEY (gender_id) REFERENCES genders(id)
);


CREATE TABLE users (
                       id       SERIAL PRIMARY KEY,
                       username VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL,
                       employee_id INT DEFAULT NULL,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255),
                       FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE
);


CREATE TABLE roles (
                       id   SERIAL PRIMARY KEY,
                       name VARCHAR(100) NOT NULL
);


CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            role_id INT NOT NULL,

                            FOREIGN KEY (user_id) REFERENCES users (id),
                            FOREIGN KEY (role_id) REFERENCES roles (id),

                            UNIQUE (user_id, role_id)
);


CREATE TABLE project_statuses (
                                  id SERIAL PRIMARY KEY,
                                  name VARCHAR(255) NOT NULL
);

CREATE TABLE project_objectives (
                                    id SERIAL PRIMARY KEY,
                                    name VARCHAR(255) NOT NULL
);



CREATE TABLE projects (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          project_objective_id INT NOT NULL,
                          project_status_id INT NOT NULL,
                          description VARCHAR(255),
                          notes VARCHAR(255),
                          start_date DATE,
                          end_date DATE,
                          FOREIGN KEY (project_objective_id) REFERENCES project_objectives(id),
                          FOREIGN KEY (project_status_id) REFERENCES project_statuses(id)
);

CREATE TABLE projects_employees (
                                    project_id INT NOT NULL,
                                    employee_id INT NOT NULL,

                                    FOREIGN KEY (project_id) REFERENCES projects (id),
                                    FOREIGN KEY (employee_id) REFERENCES employees (id),

                                    UNIQUE (project_id, employee_id)
);

CREATE TABLE posts (
                       id SERIAL PRIMARY KEY,
                       title VARCHAR(255),
                       content TEXT,
                       content_preview TEXT,
                       amount_of_likes INT DEFAULT 0,
                       image_filename VARCHAR(255),
                       post_date timestamp NOT NULL,
                       post_update_date timestamp NULL
);

CREATE TABLE comments (
                          id  SERIAL PRIMARY KEY,
                          text TEXT,
                          comment_date timestamp NOT NULL,
                          post_id INT NOT NULL,
                          user_id INT NOT NULL,
                          FOREIGN KEY (post_id) REFERENCES posts (id) ON DELETE CASCADE,
                          FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE likes (
                       id       SERIAL PRIMARY KEY,
                       post_id INT NOT NULL,
                       user_id INT NOT NULL,
                       FOREIGN KEY (post_id) REFERENCES posts (id),
                       FOREIGN KEY (user_id) REFERENCES users (id)
);

INSERT INTO users VALUES (1, 'admin',
                          '$2a$11$0WE0P1I48qwdlencv7uloOdv8wiyU0OlhXhjbYPJjZnT1XaCB8klS',null, 'Nikola', 'Tesla');
INSERT INTO users VALUES (2, 'user',
                          '$2a$11$yrcZ20Q99Ftpe73IaK0qN.shgbxgVNYztd8XIgPV3oY3EMSZ9Z84m',null, 'James', 'Bond');
INSERT INTO users VALUES (3, 'user1',
                          '$2a$11$RGgEJlAdU4wDGjyxoWa5/eA7h29GveUdSm7.xpOX0gP6Ln75zxwCO',null, 'Thomas', 'Riddle');
INSERT INTO users VALUES (4, 'user2',
                          '$2a$11$rOALwbgss12siBVGjJiTOOBU/qLfPYtYwQuCxjsykvJOapl9pMZty',null, 'Jack', 'Sparrow');
INSERT INTO users VALUES (5, 'user3',
                          '$2a$11$SwtiXTRHuBaPsBts6bu5VeyI.6QYJaFWuiDlzz.aPZG97Qsq0STAK',null, 'Sherlock', 'Holmes');

INSERT INTO roles VALUES (1, 'ROLE_USER');
INSERT INTO roles VALUES (2, 'ROLE_ADMIN');

INSERT INTO user_roles VALUES (1, 2);
INSERT INTO user_roles VALUES (2, 1);
INSERT INTO user_roles VALUES (3, 1);
INSERT INTO user_roles VALUES (4, 1);

INSERT INTO genders VALUES (1,'male');
INSERT INTO genders VALUES (2,'female');

INSERT INTO project_objectives VALUES (1,'Android application');
INSERT INTO project_objectives VALUES (2,'Android game');
INSERT INTO project_objectives VALUES (3,'Blog');
INSERT INTO project_objectives VALUES (4,'Browser game');
INSERT INTO project_objectives VALUES (5,'Web-shop');
INSERT INTO project_objectives VALUES (6,'Social network');
INSERT INTO project_objectives VALUES (7,'Other');

INSERT INTO project_statuses VALUES (1,'In progress');
INSERT INTO project_statuses VALUES (2,'Overdue');
INSERT INTO project_statuses VALUES (3,'Inactive');
INSERT INTO project_statuses VALUES (4,'Completed');

INSERT INTO working_positions VALUES (1,'Java junior');
INSERT INTO working_positions VALUES (2,'Java middle');
INSERT INTO working_positions VALUES (3,'Java senior');

--------------------------- optional inserts ---------------------------

INSERT INTO projects VALUES (1,'Fire',1,1,'some description','some notes','2016-01-01','2018-06-08');
INSERT INTO projects VALUES (2,'Mario',2,2,'some description','some notes','2016-01-01','2017-01-08');
INSERT INTO projects VALUES (3,'HotBlog',3,3,'some description','some notes','2016-01-01','2018-05-08');
INSERT INTO projects VALUES (4,'Shooter',4,4,'some description','some notes','2016-01-01','2016-06-08');

INSERT INTO employees VALUES (1,'Nikola','Tesla',1,1,'9379992','email@gmail.com','London','United Kingdom',
                              'Baker st. 221B','1856-06-10','2001-01-01',null,false);
INSERT INTO employees VALUES (2,'James','Bond',2,1,'9379992','email@gmail.com','London','United Kingdom',
                              'Baker st. 221B','1968-03-02','2003-01-01',null,false);
INSERT INTO employees VALUES (3,'Thomas','Riddle',3,1,'9379992','email@gmail.com','London','United Kingdom',
                              'Baker st. 221B','1880-07-01','2005-01-01',null,false);
INSERT INTO employees VALUES (4,'Jack','Sparrow',2,1,'9379992','email@gmail.com','London','United Kingdom',
                              'Baker st. 221B','1666-06-06','2010-01-01',null,true);

UPDATE users SET employee_id = 1 WHERE id = 1;
UPDATE users SET employee_id = 2 WHERE id = 2;
UPDATE users SET employee_id = 3 WHERE id = 3;
UPDATE users SET employee_id = 4 WHERE id = 4;

INSERT INTO projects_employees VALUES (1,1);
INSERT INTO projects_employees VALUES (1,2);
INSERT INTO projects_employees VALUES (2,1);
INSERT INTO projects_employees VALUES (2,2);
INSERT INTO projects_employees VALUES (3,3);
INSERT INTO projects_employees VALUES (3,1);
INSERT INTO projects_employees VALUES (4,2);
INSERT INTO projects_employees VALUES (4,4);

INSERT INTO posts VALUES (1,'Lorem ipsum dolor sit amet',
                          'Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                          sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                          Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum.',
                          'Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                          sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                          Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum...',
                          3, null, '2017-02-25 15:04:32');
INSERT INTO posts VALUES (2,'Lorem ipsum dolor sit amet',
                          'Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                          sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                          Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum.',
                          'Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                          sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                          Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum...',
                          4, null, '2017-02-26 19:04:32');
INSERT INTO posts VALUES (3,'Lorem ipsum dolor sit amet',
                          'Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                          sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                          Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum.',
                          'Lorem ipsum dolor sit amet, consectetur adipiscing elit,
                          sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
                          Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur. Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum...',
                          2, null, '2017-02-27 20:04:32');

INSERT INTO likes VALUES (1,1,1);
INSERT INTO likes VALUES (2,1,2);
INSERT INTO likes VALUES (3,1,3);
INSERT INTO likes VALUES (4,2,1);
INSERT INTO likes VALUES (5,2,2);
INSERT INTO likes VALUES (6,2,3);
INSERT INTO likes VALUES (7,2,4);
INSERT INTO likes VALUES (8,3,1);
INSERT INTO likes VALUES (9,3,2);

INSERT INTO comments VALUES (1,'Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum?','2017-03-28 19:30:32',1,1);
INSERT INTO comments VALUES (2,'Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur.','2017-03-28 20:30:32',1,2);
INSERT INTO comments VALUES (3,'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat.','2017-03-28 21:30:32',2,3);
INSERT INTO comments VALUES (4,'Excepteur sint occaecat cupidatat non proident, sunt in
                          culpa qui officia deserunt mollit anim id est laborum?','2017-03-28 10:30:32',2,1);
INSERT INTO comments VALUES (5,'Duis aute irure dolor in
                          reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla
                          pariatur.','2017-03-28 11:30:32',3,2);
INSERT INTO comments VALUES (6,'Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris
                          nisi ut aliquip ex ea commodo consequat.','2017-03-28 12:30:32',3,3);

select setval('comments_id_seq', (select COALESCE(max(id) + 1, 1) from comments), false);
select setval('employees_id_seq', (select COALESCE(max(id) + 1, 1) from employees), false);
select setval('genders_id_seq', (select COALESCE(max(id) + 1, 1) from genders), false);
select setval('likes_id_seq', (select COALESCE(max(id) + 1, 1) from likes), false);
select setval('posts_id_seq', (select COALESCE(max(id) + 1, 1) from posts), false);
select setval('project_objectives_id_seq', (select COALESCE(max(id) + 1, 1) from project_objectives), false);
select setval('project_statuses_id_seq', (select COALESCE(max(id) + 1, 1) from project_statuses), false);
select setval('projects_id_seq', (select COALESCE(max(id) + 1, 1) from projects), false);
select setval('roles_id_seq', (select COALESCE(max(id) + 1, 1) from roles), false);
select setval('users_id_seq', (select COALESCE(max(id) + 1, 1) from users), false);
select setval('working_positions_id_seq', (select COALESCE(max(id) + 1, 1) from working_positions), false);

/*

drop table likes;
drop table comments;
drop table posts;
drop table projects_employees;
drop table projects;
drop table project_objectives;
drop table project_statuses;
drop table user_roles;
drop table roles;
drop table users;
drop table employees;
drop table working_positions;
drop table genders;

*/
