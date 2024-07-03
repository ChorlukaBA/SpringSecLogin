## Introduction to Spring framework

### What is Spring ?
The Spring framework provides comprehensive infrastructure support for developing Java applications.
It’s packed with some nice features like Dependency Injection, and out of the box modules like:
- **Spring JDBC**
- **Spring MVC**
- **Spring Security**
- **Spring AOP**
- **Spring Test**

These modules can drastically reduce the development time of an application.

### Advantages of Spring:
- **Simplifies development**: Spring provides a lightweight and modular approach to building Java applications, making development faster and more efficient.
- **Dependency Injection**: Spring's dependency injection feature allows for loose coupling between components, making it easier to manage and test dependencies.
- **AOP Support**: Spring's Aspect-Oriented Programming (AOP) support enables separation of cross-cutting concerns, such as logging and transaction management, from the core business logic.
- **Integration with other frameworks**: Spring seamlessly integrates with other popular frameworks and technologies, such as Hibernate, JPA, and RESTful APIs.
- **Robust ecosystem**: Spring has a large and active community, which means there are plenty of resources, tutorials, and third-party libraries available for support.

### Disadvantages of Spring:
- **Steep learning curve**: Spring is a comprehensive framework with many features, which can make it overwhelming for beginners.
- **Configuration complexity**: Spring's configuration can be complex, especially when dealing with advanced scenarios or integrating with other frameworks.
- **Runtime overhead**: Spring's runtime overhead can be higher compared to lightweight frameworks, although the performance impact is usually negligible for most applications.
- **XML configuration**: In older versions of Spring, XML configuration was the primary way to configure the framework, which can be verbose and error-prone.

## Introduction to Spring Boot

### What is Spring Boot?
Spring Boot is a framework that simplifies the development of Spring applications by providing a convention-over-configuration approach.  
It is basically an extension of the Spring framework, which eliminates the boilerplate configurations required for setting up a Spring application and allows developers to quickly create standalone, production-ready applications.

### Features of Spring Boot:
1. **Auto-configuration**: Spring Boot automatically configures the application based on the dependencies present in the classpath, reducing the need for manual configuration.
2. **Embedded server**: Spring Boot includes an embedded server, such as Tomcat or Jetty, allowing applications to be easily deployed as standalone executables.
3. **Starter dependencies**: Spring Boot provides a set of starter dependencies that include all the necessary libraries for specific functionalities, such as web development, database access, and security.
4. **Actuator**: Spring Boot Actuator provides production-ready features for monitoring and managing applications, including health checks, metrics, and endpoint exposure.
5. **Spring Boot CLI**: Spring Boot Command Line Interface (CLI) allows developers to quickly prototype and develop Spring Boot applications using a command-line interface.

These features make Spring Boot a popular choice for building microservices and cloud-native applications.

## Introduction to Spring Security

### What is Spring Security?
Spring Security is a powerful and highly customizable security framework that is built using the Spring framework in Java. 
It provides a comprehensive set of features to secure your application, including authentication, authorization, and protection against common security vulnerabilities.

### Features of Spring Security:
1. **Authentication**: Spring Security allows you to easily implement various authentication mechanisms, such as username/password, token-based, or social login. It supports integration with popular authentication providers like LDAP, OAuth, and OpenID Connect.
2. **Authorization**: With Spring Security, you can define fine-grained access control rules based on roles, permissions, or custom expressions. It provides support for both method-level and URL-level security.
3. **Secure Communication**: Spring Security helps you secure your application's communication by providing features like HTTPS, CSRF protection, and secure cookie handling.
4. **Security Filters**: Spring Security uses a chain of filters to process and enforce security rules. These filters handle tasks like authentication, authorization, and session management.
5. **Integration with Spring Framework**: Spring Security seamlessly integrates with the Spring ecosystem, allowing you to leverage other Spring features like dependency injection, AOP, and MVC.
6. **Security Auditing**: Spring Security provides auditing capabilities to track and log security-related events, such as login attempts, access denied, or user management actions.
7. **Password Encoding**: Spring Security offers built-in support for password encoding and hashing algorithms, ensuring that user passwords are securely stored.
8. **Remember Me**: Spring Security includes a "Remember Me" feature that allows users to stay logged in across sessions, using persistent tokens.
9. **Multi-factor Authentication**: Spring Security supports multi-factor authentication, allowing you to add an extra layer of security to your application.
10. **Integration with External Security Providers**: Spring Security can integrate with external security providers like Active Directory, SAML, or OAuth, enabling seamless integration with existing authentication and authorization systems.

