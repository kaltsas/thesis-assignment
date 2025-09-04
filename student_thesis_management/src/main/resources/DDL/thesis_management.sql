CREATE DATABASE IF NOT EXISTS thesis_management;
USE thesis_management;

DROP TABLE IF EXISTS applications;
DROP TABLE IF EXISTS subjects;
DROP TABLE IF EXISTS students;
DROP TABLE IF EXISTS theses;
DROP TABLE IF EXISTS professors;
DROP TABLE IF EXISTS users;

CREATE TABLE users(
  id int NOT NULL AUTO_INCREMENT,
  user_name text DEFAULT NULL,
  password text DEFAULT NULL,
  first_name text DEFAULT NULL,
  last_name text DEFAULT NULL,
  role text DEFAULT NULL,
  PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE professors(
  professor_id int NOT NULL AUTO_INCREMENT,
  username text DEFAULT NULL,
  first_name text DEFAULT NULL,
  last_name text DEFAULT NULL,
  specialty text DEFAULT NULL,
  PRIMARY KEY (professor_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE theses(
  thesis_id int NOT NULL AUTO_INCREMENT,
  title text DEFAULT NULL,
  professor_id int DEFAULT NULL,
  description text DEFAULT NULL,
  implementation_grade real DEFAULT NULL,
  report_grade real DEFAULT NULL,
  presentation_grade real DEFAULT NULL,
  final_grade real DEFAULT NULL,
  PRIMARY KEY (thesis_id),
  FOREIGN KEY (professor_id) REFERENCES professors(professor_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE students(
  student_id int NOT NULL AUTO_INCREMENT,
  username text DEFAULT NULL,
  first_name text DEFAULT NULL,
  last_name text DEFAULT NULL,
  year int DEFAULT NULL,
  grade real DEFAULT NULL,
  remaining_courses int DEFAULT NULL,
  graduated boolean DEFAULT NULL,
  thesis_id int DEFAULT NULL,
  PRIMARY KEY (student_id),
  FOREIGN KEY (thesis_id) REFERENCES theses(thesis_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE subjects(
  subject_id int NOT NULL AUTO_INCREMENT,
  name text DEFAULT NULL,
  professor_id int DEFAULT NULL,
  description text DEFAULT NULL,
  availability boolean DEFAULT NULL,
  num_semesters int DEFAULT NULL,
  PRIMARY KEY (subject_id),
  FOREIGN KEY (professor_id) REFERENCES professors(professor_id)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

CREATE TABLE applications(
  application_id int NOT NULL AUTO_INCREMENT,
  student_id int DEFAULT NULL,
  subject_id int DEFAULT NULL,
  message text DEFAULT NULL,
  PRIMARY KEY (application_id),
  FOREIGN KEY (student_id) REFERENCES students(student_id),
  FOREIGN KEY (subject_id) REFERENCES subjects(subject_id) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=latin1;

#dummy inserts that will help with dao later
INSERT INTO professors (professor_id) VALUES (-1);
INSERT INTO theses (thesis_id, professor_id) VALUES (-1, -1);

#dummy inserts for testing
INSERT INTO users VALUES (1,"bob","123","Bob","Marcus","STUDENT");
INSERT INTO professors VALUES (1,"bob","Jhon","Mendus","robotics");
INSERT INTO theses VALUES (1, "web application", 1, "we will use AWS", 5.5, 6.3, 7.9, 8.2);
INSERT INTO students VALUES (1,"george","George","Moors",2019, 8.13, 2, true, 1);
INSERT INTO subjects VALUES (1,"robotics project",1,"we will use ROS",false, 2);
INSERT INTO applications VALUES (1,1,1,"I liked that subject");
