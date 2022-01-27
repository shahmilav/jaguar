<h1 align="center">Jaguar</h1>
<p align="center">
    <img src="https://github.com/shahmilav/jaguar/blob/master/images/login-screenshot.png">
    <i>Jaguar</i> is an open source Java project that aims to be a fully functioning web app. It has a login and sign up page to
    access the dashboard.
</p>

## Table of Contents

* [Project features and functionality](#features)
* [Installation](#installation)
* [How to run the program](#running-the-program)
* [Frameworks used](#frameworks-used)


## Features
- [X] Edit profile functionality
- [X] A cool dashboard
- [X] Delete account functionality
- [X] Extremely secure password hashing (new hash each time!)
- and more to come, such as
    - [ ] Sign in with Google
    - [ ] Captcha
    - [ ] Forgot password functionality

## Installation
To install, clone the git repo: 
```git clone https://github.com/shahmilav/jaguar```

## Running the Program
To run the program, type ```mvn springboot:run``` in the command line and go to _localhost:8080_ in a browser. The app
should be running. Make sure you have maven installed on your machine. Alternatively, run it using your favorite code
editor.

## Frameworks Used
This project uses

- [Apache Maven](https://maven.apache.org/) - framework for project structure
- [Spring](https://spring.io/) - a useful framework for web apps
- [Thymeleaf](https://www.thymeleaf.org/) - convenient way to make a web app
- [MongoDB](https://www.mongodb.com/) for all our database needs
- [Apache Log4j 2](https://logging.apache.org/log4j/2.x/) - for logging
- _All dependencies are in the [pom.xml](https://github.com/shahmilav/jaguar/blob/main/pom.xml) file._