### Spring Security Modules
- Core: It includes Spring Security's core classes and interfaces related to authentication and application access control.
- Remoting: It is used for handling the Spring Remoting application and contains the necessary classes.
- Aspect: It is used to include Aspect-Oriented Programming (AOP) support within Spring Security.
- Config: It is used to configure the Spring Security application by using XML and Java.
- Crypto: This module contains classes and interfaces for cryptography support.
- Data: It is used to integrate Spring Security with Spring Data.
- Messaging: It is helpful to implement messaging in the application.
- OAuth2: It includes classes and interface for OAuth 2.x within Spring Security:
- OpenID: It provides support to integrate OpenID web-authentication.
- CAS: CAS (Central Authentication Service) client integration.
- TagLib: It contains several tag libraries regarding Spring Security.
- Test: It adds testing support in the Spring Security.
- Web: It contains web security code, such as filters and Servlet API dependencies.

### Enabling Method Security 
As most open source projects, Spring Security deploys its dependencies as Maven artifacts.
Open the pom.xml file and add the following maven dependency:
```xml
<dependencies>
	<!-- ... other dependency elements ... -->
	<dependency>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-security</artifactId>
	</dependency>
</dependencies>
```
This dependency provides the necessary libraries for integrating Spring Security into your Spring Boot application. It includes the core security features and dependencies required for authentication, authorization, and protection against common security vulnerabilities.

Since Spring Boot provides a Maven BOM to manage dependency versions, you do not need to specify a version. If you wish to override the Spring Security version, you can do so by providing a Maven property:
```xml
<properties>
	<!-- ... -->
	<spring-security.version>6.3.1</spring-security.version>
</properties>
```
## Spring Security Authentication 

### Username/Password Authentication
Spring Security provides comprehensive support for authentication. 
Authentication is how we verify the identity of who is trying to access a particular resource. 
A common way to authenticate users is by requiring the user to enter a username and password. 
Once authentication is performed we know the identity and can perform authorization.

One of the most common ways to authenticate a user is by validating a username and password. Spring Security provides comprehensive support for authenticating with a username and password.

You can configure username and password authentication using the following:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.anyRequest().authenticated()
			)
			.httpBasic(Customizer.withDefaults())
			.formLogin(Customizer.withDefaults());

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

}
```
The preceding configuration automatically registers an in-memory UserDetailsService with the SecurityFilterChain, registers the DaoAuthenticationProvider with the default AuthenticationManager, and enables Form Login and HTTP Basic authentication.

- What is **`In-Memory Authentication`** ?
Spring Security’s InMemoryUserDetailsManager implements UserDetailsService to provide support for username/password based authentication that is stored in memory. InMemoryUserDetailsManager provides management of UserDetails by implementing the UserDetailsManager interface. UserDetails-based authentication is used by Spring Security when it is configured to accept a username and password for authentication.

- What is **`DaoAuthenticationProvider`** ?
DaoAuthenticationProvider is an AuthenticationProvider implementation that uses a UserDetailsService and PasswordEncoder to authenticate a username and password.

- What is **`AuthenticationManager`** ?
AuthenticationManager is the API that defines how Spring Security’s Filters perform authentication.
A fairly common requirement is publishing an AuthenticationManager bean to allow for custom authentication, such as in a **@Service** or Spring MVC **@Controller**.
For example, you may want to authenticate users via a REST API instead of using Form Login.

You can publish such an **AuthenticationManager** for custom authentication scenarios using the following configuration:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/login").permitAll()
				.anyRequest().authenticated()
			);

		return http.build();
	}

	@Bean
	public AuthenticationManager authenticationManager(
			UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService);
		authenticationProvider.setPasswordEncoder(passwordEncoder);

		return new ProviderManager(authenticationProvider);
	}

	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails userDetails = User.withDefaultPasswordEncoder()
			.username("user")
			.password("password")
			.roles("USER")
			.build();

		return new InMemoryUserDetailsManager(userDetails);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
```
With the preceding configuration in place, you can create a @RestController that uses the AuthenticationManager as follows:
```java
@RestController
public class LoginController {

	private final AuthenticationManager authenticationManager;

	public LoginController(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
		Authentication authenticationRequest =
			UsernamePasswordAuthenticationToken.unauthenticated(loginRequest.username(), loginRequest.password());
		Authentication authenticationResponse =
			this.authenticationManager.authenticate(authenticationRequest);
		// ...
	}

	public record LoginRequest(String username, String password) {
	}

}
```
### Password Storage: BCryptPasswordEncoder
Spring Security’s PasswordEncoder interface is used to perform a one-way transformation of a password to let the password be stored securely. 
Given PasswordEncoder is a one-way transformation, it is not useful when the password transformation needs to be two-way (such as storing credentials used to authenticate to a database). 
Typically, PasswordEncoder is used for storing a password that needs to be compared to a user-provided password at the time of authentication. 

