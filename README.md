# Patient Information System

  

## Purpose

The purpose of Patient Information System is to facilitate application users to view patient information. System will provide following core functionalities

1. frontend interface to add, edit, view and delete patients in the system

2. Restrict application access only to authorized users

3. Only allow admin users to delete patient information

  
  

#### Application Front End

http://localhost:3000

#### Swagger web UI

http://localhost:8080/swagger-ui/index.html

#### Swagger API URL

http://localhost:8080/v3/api-docs

  

### Application Code

#### Backend

https://github.com/sekhoniqbal/patient-info-system-backend

#### Frontend

https://github.com/sekhoniqbal/patient-info-system-frontend

  

## Architecture and technologies

### Requirements
Patient Information System was designed keeping in mind below points
- backend must provide api to manage CRUD operations on patients.
- backend must be built using spring boot.
- frontend must be built with modern frontend framework such as react
- system is only accessible to authorized users
- system must have role based access control

#### Backend Technology

Spring boot framework has been used to implement backend. Framework allows to easily build highly scalable restful web API services with powerful and customizable authentication and access-controls.

#### Frontend Technology

React has been used to build the frontend user interfaces for the system.React will allow to building interactive user interfaces quickly and efficiently as it makes application creation and maintenance fast and easy through reusable components.

  

## Database Design

For local development and for demonstration of the functionality of the system, backend of the application uses sqlite database, which is a file based database.   Application config can be easily changed  to point to a different database server for production environment. For the core functionality of the application, database is designed as below.

- database consist of 2 tables namely patient and user.
- each table has the unique id column as primary key.
- each user has id, username, role, enabled, full name and password(only password hash is stored for security reasons) fields.
- each patient has id, date of birth, first name and last name fields

## Overview of API endpoints

Backend application provides its service through restful web service. Application is accessible through below listed api endpoints.

### Patient API Endpoint
GET[/api/patients/{id}](http://localhost:8080/swagger-ui/index.html#/patient-controller/getPatient_1)

- get the patient with given id

- throw not found error if id is valid but patient is not found

- throw bad request error if id is invalid

  

PUT[/api/patients/{id}](http://localhost:8080/swagger-ui/index.html#/patient-controller/updatePatient)

- update the patient with the given id

- throw not found error if id is valid but patient is not found

- throw bad request error if id is invalid

- throw bad request error if patient first name, last name or date of birth is blank

  

DELETE[/api/patients/{id}](http://localhost:8080/swagger-ui/index.html#/patient-controller/deletePatient)

- throw not found error if id is valid but patient is not found

- throw bad request error if id is invalid
- throw forbidden error if non admin user tries to delete a patient

  

GET[/api/patients](http://localhost:8080/swagger-ui/index.html#/patient-controller/getPatient)

- get all patients

  

POST[/api/patients](http://localhost:8080/swagger-ui/index.html#/patient-controller/createPatient)

- create new patient with given first name, last name and date of birth

- throw bad request error if first name, last name or date of birth is missing

## Application Backend Installation

Backend of the application has been built using the spring boot framework.

It needs java 17 installed.

### Installing java on Linux.

below are commands to install java on linux server running ubuntu

  

`cd /opt/app`

`wget https://download.oracle.com/java/17/archive/jdk-17.0.7_linux-x64_bin.tar.gz`

`gunzip jdk-17.0.7_linux-x64_bin.tar.gz`

` tar -xf jdk-17.0.7_linux-x64_bin.tar`

setup JAVA_HOME and PATH variable correctly

`JAVA_HOME=/opt/app/jdk-17.0.7`

`export JAVA_HOME`

`PATH=$PATH:$JAVA_HOME/bin`

`export PATH`

  

### Installing java on Windows

download and install jdk from https://download.oracle.com/java/17/latest/jdk-17_windows-x64_bin.msi

  

### Running Backend Application

download application code

`git clone https://github.com/sekhoniqbal/patient-info-system-backend`

  

#### for linux

make the mvnw file executable if not already

`cd /opt/app/patient-info-system-backend`

`chmod 777 /opt/app/patient-info-system-backend/mvnw`

`mvnw clean package`

`mvnw spring-boot:start`

#### for windows

open the folder `patient-info-system-backend` folder in windows command prompt

run the command

`mvnw clean package`

`mvnw spring-boot:start`

  

Once the application launches, you should be able to access it at

http://localhost:8080/

http://localhost:8080/v3/api-docs

http://localhost:8080/swagger-ui/index.html

  
  
  

## Application Frontend Installation

Frontend of the application has been build using react frontend framework. Requirement to run the react application is to have node and npm on development machine.

please follow the steps given at https://nodejs.org/en to install LTS version of nodejs and then follow the steps below.

`cd /opt/app`

`git clone https://github.com/sekhoniqbal/patient-info-system-frontend`

  

`cd /opt/app/patient-info-system-frontend`

`npm install`

  

### for local development or demo

`npm start`

in your browser go to http://localhost:3000

  

### for production build

`npm run build`

`npx serve -s -l 3000 ./build`

go to http://yourservername:3000