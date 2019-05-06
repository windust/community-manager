# community-manager

Add full project description and goals of this project.

---

## Table of Contents

1. Getting started
    1. Prerequisites
       1. Libraries which need to be installed
       2. Getting credentials
       3. Spreadsheet setup
    2. Installing
       1. Setup configuration files
2. Running the tests
    1. Breakdown into the end-to-end tests
    2. And coding style tests
3. System Architecture
   1. Model
      1. Entities
      2. Collections
   2. DataStorage
   3. Controller
   4. OAuth
   5. Exceptions
   6. Resources
4. Deployment
5. Security considerations
   1. Data backup and redundancy procedure
6. Built with
7. Contributing
8. Authors
9. License
10. Acknowledgments

---

## Getting started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites

#### Libraries which need to be installed

* JUnit 5
* ...

#### Getting credentials

#### Spreadsheet setup

### Installing

#### Setup configuration files

---

## Running the tests

Explain how to run the automated tests for this system

### Breakdown into the end-to-end tests

Explain what these tests test and why

`Give an example`

### And coding style tests

Explain what these tests test and why

`Give an example`

---

## System Architecture

### Model

#### Entities

#### Collections

### DataStorage

### Controller

### OAuth
1. Go to website: https://console.developers.google.com
    1. Click Credentials tab at left of page.
    2. Click Create Credentials blue button.
    3. Click OAuth client ID from dropdown on button.
    4. Click Web Application under Application Type and hit create. 
    5. Under Authorized redirects URIs have this route: http://localhost:8080/login/oauth2/code/google
    6. Hit Save.
    7. Copy OAuth Client ID and Secret from OAuth Client pop to clipboard or some other location.
2. Go to application properties 
    1. Paste client ID after: spring.security.oauth2.client.registration.google.client-id=
    2. Paste client Secret after: spring.security.oauth2.client.registration.google.client-secret=
3. Go to build.gradle and under dependencies add: 
    1. implementation 'org.springframework.boot:spring-boot-starter-oauth2-client'
    2. implementation 'org.springframework.boot:spring-boot-starter-oauth2-resource-server'
    3. implementation 'org.springframework.cloud:spring-cloud-starter-oauth2'
4. Create SecurityConfig class that extends WebSecurityConfigurerAdapter
    1. Create public method configure with parameters HttpSecurity http and throws an Exception.
    2. http: a lot of stuff happens here to get OAuth login working properly.
         1. authorizeRequests():
         2. .antMatchers("/", "/css/main.css", "/js/available_dates.js", "/images/logo_draft_1.png",
           "/venue", "/venueSignUp", "/food", "/foodSignUp").permitAll(): Here antMatchers and permitAll defines what routes is permitted before authentication. 
         3. .anyRequest().authenticated(): anyRequest and authenticated after antMatchers sends any request next through OAuth login.
         4. .and(): Just to include what's next.
         5. .oauth2Login().defaultSuccessUrl("/loginSuccess", true): Sends your login after successful login attempt to a login method you'll create in the Controller.
         6. .and(): Just to include what's next.
         7. .logout("/log_out").logoutUrl().logoutSuccessUrl(): logoutUrl is your log out method in controller and logoutSuccessUrl is whats displayed after successful logout of app.
         8. .and(): Just to include what's next.
         9. .csrf().disable("/"): You'll need to disable the csrf.
5. Admin Controller to handle login and logout
    1. login method: routes to OAuth login page or your own custom login page.
    2. loginSuccess method with the following parameters: HttpServletRequest request, OAuth2AuthenticationToken authentication
          1. Map<String, Object> properties = authentication.getPrincipal().getAttributes(): This puts principal info into a map.
          2. String email = (String) properties.get("email"): Here we get an email to check the user email.
          3. We use the email to check against a list of valid users on the backend before they are granted access.
    3. logOut method
          1. new SecurityContextLogoutHandler().logout(request, null, null): This line resets the security upon log out and ensures if you are logged out of GMail you are logged out of the app.
          2. loggedIn = false: resets boolean to false
          3. return "redirect:/": redirects user to homepage.
### Exceptions

### Resources

---

## Deployment

Add additional notes about how to deploy this on a live system

---

## Security considerations

### Data backup and redundancy procedure

---

## Built with

* Spring Boot - The web framework used
* Gradle - Dependency Management

## Contributing

Please read CONTRIBUTING.md for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

`acknowledge specific developers and roles they had`

See also the list of [contributors](https://github.com/windust/community-manager/graphs/contributors) who participated in this project.

## License

This project is listed under the Apache License Version 2.0 - see the LICENSE.md for details

## Acknowledgments

* Hat tip to anyone whose code was used
* Inspiration
* etc