The **`BCryptPasswordEncoder`** implementation uses the widely supported **bcrypt algorithm** to hash the passwords. To make it more resistant to password cracking, bcrypt is deliberately slow. Like other adaptive one-way functions, it should be tuned to take about 1 second to verify a password on your system.
The default implementation of BCryptPasswordEncoder uses strength 10 as mentioned in the Javadoc of BCryptPasswordEncoder. The recommended strength value for bcrypt is typically between 10 and 31.
In this context, the "strength" refers to the computational cost or complexity of the bcrypt algorithm used for password hashing, so the strength value determines how many iterations of the bcrypt algorithm will be performed to hash the password. 
The higher the strength value, the more iterations will be performed, resulting in a slower hashing process. This is intentional to make it more difficult for attackers to crack the hashed passwords through brute-force or dictionary attacks.

It's important to note that increasing the strength value will also increase the time it takes to hash and verify passwords. Therefore, it's a trade-off between security and performance. You should choose a strength value that provides an acceptable level of security while still maintaining reasonable performance for your specific application.
```java
// Create an encoder with strength 16
BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16);
String result = encoder.encode("myPassword");
assertTrue(encoder.matches("myPassword", result));
```
- Which are the advantages of using bcrypt algorithm ?

1. **Strong Security**: The bcrypt algorithm is designed to be highly secure and resistant to various types of attacks, including brute-force and dictionary attacks. It uses a combination of salt and multiple iterations to hash passwords, making it extremely difficult for attackers to reverse-engineer the original password.

2. **Adaptive Hashing**: Bcrypt is an adaptive hashing algorithm, which means it can be easily adjusted to increase the computational cost of hashing. This allows you to increase the strength of the algorithm over time as computing power advances, ensuring that your password hashes remain secure even in the face of evolving attack techniques.

3. **Slow Hashing**: Bcrypt intentionally introduces a significant delay in the hashing process, making it computationally expensive for attackers to crack passwords. This helps to mitigate the impact of brute-force attacks by slowing down the rate at which an attacker can try different password combinations.

4. **Salted Hashes**: Bcrypt automatically generates and includes a random salt value as part of the password hash. This salt adds an extra layer of security by ensuring that even if two users have the same password, their hashes will be different. This prevents attackers from using precomputed tables (rainbow tables) to quickly crack multiple passwords.

5. **Widely Supported**: The bcrypt algorithm is widely supported across different programming languages and platforms, making it a reliable choice for password hashing in various environments. This ensures that you can easily integrate bcrypt into your application regardless of the technology stack you are using.

6. **Future-Proof**: Bcrypt is designed to be future-proof, meaning that even if new vulnerabilities or weaknesses are discovered in the algorithm, it can be easily upgraded or replaced with a more secure alternative. This helps to ensure that your password hashes remain secure in the long term.


## Spring Security Authorization
Having established how users will authenticate, you also need to configure your application’s authorization rules. Authorization is determining who is allowed to access a particular resource. 
Spring Security provides defense in depth by allowing for request based authorization and method based authorization.

You should consider attaching authorization rules to request URIs and methods to begin. In either case, you can listen and react to authorization events that each authorization check publishes.

### Authorities: GrantedAuthority and SimpleGrantedAuthority

Authentication discusses how all Authentication implementations store a list of GrantedAuthority objects. These represent the authorities that have been granted to the principal. 
The **`GrantedAuthority`** objects are inserted into the Authentication object by the AuthenticationManager and are later read by AccessDecisionManager instances when making authorization decisions.

