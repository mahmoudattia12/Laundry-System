# Laundry-System
A Laundry System Web App developed with spring boot, MySQL, JPA and React

## Contents:
- [Frameworks and technology used](#Frameworks-and-technology-used)
- [How to run](#How-to-run)
- [Used design patterns](#used-design-patterns)
- [UML class diagram](#UML-class-diagram)
- [Snapshots of our UI and a user guide](#Snapshots-of-our-UI-and-a-user-guide)
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
- open the LaundrySystem-Frontend folder using visual studio IDE, then open the terminal of the IDE, and write:
- npm run dev in the terminal.
- then click on the provided local link.
- make sure that the opened local link is the same at the origins of the @CrossOrigin(origins = "local link") annotations in all classes of the server controller package in the backend.
---
## Used design patterns:
---
## UML class diagram:
---
## Snapshots of our UI and a user guide:
---
## Demo Video for using the app:


