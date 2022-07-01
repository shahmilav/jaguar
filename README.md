# Jaguar

Try it [here](http://104.238.134.163:8080/).

Jaguar is an open source Java project that aims to be a fully functioning web app. It has a login and sign up page to
access the dashboard. Furthermore, users can edit and delete their account.


<!-- TODO: Turn image to a link to the website, and make it transparent.) -->

![A screenshot demonstrating the login page.](images/login-screenshot.png)

## table of contents

* [features](#features)
* [installation](#installation)
* [dependencies](#dependencies)
* [usage](#running-the-program)
* [frameworks](#frameworks-used)

## features

* [X] Edit profile functionality
* [X] A cool dashboard
* [X] Delete account functionality
* [X] Extremely secure password hashing (new hash each time!)
* and more to come, such as
    * [ ] Sign in with Google and GitHub
    * [ ] Captcha
    * [ ] Forgot password functionality

## installation

To install, clone the git repo:

```shell
git clone https://github.com/shahmilav/jaguar
```

## dependencies

The project requires the latest version of [Apache Maven](https://maven.apache.org/)
and [Java](https://adoptopenjdk.net/releases.html). Furthermore, to contribute, make sure you
have [geckodriver](https://github.com/mozilla/geckodriver) and [Firefox](https://www.mozilla.org/en-US/firefox/new/)
installed as Selenium runs on Firefox.

## running the program

To run the program, type

```shell
mvn spring-boot:run
``` 

in the command line and go to [localhost:8080](http://localhost:8080) in a browser. The app should be running.

## frameworks used

This project uses


![Java](https://img.shields.io/badge/Java-f0921b?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/SpringBoot-6db33f?style=for-the-badge&logo=spring&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Thymeleaf](https://img.shields.io/badge/Thymeleaf-05F0F?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=Apache-maven&logoColor=white)
![Bootstrap](https://img.shields.io/badge/Bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)