The GrantedAuthority interface has only one method:
```java
String getAuthority();
```
This method is used by an **`AuthorizationManager`** instance to obtain a precise String representation of the GrantedAuthority. By returning a representation as a String, a GrantedAuthority can be easily "read" by most AuthorizationManager implementations.
An example of a complex GrantedAuthority would be an implementation that stores a list of operations and authority thresholds that apply to different customer account numbers.

Representing this complex GrantedAuthority as a String would be quite difficult. As a result, the getAuthority() method should return null. This indicates to any AuthorizationManager that it needs to support the specific GrantedAuthority implementation to understand its contents.

Spring Security includes one concrete GrantedAuthority implementation: **`SimpleGrantedAuthority`**
This implementation lets any user-specified String be converted into a GrantedAuthority. All AuthenticationProvider instances included with the security architecture use SimpleGrantedAuthority to populate the Authentication object.

By default, role-based authorization rules include ROLE_ as a prefix. This means that if there is an authorization rule that requires a security context to have a role of "USER", Spring Security will by default look for a GrantedAuthority getAuthority that returns "ROLE_USER".

It's possible to configure the authorization rules to use a different prefix by exposing a GrantedAuthorityDefaults bean, like so:
```java
@Bean
static GrantedAuthorityDefaults grantedAuthorityDefaults() {
	return new GrantedAuthorityDefaults("MYPREFIX_");
}
```
### Authorize HttpRequests
Spring Security allows you to model your authorization at the request level. For example, with Spring Security you can say that all pages under /admin require one authority while all other pages simply require authentication.

By default, Spring Security requires that every request be authenticated. That said, any time you use an HttpSecurity instance, it’s necessary to declare your authorization rules.

Whenever you have an HttpSecurity instance, you should at least do:
```java
http
    .authorizeHttpRequests((authorize) -> authorize
        .anyRequest().authenticated()
    )
```
This tells Spring Security that any endpoint in your application requires that the security context at a minimum be authenticated in order to allow it.

Once a request is matched, you can authorize it in several ways already seen like permitAll, denyAll, and hasAuthority.
As a quick summary, here are the authorization rules:
- **`permitAll`** - The request requires no authorization and is a public endpoint; note that in this case, the Authentication is never retrieved from the session
- **`denyAll`** - The request is not allowed under any circumstances; note that in this case, the Authentication is never retrieved from the session
- **`hasAuthority`** - The request requires that the Authentication have a GrantedAuthority that matches the given value
- **`hasRole`** - A shortcut for hasAuthority that prefixes ROLE_ or whatever is configured as the default prefix
- **`hasAnyAuthority`** - The request requires that the Authentication have a GrantedAuthority that matches any of the given values
- **`hasAnyRole`** - A shortcut for hasAnyAuthority that prefixes ROLE_ or whatever is configured as the default prefix
- **`access`** - The request uses this custom AuthorizationManager to determine access

For example:
```java
import static jakarta.servlet.DispatcherType.*;

import static org.springframework.security.authorization.AuthorizationManagers.allOf;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasAuthority;
import static org.springframework.security.authorization.AuthorityAuthorizationManager.hasRole;

@Bean
SecurityFilterChain web(HttpSecurity http) throws Exception {
	http
		// ...
		.authorizeHttpRequests(authorize -> authorize                                  (1)
            .dispatcherTypeMatchers(FORWARD, ERROR).permitAll() (2)
			.requestMatchers("/static/**", "/signup", "/about").permitAll()         (3)
			.requestMatchers("/admin/**").hasRole("ADMIN")                             (4)
			.requestMatchers("/db/**").access(allOf(hasAuthority("db"), hasRole("ADMIN")))   (5)
			.anyRequest().denyAll()                                                (6)
		);

	return http.build();
}
```
1. There are multiple authorization rules specified. Each rule is considered in the order they were declared.
2. Dispatches FORWARD and ERROR are permitted to allow Spring MVC to render views and Spring Boot to render errors
3. We specified multiple URL patterns that any user can access. Specifically, any user can access a request if the URL starts with "/static/", equals "/signup", or equals "/about".
4. Any URL that starts with "/admin/" will be restricted to users who have the role "ROLE_ADMIN". You will notice that since we are invoking the hasRole method we do not need to specify the "ROLE_" prefix.
5. Any URL that starts with "/db/" requires the user to have both been granted the "db" permission as well as be a "ROLE_ADMIN". You will notice that since we are using the hasRole expression we do not need to specify the "ROLE_" prefix.
6. Any URL that has not already been matched on is denied access. This is a good strategy if you do not want to accidentally forget to update your authorization rules.

