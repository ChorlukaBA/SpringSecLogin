# Introduction
The goal of our project is to create a secure login system for users of a Product Store.
Only logged users can get the products and their details, insert new products, update or delete existing products.
There are 2 microservices that interact with each other: our Store service and the DB (MySQL).
## Enabling Technologies
1. **Spring Boot**
2. **Spring Data JPA**
3. **Spring Validation**
4. **Spring Security + JWT Token**
5. **MySQL**
6. **Mapstruct**
7. **Lombok**
8. **Swagger**

Each of these are well known technologies in the Java ecosystem and are widely used in the industry.
We already explained the purpose of each of these technologies in the General Documentation, so here we will focus on how we used them in our project.
# Usage
1. Use maven to build the project, from the "lm-app" directory:
```bash
mvn clean install
```
2. Build the services with docker-compose:
```bash
docker compose build
```
3. Run the services with docker-compose:
```bash
docker compose up
```
4. Access the Swagger UI at http://localhost:8080/swagger-ui.html
![](https://github.com/ChorlukaBA/SpringSecLogin/blob/main/images/SwaggerUI.png)
5. Use the Swagger UI or PostMan to test the REST endpoints.


# Project Structure
The project is divided into several packages:
1. **configuration**: Contains the configuration classes for Spring Security and Swagger.
2. **controller**: Contains the REST controllers. Each controller is responsible for a specific entity.
3. **exceptions**: Contains the custom exceptions. Used for handling exceptions in the application.
4. **model**: Contains the JPA entities which are mapped to the database tables.
5. **repository**: Contains the JPA repositories which are used for accessing the database.
6. **security**: Contains the classes for Spring Security configuration.
7. **service**: Contains the service classes which are used for business logic.

# 1. Configuration
In the configuration package, we have three classes: PasswordEncoderConfiguration, SecurityConfiguration and SwaggerConfiguration
## 1.1 PasswordEncoderConfiguration
Here we have the configuration for the password encoder. We use the BCryptPasswordEncoder which is a strong hashing function.
## 1.2 SecurityConfiguration
Allows us to configure which endpoints are secured and which are not.
```java
// We configure the security filter chain to handle incoming requests.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception
    {
        return http
                .cors()
                .and()
                .csrf()
                .disable()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class) // Here we check if the request is preceded by a JWT token.
                .authorizeRequests()
                .antMatchers("/register", "/health", "/login", "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/actuator/**")     // We permit all requests to these endpoints.
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(unauthorizedHandler)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .build();
    }
```
## 1.3 SwaggerConfiguration
Used for configuring Swagger UI. Which is a tool for documenting APIs.
Once the application is running, you can access the Swagger UI at http://localhost:8080/swagger-ui.html and see the documentation for the REST endpoints.

# 2. Controller
In the controller package, we have four classes: ProductController, HealthController, LoginController and RegistrationController.
Each of these REST controller is responsible for a specific entity, handling the recognized HTTP requests.

## 2.1 ProductController
This controller is responsible for handling the requests related to the Product entity.
Swagger documentation for the ProductController:
![](https://github.com/ChorlukaBA/SpringSecLogin/blob/main/images/ProductController.png)

## 2.2 HealthController
This controller is responsible for handling the requests related to the Health entity.
If you send a GET request to the /health endpoint, you will get a response with the status of the application.

## 2.3 LoginController
This controller is responsible for handling the requests related to the Login entity.
If you send a POST request to the /login endpoint with the correct credentials (username and password of a registered user), you will get a response with a JWT token.

## 2.4 RegistrationController
This controller is responsible for handling the requests related to the Registration entity.
The user can register by sending a POST request to the /register endpoint with the required information (name, username, email, password).

# 3. Exceptions
In the exceptions package, we deal with the exceptions that can occur in the application.

# 4. Model
In the model package, we have the JPA entities which are mapped to the database tables.
The entities are: Product, User, UserRole.
Each of these has its attributes and constraints.
Here we also use the Lombok library to generate the getters, setters, constructors, etc.

# 5. Repository
In the repository package, we have the JPA repositories which are used for accessing the database.
The repositories are: ProductRepository, UserRepository.
Each of these extends the JpaRepository interface which provides the basic CRUD operations.
UserRepository provides also custom methods for finding a user by username or check if it exists (by username or email).

# 6. Security
The most important part of the project is the security package.
Here we have the classes for Spring Security management and JWT token generation.
We have different subpackages:
1. **dto**: Contains the Data Transfer Objects which are used for transferring data between the layers.
2. **jwt**: Contains the classes for JWT token generation and validation.
3. **mapper**: Contains the classes for mapping the entities to DTOs and vice versa.
4. **service**: Contains the classes for user authentication and user details.
5. **utils**: Provides utility classes and constants for the security package.

## 6.1 DTO
Contains the Data Transfer Objects which are used for transferring data between the layers.
We have 5 DTOs: AuthenticatedUserDTO, LoginRequest, LoginResponse, RegistrationRequest, RegistrationResponse.

### 6.1.1 AuthenticatedUserDTO
This DTO is used for transferring the authenticated user details (name, username, email, roles).
We use Lombok to generate the getters, setters, constructors, etc.
### 6.1.2 LoginRequest
This DTO is used for transferring the login request data (username, password).
Here we use Lombok to generate the getters, setters, constructors, etc. but also Spring Validation to validate the fields.
```java
public class LoginRequest
{
  @NotEmpty(message = "{Username cannot be empty}")
  private String username;

  @NotEmpty(message = "{Password cannot be empty}")
  private String password;
}
```
### 6.1.3 LoginResponse
This DTO is used for transferring the login response data (JWT token), generated after a successful login.
### 6.1.4 RegistrationRequest
This DTO is used for transferring the registration request data (name, username, email, password).
Here we use Lombok to generate the getters, setters, constructors, etc. but also Spring Validation to validate the fields.
### 6.1.5 RegistrationResponse
This DTO is used for transferring the registration response data (message), generated after a successful registration.

## 6.2 JWT
Contains the classes for JWT token generation and validation.
JWT is a JSON Web Token which is used for securely transmitting information between parties as a JSON object.
We generate a JWT token when the user logs in and we validate it when the user sends a request to a secured endpoint.
It has an expiration time and a secret key which is used for signing the token.

We have 5 classes in this package:

### 6.2.1 JwtAuthenticationEntryPoint
Here we handle the exceptions that occur when an unauthenticated user tries to access a secured endpoint.
We return a 401 Unauthorized response.

### 6.2.2 JwtAuthenticationFilter
This class extends the OncePerRequestFilter class and is used for checking if the request is preceded by a JWT token in the header.
If the token is valid, we set the authentication in the SecurityContext.
Since it extends the OncePerRequestFilter class, the doFilterInternal method is called only once per request.
```java
@Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws ServletException, IOException
    {
        final String requestURI = req.getRequestURI();              // Get the request URI
        // If the request URI is the login or registration endpoint, we permit all requests.
        if (requestURI.contains(SecurityConstants.LOGIN_REQUEST_URI) || requestURI.contains(SecurityConstants.REGISTRATION_REQUEST_URI))
        {
            chain.doFilter(req, res);                        // Continue with the filter chain
            return;
        }
        
        // Get the JWT token from the header
        final String header = req.getHeader(SecurityConstants.HEADER_STRING);
        String username = null;
        String authToken = null;
        // If the header is not null and starts with the token prefix (Bearer), we extract the token
        if (Objects.nonNull(header) && header.startsWith(SecurityConstants.TOKEN_PREFIX))
        {
            authToken = header.replace(SecurityConstants.TOKEN_PREFIX, StringUtils.EMPTY);  // Extract the token
            try
            {
                username = jwtTokenManager.getUsernameFromToken(authToken);             // Get the username from the token
            }
            catch (Exception e)
            {
                log.error("Authentication Exception: {}", e.getMessage());
            }
        }
        
        // Get the current security context
        final SecurityContext securityContext = SecurityContextHolder.getContext();

        // If the username is not null and the authentication is null, we load the user details and set the authentication
        if (Objects.nonNull(username) && Objects.isNull(securityContext.getAuthentication()))
        {
            final UserDetails userDetails = userDetailsService.loadUserByUsername(username);    // Load the user details
            // If the token is valid, we set the authentication
            if (jwtTokenManager.validateToken(authToken, userDetails.getUsername()))
            {
                final UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(req));
                log.info("Authentication successful. Logged in username: {}", username);
                securityContext.setAuthentication(authentication);
            }
        }
        chain.doFilter(req, res);                 // Continue with the filter chain
    }
```

### 6.2.3 JwtProperties
This class is used to store the properties for the JWT token (issuer, secret key, expiration time).
We use the @ConfigurationProperties annotation to bind the properties from the application.yml file.
Worth mentioning that we use the annotation ``` @ConfigurationProperties(prefix = "jwt")``` to bind the properties from the application.yml file.

### 6.2.4 JwtTokenManager
This class is used to generate a JWT token, extract the username from the token, validate the token and check if it is expired.
We use HMAC256 algorithm to sign the token with the secret key.
In the generateToken method, we use the JWT create method to create a token with the username, expiration time and secret key.
```java
public String generateToken(User user)
    {
        final String username = user.getUsername();
        final UserRole userRole = user.getUserRole();

        return JWT.create()
                .withSubject(username)
                .withIssuer(jwtProperties.getIssuer())              // Issuer is the application that created the token
                .withClaim("role", userRole.name())
                .withIssuedAt(new Date())                           // Issued at is the time when the token was created
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtProperties.getExpirationMinute() * 60 * 1000))      // Expiration time is set in minutes
                .sign(Algorithm.HMAC256(jwtProperties.getSecretKey().getBytes()));                                         // JWT token is signed with the secret key using HMAC256 algorithm
    }
```
In the validateToken method, we check if the username from the token is the same as the username from the user details(the one authenticated) and if the token is not expired.
```java
public boolean validateToken(String token, String authenticatedUsername)
    {
        final String UsernameFromToken = getUsernameFromToken(token);               // Get the username from the token, decoding it using DecodingJWT and the secret key (HMAC256 algorithm)

        final boolean equalsUsername = UsernameFromToken.equals(authenticatedUsername);     // Check if the username from the token is the same as the authenticated username
        final boolean tokenExpired = isTokenExpired(token);                                 // Check if the token is expired, comparing the expiration time with the current time

        return equalsUsername && !tokenExpired;
    }
```
Of course, methods used for decoding and expiration-check are also implemented in this class.
Feel free to check the code for more details.

### 6.2.5 JwtTokenService
This class is a service class that provides the logic for generating a JWT token for a user when he logs in.
It uses AuthenticationManager to authenticate the user and the JwtTokenManager to generate the token.
It also uses UserService to find the user by username and UserMapper to map the user entity to the AuthenticatedUserDTO.
It returns a LoginResponse with the JWT token.
```java
public LoginResponse getLoginResponse(LoginRequest loginRequest)
    {
        final String username = loginRequest.getUsername();
        final String password = loginRequest.getPassword();

        final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        final AuthenticatedUserDto authenticatedUserDto = userService.findAuthenticatedUserByUsername(username);

        final User user = UserMapper.INSTANCE.convertToUser(authenticatedUserDto);
        final String token = jwtTokenManager.generateToken(user);

        log.info("{} has successfully logged in!", user.getUsername());

        return new LoginResponse(token);
    }
```

## 6.3 Mapper
Here we have only one class: UserMapper.
It provides the logic to convert Entities to DTOs and vice versa.
We use the Mapstruct library to generate the implementation of the mapper interface.
```java
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)              // Here we define the unmappedTargetPolicy to ignore the unmapped properties
public interface UserMapper
{
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User convertToUser(RegistrationRequest registrationRequest);

    AuthenticatedUserDto convertToAuthenticatedUserDto(User user);

    User convertToUser(AuthenticatedUserDto authenticatedUserDto);
}
```

## 6.4 Service
Service classes which are used for user authentication and user details handling.

### 6.4.1 UserDetailsServiceImpl
Service class that provides the logic to load the user by username.
It uses UserService to find the authenticated user by username.
It returns a UserDetails object with the authenticated user details.
It throws a UsernameNotFoundException if the user is not found.
```java
@Override
    public UserDetails loadUserByUsername(String username)
    {
        final AuthenticatedUserDto authenticatedUser = userService.findAuthenticatedUserByUsername(username);           // Here we find the authenticated user by username

        // If the authenticated user is null, throw a UsernameNotFoundException
        if (Objects.isNull(authenticatedUser))
        {
            throw new UsernameNotFoundException(USERNAME_OR_PASSWORD_INVALID);
        }

        // If the authenticated user is not null, return a new UserDetails object with the authenticated user details
        final String authenticatedUsername = authenticatedUser.getUsername();
        final String authenticatedPassword = authenticatedUser.getPassword();
        final UserRole userRole = authenticatedUser.getUserRole();
        final SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(userRole.name());

        return new User(authenticatedUsername, authenticatedPassword, Collections.singletonList(grantedAuthority));
    }
```

### 6.4.2 UserService
Interface that provides the methods for finding the authenticated user by username, registering a new user and finding an authenticated user by username.
It is implemented in the UserServiceImpl class.

### 6.4.3 UserServiceImpl
This service class provides the implementation for the three methods descripted above.
We use UserRepository to save the user in the DB and to find the user by username.
We use BCryptPasswordEncoder to encode the user password before saving it in the DB.
We use UserValidationService to validate the user before saving it in the DB.
Furthermore, we use UserMapper to map the User entity to the AuthenticatedUserDTO, and vice versa.
The core of the class is the registration method which is used for registering a new user.
```java
@Override
    public RegistrationResponse registration(RegistrationRequest registrationRequest)
    {
        userValidationService.validateUser(registrationRequest);

        final User user = UserMapper.INSTANCE.convertToUser(registrationRequest);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUserRole(UserRole.USER);

        userRepository.save(user);

        final String username = registrationRequest.getUsername();
        final String registrationSuccessMessage = "Registration successful";
        log.info("{} registered successfully!", username);

        return new RegistrationResponse(registrationSuccessMessage);
    }
```

## 6.5 Utils
Provides utility classes and constants for the security package.
i.e. the expiration time for the JWT token, the secret key, the issuer, the header string, the token prefix, the login and registration request URIs.

# 7. Service
This package contains the service classes which are used for business logic.
We have two service classes: ProductServiceImpl(which implements ProductService) and UserValidationService.

## 7.1 ProductService
This interface provides the methods for CRUD operations on the Product entity.
It is implemented in the ProductServiceImpl class.

## 7.2 ProductServiceImpl
We use ProductRepository to access the database and perform CRUD operations on the Product entity.

## 7.3 UserValidationService
This service class provides the logic for validating the user before saving it in the DB.
It checks if the email and username already exist in the DB and throws an exception if they do. This is done to prevent duplicate entries in the DB.
Checks are done in private methods called from UserServiceImpl class in the registration phase.

# Application.yml
To conclude, in resources folder we have our application.yml file where we store the properties for the JWT token (issuer, secret key, expiration time), the database connection properties, logging and swagger properties, ecc.
