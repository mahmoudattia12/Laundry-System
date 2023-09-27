# Laundry-System
A Laundry System Web App developed with spring boot, MySQL, JPA and React

## Contents:
- [Frameworks and technology used](#Frameworks-and-technology-used)
- [How to run](#How-to-run)
- [Used design patterns](#used-design-patterns)
- [Design Diagrams](#Design-Diagrams)
- [Demo Video for using the app](#Demo-Video-for-using-the-app)
---
## Frameworks and technology used:
- For the frontend part (view part): React library with typescript.
- For the backend (model and controller): Java with spring boot framework and for the database MySQL and JPA.
---
## How to run:
- Note: Make sure you have downloaded NodeJs.
- extract the compressed project folder.
#### Back-end part:
- Open the LaundrySystem-Backend folder using IntelliJ IDE, and run the LaundrySystemApplication.java class on port 9080. you can change the port from the project resources at application.properties (server.port = …..) if the 9080 port was already used in your device but in this case, you will need to change it in all http requests in the angular folder.
#### Front-end part:
- open the LaundrySystem-Frontend folder using visual studio IDE, then open the terminal of the IDE (current directory must be the LaundrySystem-Frontend folder), and write:
- npm install.
- npm run dev.
- then click on the provided local link.
- make sure that the opened local link is the same at the origins of the @CrossOrigin(origins = "local link") annotations in all classes of the server controller package in the backend.
---
## Used design patterns:
### 1- Singleton design pattern:
It was used in all service classes of the service provider package, Sorting package, Filter package inaddition to the JPA repositories used to deal with MySQL database. All these classes provide the same services each time they are used so no need for creating so manu instances of them, instead I used singleton design pattern so creating only a single instance and use it whenever needed. This instance is injected whenever needed using @Autowired annotation. 
### 2- Facade design pattern:
It was used in the service provider package classes. It was used to handle the client requests in the controller package. When the client request any of the system functionality there is an opposite class that is responsible for providing this system functionality to the client (For example providing for it all CRUD database operations).
### 3- Factory design pattern:
It was used in the Emplyee Helper package to help the Employee services to use the task and holiday services in an easier way based on the passed param and here the employee services in considered as the client.
### 4- Filter design pattern:
It was used in the filter package to filter or search employees/orders/customers based on a passed criteria or more than one criteria using the And/Or criteria. The Facade design pattern was also applied here as Each of the Filter Employee/Order/customer classes was asking its JPA repository to do its real work using MySQL queries.
### 5- Strategy design pattern:
It was used in the Sorting and the Filter packages. In the sort package there was the Sort Employee/Order/Customer classes each provide its own sorting alogorithm and all of them implements the ISorterStrtegy interface and the SortServices(sorter context) based on the client request choose which alogorithm to use. The same was applied in the Filter package. This was also simplified the controller Filter/Sort requests.
### 6- Adapter design pattern:
It was used for the Order and Customer entities To convert a list of Orders/Customers to a list of ToSendOrder/ToSendCustomer (what the front expected) using the OrderAdapter/CustomerAdapter classes in the service provider package.
### 7- Model-View-Controller (MVC):
- View (Frontend): In the MVC pattern, the "View" is responsible for presenting data to the user and handling user interactions.
- Controller (Backend): The "Controller" in MVC is responsible for handling user input, processing requests, and managing the flow of data between the Model and the View. In a web application, the Controller corresponds to the backend server, which receives HTTP requests from the frontend, processes them, interacts with the database (the Model), and sends back responses to the frontend. It contains the application's business logic and serves as an intermediary between the View and the Model.
- Model (Database): The "Model" represents the data and the business logic of the application. In the context of a web application with a MySQL database, the Model corresponds to the database itself and the data stored within it. It includes the tables, entities, and relationships within the database schema. The backend (Controller) interacts with the Model (database) to perform CRUD (Create, Read, Update, Delete) operations, retrieve data, and maintain data integrity.
---
## Design Diagrams:
### Entity Relationship diagram:
![Laundry ER diagram](https://github.com/mahmoudattia12/Laundry-System/assets/96799025/1fe1d8ad-ae90-4b1e-b793-c5feed0bbc96)
### Relational database tables:
![LaundrySystem Relational database tables](https://github.com/mahmoudattia12/Laundry-System/assets/96799025/dedf438e-d78a-4752-ade2-74df0e477556)
### UML class diagram:
- [link to the uml class diagram](https://drive.google.com/file/d/1ocEMtN3ttcIXc5rS1hyM8YQyvSVRSCQm/view?usp=sharing)
---
## Demo Video for using the app:
https://github.com/mahmoudattia12/Laundry-System/assets/96799025/98acc27e-e908-4362-a22f-fc8ef5ddd99c