### Method Security 
In addition to modeling authorization at the request level, Spring Security also supports modeling at the method level.
You can activate it in your application by annotating any **@Configuration** class with **`@EnableMethodSecurity`**.
Then, you are immediately able to annotate any Spring-managed class or method with **`@PreAuthorize`**, **`@PostAuthorize`**, **`@PreFilter`**, and **`@PostFilter`** to authorize method invocations, including the input parameters and return values.
Note that Spring Boot Starter Security does not activate method-level authorization by default.

Spring Security’s method authorization support is handy for:
- Extracting fine-grained authorization logic; for example, when the method parameters and return values contribute to the authorization decision.
- Enforcing security at the service layer
- Stylistically favoring annotation-based over HttpSecurity-based configuration

#### Authorizing Method Invocation with @PreAuthorize
When Method Security is active, you can annotate a method with the @PreAuthorize annotation like so:
```java
@Component
public class BankService {
	@PreAuthorize("hasRole('ADMIN')")
	public Account readAccount(Long id) {
        // ... is only invoked if the `Authentication` has the `ROLE_ADMIN` authority
	}
}
```
This is meant to indicate that the method can only be invoked if the provided expression hasRole('ADMIN') passes.

#### Authorization Method Results with @PostAuthorize
When Method Security is active, you can annotate a method with the @PostAuthorize annotation like so:
```java
@Component
public class BankService {
	@PostAuthorize("returnObject.owner == authentication.name")
	public Account readAccount(Long id) {
        // ... is only returned if the `Account` belongs to the logged in user
	}
}
```
This is meant to indicate that the method can only return the value if the provided expression returnObject.owner == authentication.name passes. returnObject represents the Account object to be returned.

#### Filtering Method Parameters with @PreFilter
When Method Security is active, you can annotate a method with the @PreFilter annotation like so:
```java
@Component
public class BankService {
	@PreFilter("filterObject.owner == authentication.name")
	public Collection<Account> updateAccounts(Account... accounts) {
        // ... `accounts` will only contain the accounts owned by the logged-in user
        return updated;
	}
}
```
This is meant to filter out any values from accounts where the expression filterObject.owner == authentication.name fails. filterObject represents each account in accounts and is used to test each account.

#### Filtering Method Results with @PostFilter
When Method Security is active, you can annotate a method with the @PostFilter annotation like so:
```java
@Component
public class BankService {
	@PostFilter("filterObject.owner == authentication.name")
	public Collection<Account> readAccounts(String... ids) {
        // ... the return value will be filtered to only contain the accounts owned by the logged-in user
        return accounts;
	}
}
```
This is meant to filter out any values from the return value where the expression filterObject.owner == authentication.name fails. filterObject represents each account in accounts and is used to test each account.

#### Authorizing Method Invocation with @Secured
@Secured is a legacy option for authorizing invocations. @PreAuthorize supercedes it and is recommended instead.
To use the @Secured annotation, you should first change your Method Security declaration to enable it like so:
@EnableMethodSecurity(securedEnabled = true)
This will cause Spring Security to publish the corresponding method interceptor that authorizes methods, classes, and interfaces annotated with @Secured.

## Protection Against Exploits
### Cross Site Request Forgery (CSRF)
Cross-Site Request Forgery (CSRF) is a type of attack where an attacker tricks a user into performing unwanted actions on a website without their knowledge or consent. This can lead to unauthorized changes, data theft, or other malicious activities.
Spring Security protects against CSRF attacks by default for unsafe HTTP methods, such as a POST request, so no additional code is necessary.
In this way CSRF protection adds an additional layer of security by ensuring that requests made to your application are legitimate and originated from your own website.
You can specify the default configuration explicitly using the following:
```java
@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			// ...
			.csrf(Customizer.withDefaults());
		return http.build();
	}
}
```
Here's how CSRF protection works:
- When a user visits your website, a unique token is generated and associated with their session.
This token is then embedded in forms or added to request headers whenever the user performs an action that modifies data on your website (e.g., submitting a form).
- When the user submits the form or sends a request, the server checks if the token matches the one associated with the user's session. If they match, the request is considered legitimate and processed. If they don't match, the request is rejected as a potential CSRF attack.

