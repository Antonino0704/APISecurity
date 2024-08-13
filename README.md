# APISecurity
A simple java project to learn authentication with jwt (json web token)

# What is a JWT ?
JWT stands for json web token and it is an authentication token. It's used to authenticate a user without sending the password every time, the token is sent with the request in the headers with ```Authorization: Bearer <token>```, in the jwt are usually saved email and privileges. [To learn more](https://jwt.io/)


# Requirements
- <img  src="https://img.shields.io/badge/-Java%2017-white?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABAAAAAQCAMAAAAoLQ9TAAAAh1BMVEXe5uzAztqxwtHc4OT09PT19fXw8PCqu8rn5+f8/Pzs0Lf6+vrnrXrleiugscLk5OT39/fs7OzkcR7lx6zh4eHhr4LhZRLhpW+Vp7fd3d3gYA6Knq3W1tbbbhzXoW1cepV6jZ6EmKdIZoJWc4vQ0NB8kJ6otb3Ly8twhZV1jqW5vcK9yM9ofY52NCXaAAAAr0lEQVR4AU3KhUIDMQwA0BwrWWftZWQSLrDq/P+/jxPsVaIAzcs/TV/PzOsI+2dmDcztYoTLVf/bed9YDXC9MX3oG863PVpv6c20rXfg2BiDuz0ftmgMOzgKItKe9nsiRDn2DSKyG3m3Vpj6RqfWq+rHp8opiHYQ1XNKub8dp1Ai1MIi554rJYVLhXAR0Zz6DU1BLwGCsmqZMGuAq7K7nSbe6hXqnf/4e4VHfP4TH1/dVRTdLcfq+gAAAABJRU5ErkJggg==&logoSize=auto" alt="Java" />  

- <img src="https://img.shields.io/badge/-MySQL-white?logo=MySQL" alt="MySQL" /> ![Static Badge](https://img.shields.io/badge/OR-gray) <img src="https://img.shields.io/badge/-MariaDB-c4745c?logo=MariaDB" alt="Maria DB" />
- <img src="https://img.shields.io/badge/PostMan-(recommended)-gray?logo=postman&logoColor=white&labelColor=orange" alt="postman" />


For maven, you can run mvnw.cmd or use your maven installed/maven integrated with your IDE

# Setup
- Fork and clone the repository
- Create the ```resources``` folder in ```src/main/```
- Create ```application.properties``` file into ```resources``` folder
- Add the following properties:
    ```
    spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
    spring.datasource.username=YourDBUser
    spring.datasource.password=YourPasswdUser
    spring.datasource.url=jdbc:mariadb://localhost:3306/YuorDBName
    dev=false
    ```

# Build
Run mvnw.cmd (or your maven installed/maven integrated with your IDE) 

```mvnw package```

And run jar file ```yourJDKPath/bin/java -jar target/APISecurity-VERSION.jar```

The jar file'll create the public.key and private.key files in the same folder

# Test
You can use postman to make requests to the server

Endpoints are:
```
/apiV0/auth/login
/apiV0/auth/registration

/apiV0/user/{email}
/apiV0/user/edit/{email}
/apiV0/user/{email}/privilege

/apiV0/privilege/
```
We create the following roles in DB: employee, admin, manager, CEO. The highest role has the highest id, every user who has a certain role will in turn have all the roles lower than their.

First request, let's register (we'll get 200 ok or 400 bad request)
>![image](https://github.com/user-attachments/assets/a9359cf0-90e4-45e2-9c7b-75b29a93d7fb)

Check if the user exists
>![image](https://github.com/user-attachments/assets/7d0f625b-148c-47a2-bb18-e6ee5c1d517b)

> we'll get the employee role as well ![image](https://github.com/user-attachments/assets/cbd9b0ed-3ad2-4085-b807-0396c843f314)

Login to get jwt
>![image](https://github.com/user-attachments/assets/bf23ab2d-5881-4242-8939-1fd2d8567069)
>![image](https://github.com/user-attachments/assets/22bf95df-9a12-4927-9cdf-a3670d22e7e5)

Copy the token and insert it into the authorization: Bearer Token, we'll use it to authenticate us

Now we'll change our first name and use jwt for authenticate
>![image](https://github.com/user-attachments/assets/9a2767ef-acc6-4218-9032-8c143dfc7be4)
>![image](https://github.com/user-attachments/assets/68f9cdde-5894-4739-afdd-320e9283b7b0)

# Contribute
 If you find any bugs or want to contribute, open a pull request following the [google java formatting](https://github.com/google/google-java-format) and the [Angular Conventional Commit](https://www.conventionalcommits.org/en/v1.0.0-beta.4/). Remember to bump the pom.xml version following the [semantic versioning standards](https://semver.org/)
