# Snippet Saver

* This is a full stack web development project. The purpose of the website is that developers from across the world can
  access the code snippets which assist during coding and building projects. This project follows microservice
  architecture. The backend part will be designed with springboot, and the frontend part will be designed with React +
  Typescript.
* The brief description of the project is it will be a social media of code snippets. Where many users can create their
  snippets, check out, others copy them and assist self-coding.
* If the above idea goes well, then I will implement a likes and comment system. I have a planning to integrate a chat
  system with these. Though this project will be a microservice, it is easy to add more and more service.

> Documentation [Postman](https://documenter.getpostman.com/view/23395461/2s93m7X1zz)

## Contact me

[![facebook](./facebook.png)](https://www.facebook.com/abhisek.mohanty.79069/)
[![linkedin](./linkedin.png)](https://www.linkedin.com/in/abhisek-mohanty-3a2241235/)
[![github](./github.png)](https://github.com/abhisekmohantychinua)

### **SnippetSaver Architecture**

![snippetsaver-architecture](./snippetsaver-architecture-2.png)

* Above is the current architecture of the project which is upgraded.
* These are the previous architectures :
    1. [snippetsaver-architecture-1](./snippetsaver-architecture.png)

### **ORM for Entities**

![snippetsaver-orm](./snippetsaver-orm.png)

#### Problem solved by the above ORM

> While I am trying to Fetch all snippets by userId and Fetch the user by snippetId, it creates a recursion between the
> json responses. To solve this issue, I have added a field `userId` in `Snippet` which may take an unnecessary space, but
> it is far better than storing the reference of `Snippet` entity in `User`.It does the both above things
> with ```Optionl<Snippet> findById(String snippetId)``` and ```List<Snippet> findAllByUserId(String userId)```.

### MongoDB's connectivity with .env file

I have configured mongodb connectivity using only `spring.data.mongodb.uri`
and `spring.data.mongodb.database` which gives
me `org.springframework.data.mongodb.UncategorizedMongoDbException: Exception authenticating MongoCredential{mechanism=SCRAM-SHA-1, userName='abhis', source='admin', password=<hidden>, mechanismProperties=<hidden>}`
.So
to avoid this kind of error, you have to provide username and password explicitly.

```yaml
spring:
  data:
    mongodb:
      uri: ${URL}
      username: ${USERNAME}
      password: ${PASSWORD}
      database: ${DATABASE}
```

Password and Other credentials shouldn't be public, so I have created an `.env` file
and stored my data in a key value pair and added file name to [`gitignore`](./user-service/.gitignore).I have also given
a reference
as [`.env.example`](./user-service/src/main/resources/.env.examples) check that out.

* **important**: Must add the spring dotenv dependency to you `buil.gradle` or `pom.xml`.
* If the dotenv version is older, then we have to write

```yaml
    spring:
      data:
        mongodb:
          uri: ${env.URL}
          username: ${env.USERNAME}
          password: ${env.PASSWORD}
          database: ${env.DATABASE}
```

___

```groovy
dependencies {
    implementation 'me.paulschwarz:spring-dotenv:4.0.0'
}
```

> Check out the GitHub repository and documentation of the above
> extension [spring-dotenv](https://github.com/paulschwarz/spring-dotenv.git)

### How security is implemented

  * I have created a new microservice as [`AUTH-SERVICE`](./auth-service) which basically implements spring-security.I
  have created 3 endpoints `/register`,`/token`,`/validate`
  which does register a user,login a user
  and validates a user respectively.And configured the `SecurityFilterChain` like this:

```java

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("auth/**")
                .permitAll();
        return http.build();
    }
}
```

  Other authentication and authorization are just normal.

  * Now comes the interesting part [`API-GATEWAY`](./api-gateway). I have created A class which
  extends `AbstractGatewayFilterFactory` and added it to the routes as filter I want to authenticate.
```yaml
        - id: USER-SERVICE
          uri: lb://USER-SERVICE
          predicates:
            - Path=/user/**
          filters:
            - AuthFilter
```
  Then created a class as `RouteValidator` and stored the routes as string which I don't want to be filtered.Then as usual the process of retrieving authorization header and validating the token.
  > **Imp** : I am calling the auth service to validate the token using `RestTemplate`.
  After that if anything wrong occurs I handle the status and filter the exchange.


#### Disadvantage
  1. For validating a user it calls the authservice which forms a cyclic loop.At first I have done the communication beetween these services using `FeignClient` but it gave me the bellow error. For that I have used rest template and no `LoadBalanced`.
  ```
Description:

The dependencies of some of the beans in the application context form a cycle:

┌─────┐
|  authFilter defined in file [D:\-----\----\----\snippetsaver-microservices\api-gateway\build\classes\java\main\dev\abhisek\apigateway\filter\AuthFilter.class]
↑     ↓
|  dev.abhisek.apigateway.service.AuthClient
↑     ↓
|  corsGatewayFilterApplicationListener defined in class path resource [org/springframework/cloud/gateway/config/GatewayAutoConfiguration.class]
↑     ↓
|  routePredicateHandlerMapping defined in class path resource [org/springframework/cloud/gateway/config/GatewayAutoConfiguration.class]
↑     ↓
|  cachedCompositeRouteLocator defined in class path resource [org/springframework/cloud/gateway/config/GatewayAutoConfiguration.class]
↑     ↓
|  routeDefinitionRouteLocator defined in class path resource [org/springframework/cloud/gateway/config/GatewayAutoConfiguration.class]
└─────┘


Action:

Relying upon circular references is discouraged and they are prohibited by default. Update your application to remove the dependency cycle between beans. As a last resort, it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true.
  ```

  2. And for every time accessing any end point through the gateway, It calls the filter and filter calls the auth-service which is not a good approach to do.And It also makes the response slower.

  > So feel free to contribute to this security implementation. I am going to host this website, So if your approach is good, I will feature you on my LinkedIn.