In the code snippet, Spring Security's CSRF protection is enabled by calling the .csrf(Customizer.withDefaults()) method. This configures the default CSRF protection settings for unsafe HTTP methods like POST requests. Spring Security automatically generates and validates CSRF tokens for you, so you don't need to write additional code to handle CSRF protection.
It's important to note that CSRF protection should be used in conjunction with other security measures, such as authentication and authorization, to provide comprehensive protection for applications.

## Spring Security + JWT Token: JWT authentication

### JWT's goal and structure 

JSON Web Token (JWT) is an open standard that defines a compact and self-contained way for securely transmitting information between parties as a JSON object. 
This information can be verified and trusted because it is digitally signed.
JWTs can be signed using a secret (with the HMAC algorithm) or a public/private key pair using RSA or ECDSA.

Here are some scenarios where JSON Web Tokens are useful:
- Authorization: This is the most common scenario for using JWT. Once the user is logged in, each subsequent request will include the JWT, allowing the user to access routes, services, and resources that are permitted with that token. 

- Information Exchange: JSON Web Tokens are a good way of securely transmitting information between parties. Because JWTs can be signed—for example, using public/private key pairs—you can be sure the senders are who they say they are. Additionally, as the signature is calculated using the header and the payload, you can also verify that the content hasn't been tampered with.

In its compact form, JSON Web Tokens consist of three parts separated by dots (.), which are:
- Header
- Payload
- Signature

 The header typically consists of two parts: the type of the token, which is JWT, and the signing algorithm being used, such as HMAC SHA256 or RSA.
 For example:
 ```json
 {
    "alg":"HS256",
    "typ":"JWT"
 }
 ```
 Then, this JSON is Base64Url encoded to form the first part of the JWT.

 The second part of the token is the payload, which contains the claims. Claims are statements about an entity (typically, the user) and additional data. There are three types of claims: registered, public, and private claims.
 An example payload could be:
 ```json
 {
    "sub":"1234567890",
    "name":"John Doe",
    "admin": true
 }
 ```
 The payload is then Base64Url encoded to form the second part of the JSON Web Token.

To create the signature part you have to take the encoded header, the encoded payload, a secret, the algorithm specified in the header, and sign that.
For example if you want to use the HMAC SHA256 algorithm, the signature will be created in the following way:
```java
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```
The signature is used to verify the message wasn't changed along the way, and, in the case of tokens signed with a private key, it can also verify that the sender of the JWT is who it says it is.

The output is three Base64-URL strings separated by dots that can be easily passed in HTML and HTTP environments.

To work with JWT, we need to define the right dependency in the `pom.xml` file:
```xml
<properties>
	<jwt.version>4.3.0</jwt.version>
</properties>

<dependency>
	<groupId>com.auth0</groupId>
	<artifactId>java-jwt</artifactId>
	<version>${jwt.version}</version>
</dependency>
```
### How do JSON Web Tokens (JWT) work?
In authentication, when the user successfully logs in using their credentials, a JSON Web Token will be returned. Since tokens are credentials, great care must be taken to prevent security issues. In general, you should not keep tokens longer than required.

Whenever the user wants to access a protected route or resource, the user agent should send the JWT, typically in the Authorization header using the Bearer schema.
Bearer scheme is an HTTP authentication scheme that involves security tokens called bearer tokens.
The name “Bearer authentication” (also called token authentication) can be understood as “give access to the bearer of this token”. 
The content of the header during requests should look like the following:
```xml
Authorization: Bearer <token>
```
The server's protected routes will check for a valid JWT in the Authorization header, and if it's present, the user will be allowed to access protected resources.
The Bearer authentication scheme was originally created as part of **OAuth 2.0** but is sometimes also used on its own.  

## Spring Validation
In Spring Boot, validation is made easier with annotations that mark fields with specific validation rules.

