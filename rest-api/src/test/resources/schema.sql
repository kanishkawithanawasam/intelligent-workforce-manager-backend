# Create the database
CREATE DATABASE Intelligent_Workforce_DB;

# Select Schema
  USE Intelligent_Workforce_DB;

# Employee Table
CREATE TABLE Employee (
      id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
      first_name VARCHAR(20) NOT NULL,
      last_name VARCHAR(20) NOT NULL,
      date_of_birth DATE NOT NULL,
      address_line1 VARCHAR(50) NOT NULL,
      post_code VARCHAR(10) NOT NULL,
      contact_number VARCHAR(11) NOT NULL
);

# Schedule Table
CREATE TABLE Schedule (
      id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
      week_start_date DATE NOT NULL
);

# Shift Table
CREATE TABLE Shift (
       id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
       shift_date DATE NOT NULL,
       start_time TIME NOT NULL,
       end_time TIME NOT NULL,
       employee_id BIGINT NOT NULL,
       schedule_id BIGINT NOT NULL,
       CONSTRAINT fk_shift_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
           ON DELETE CASCADE ON UPDATE CASCADE,
       CONSTRAINT fk_shift_schedule FOREIGN KEY (schedule_id) REFERENCES schedule(id)
           ON DELETE CASCADE ON UPDATE CASCADE
);


# Employee Contract Data
CREATE TABLE Contract_Data (
       id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
       hourly_rate DOUBLE NOT NULL,
       max_hours_per_week DOUBLE NULL,
       min_hours_per_week Double NULL,
       start_date Date NOT NULL,
       end_date Date NULL,
       role varchar(20) NOT NULL,
       employee_id BIGINT NOT NULL,
       CONSTRAINT fk_contract_employee FOREIGN KEY (employee_id) REFERENCES employee(id)
);

# Employee Preferences Table
CREATE TABLE Preferences (
         id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
         weekly_hours DOUBLE NOT NULL,
         employee_id BIGINT NOT NULL,
         CONSTRAINT fk_preferences_employee FOREIGN KEY (employee_id) REFERENCES employee(id));


# User table representing login information
CREATE TABLE User(
     id BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
     email varchar(225) NULL,
     password varchar(225) NULL)
    #employee_id BIGINT NOT NULL,
    #CONSTRAINT fk_user_employee FOREIGN KEY (employee_id) REFERENCES employee(id));