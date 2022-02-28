# jaguar

Jaguar is an open source Java project that aims to be a fully functioning web app. It has a login and sign up page to
access the dashboard. Furthermore, users can edit and delete their account.


<!-- TODO: Turn image to a link to the website, and make it transparent.) -->

![login_screenshot](https://github.com/shahmilav/jaguar/blob/master/images/login-screenshot.png)

## table of contents

* [features](#features)
* [installation](#installation)
* [dependencies](#dependencies)
* [usage](#running-the-program)
* [frameworks](#frameworks-and-technologies-used)

## features

* [X] Edit profile functionality
* [X] A cool dashboard
* [X] Delete account functionality

* and more to come, such as
    * [ ] Extremely secure password hashing (new hash each time!)
    * [ ] Sign in with Google and GitHub
    * [ ] Captcha
    * [ ] Forgot password functionality

## installation

To install, clone the git repo:
```git clone https://github.com/shahmilav/jaguar```

## dependencies

The project requires the latest version of [Apache Maven](https://maven.apache.org/)
and [Java](https://adoptopenjdk.net/releases.html). Furthermore, to contribute, make sure you
have [geckodriver](https://github.com/mozilla/geckodriver) and [Firefox](https://www.mozilla.org/en-US/firefox/new/)
installed as Selenium runs on Firefox.

## running the program

To run the program, type ```mvn spring-boot:run``` in the command line and go to _localhost:8080_ in a browser. The app
should be running.

## frameworks used

This project uses

- [Apache Maven 3.6.3](https://maven.apache.org/) build tool.
- [SpringBoot 5](https://spring.io/), a useful framework for web apps.
- [Thymeleaf 3](https://www.thymeleaf.org/), convenient way to make a web app.
- [MongoDB](https://www.mongodb.com/) for all our database needs.
- [Bootstrap v5](https://getbootstrap.com/) for web design.
- [Apache Log4j 2](https://logging.apache.org/log4j/2.x/) for logging.
- *All dependencies can be found in the [pom.xml](https://github.com/shahmilav/jaguar/blob/main/pom.xml) file.*