When you apply the `@Valid` annotation to a method parameter, Spring Boot automatically triggers validation for that parameter before the method is invoked. It is placed before the object to indicate that it should be validated. This means that the incoming data for that parameter will be validated against the specified validation rules.
See the example below:

```java
@RestController
@RequestMapping("/user")
public class ApiController 
{
    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body("Validation failed");
        }
        userService.saveUser(user);
        return ResponseEntity.ok("User created successfully");
    }
}
```
If the data fails validation, Spring Boot will automatically generate validation error messages and associate them with the appropriate fields in the input data.
The dependency which it's required in the `pom.xml` file is:
```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-validation</artifactId>
</dependency>
```

## Spring Data JPA
Spring Data JPA, part of the larger Spring Data family, makes it easy to easily implement JPA-based (`Java Persistence API`) repositories. It makes it easier to build Spring-powered applications that use data access technologies.
Spring Data JPA aims to significantly improve the implementation of data access layers by reducing the effort to the amount that’s actually needed.
The java persistence API provides a specification for persisting, reading, and managing data from your java object to your relational tables in the database. JPA specifies the set of rules and guidelines for developing interfaces that follow standards.

Some key features of Spring Data JPA include:

1. **Repository Abstraction**: Spring Data JPA provides a repository abstraction layer that allows you to define repositories with CRUD operations and custom queries without writing any implementation code.

2. **Query Methods**: You can easily define queries by using method names in your repository interfaces. Spring Data JPA will automatically generate the necessary SQL queries based on the method names.

3. **Pagination and Sorting**: Spring Data JPA supports pagination and sorting out of the box. You can easily retrieve a subset of data and apply sorting to the result set.

4. **Auditing**: Spring Data JPA provides auditing support, allowing you to automatically track and manage the creation and modification timestamps of your entities.

Here's an example of a code snippet that demonstrates the usage of Spring Data JPA:
```java
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByLastName(String lastName);
}
```
In this example, the `UserRepository` interface extends the `JpaRepository` interface, which provides basic CRUD operations for the User entity. The `findByLastName` method is a query method that retrieves users by their last name.

To use Spring Data JPA in your project, you need to include the following Maven dependencies in your `pom.xml` file:

```xml
<dependencies>
    <!-- Spring Data JPA -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <!-- Database Driver (e.g., MySQL) -->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
</dependencies>
```
It's important to replace **mysql-connector-java** with the appropriate database driver for your project.

## Mapstruct
MapStruct is a Java-based code generation library that simplifies the mapping between different Java bean types, such as the mapping between **DTOs (Data Transfer Objects)** and entities.
It eliminates the need for writing boilerplate mapping code by generating the mapping implementation at compile-time.

To use MapStruct, we need to follow these steps:
1. Add the MapStruct dependency to your project's build file (e.g., Maven or Gradle).
2. Define our source and target bean classes.
3. Create a mapper interface that defines the mapping methods. Annotate the interface with `@Mapper` to enable MapStruct to generate the implementation.
4. Define the mapping methods in the mapper interface. MapStruct will automatically generate the implementation based on the method signatures.
5. Optionally, customize the mapping behavior by using annotations such as `@Mapping` or `@Mappings`.
6. Build our project. MapStruct will generate the mapping implementation classes during the compilation process.
7. Use the generated mapper implementation to perform the mapping between source and target objects.

Here's an example of how to use MapStruct:
```xml
// Step 1: Add the MapStruct dependency to your build file
<dependency>
    <groupId>org.mapstruct</groupId>
    <artifactId>mapstruct</artifactId>
    <version>1.5.3.Final</version> 
</dependency>
```
```java
// Step 2: Define your source and target bean classes
public class Car {
    private String make;
    private int year;
    // getters and setters
}

public class CarDto {
    private String make;
    private int year;
    // getters and setters
}

// Step 3: Create a mapper interface
@Mapper
public interface CarMapper {
    // Step 4: Define the mapping methods
    CarDto carToCarDto(Car car);
    Car carDtoToCar(CarDto carDto);
}

// Step 7: Use the generated mapper implementation
Car car = new Car("Toyota", 2022);
CarMapper mapper = Mappers.getMapper(CarMapper.class);
CarDto carDto = mapper.carToCarDto(car);
```

MapStruct will generate the implementation of the `CarMapper` interface, which includes the mapping methods `carToCarDto` and `carDtoToCar`. 
We can then use the generated mapper to perform the mapping between `Car` and `CarDto` objects.

## Lombok
Lombok is a java library tool that is used to minimize/remove the boilerplate code and save the precious time of developers during development by just using some annotations.
In addition to it, it also increases the readability of the source code and saves space.On the other hand, Lombok adds all these boilerplate codes at the compile time in the “.class” file and not in our source code. Let us compare our source code with and without using Lombok.

- **Without Lombok**: A java model class with four private fields and their getters, setters, no-args constructor, parameterized construct, and toString method.
```java
public class Employee {

	private Integer employeeId;
	private String name;
	private String company;
	private String emailId;

	public Employee() {}
	public Employee(Integer employeeId, String name,
					String company, String emailId)
	{
		super();
		this.employeeId = employeeId;
		this.name = name;
		this.company = company;
		this.emailId = emailId;
	}
	public Integer getEmployeeId() { return employeeId; }
	public void setEmployeeId(Integer employeeId)
	{
		this.employeeId = employeeId;
	}

	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public String getCompany() { return company; }

	public void setCompany(String company)
	{
		this.company = company;
	}
	public String getEmailId() { return emailId; }
	public void setEmailId(String emailId)
	{
		this.emailId = emailId;
	}
	@Override public String toString()
	{
		return "Employee ["
			+ "employeeId=" + employeeId + ", name=" + name
			+ ", "
			+ " company=" + company + ", emailId=" + emailId
			+ "]";
	}
}
```
- **With Lombok**: A java model class with four private fields and their getters, setters, no-args constructor, parameterized construct, and toString method using Lombok annotations. 
To use Lombok we first define the right dependency in the ```pom.xml``` file:

```xml
<dependency>
	<groupId>org.projectlombok</groupId>
	<artifactId>lombok</artifactId>
	<scope>provided</scope>
	<version>1.18.30</version>
</dependency>
```
```java
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Employee {
	private @Getter @Setter Integer employeeId;
	private @Getter @Setter String name;
	private @Getter @Setter String company;
	private @Getter @Setter String emailId;
}
```
The difference is clearly visible in the codes.
Lombok provides a set of annotations to make our coding life easier. Let us look at the few most frequently used annotations of Lombok. 

1. **`@Getter`** and **`@Setter`**
These annotations provide the getter and setter methods for a field.
@Getter annotation generates a getter method with access type as public which simply returns the field and with name getName() if the field name is “Name”. @Setter annotation generates a setter method with access type as public which returns void and takes a single parameter to assign the value to the field. The default setter will have the name setName() if the field name is “Name”. 

2. **`@NoArgsConstructor`**: This annotation is used to generate a constructor with no arguments. It has an empty body and does nothing. It is generally used in combination with some other parameterized constructor in use. It is required when you want to generate an object of the class by passing no arguments in the constructor. 

3. **`@AllArgsConstructor`**: This annotation is used to generate a parameterized constructor which accepts a single parameter for each field and initializes them using it. It is required when you want to generate an object of the class by passing the initial values of the fields in the constructor. 

4. **`@Data`**: This annotation is a shortcut annotation and bundles @ToString, @Getter, @Setter, @EqualsAndHashCode and @RequiredArgsConstructor annotations into a single annotation. This annotation provides all the normally used boilerplate code in the model classes of java like getters for all the fields, setter for all the non-final fields, a default implementation for toString(), equals() and hashCode() using all the fields of the class and a constructor that initializes all the fields of the class. 

## Swagger
Swagger is an open-source framework that allows developers to design, build, document, and consume RESTful web services. It provides a set of tools and specifications that enable the creation of interactive API documentation.

With Swagger, we can define your API using the **OpenAPI** Specification (formerly known as Swagger Specification). This specification allow us to describe our API endpoints, request/response formats, authentication methods, and more. 
It provides a standardized way to document and communicate our API design.

To use Swagger, we can add the Swagger dependency to our project in the `pom.xml` file:
```xml
<properties>
	<openapi-swagger.version>1.6.15</openapi-swagger.version>
</properties>

<dependency>
	<groupId>org.springdoc</groupId>
	<artifactId>springdoc-openapi-ui</artifactId>
	<version>${openapi-swagger.version}</version>
</dependency>
```