# blog-app-apis
 Blog Application (Backend) using Spring Boot

# Form based authentication
1. add dependency '' in pom.xml
2. run the app and you'll get the form based authentication ready
3. username : 'user' | password : (will be shown on the console such as below : )

```
Using generated security password: 81da61b3-c24d-437d-ab23-e288c185aa18

This generated password is for development use only. Your security configuration must be updated before running your application in production.
```


#JWT Authentication
STEP-1) add dependency : (io.jsonwebtoken)

```
<!-- https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt</artifactId>
    <version>0.9.1</version>
</dependency>
```
STEP-2) create class JwtAuthenticationENtryPoint implements AuthenticationEntryPoint in Security package, mark the class as @Component for autowiring 
-> 'commence()' (method under this class) will run, whenever some unauthorized person tries to access the APIs

STEP-3) create class JwtTokenHelper for the token related operations and mark the class as @Component for autowiring : in this class, the entire code is copied from source and pasted here

STEP-4) create class jJwtAuthenticationFilter extends OncePerRequestFilter, mark the class as @Component for autowiring 
-> 'doFilterChain()' method will run, whenever a reuest is hit

```
-> Steps :
	i.	autowire 'UserDetailsService' and 'JwtTokenHelper' 
	ii.	get JWT token from request
	iii.retrieve username from JWT token
	iv. once we get the token, Validate the token
	v. fetching userDetails by 'loadUserByUsername()' method of 'UserDetailsService'
	vi. once we Validate the token and check Authentication is null in Security Context, Set authentication in Security Context
	vii. filter the request using 'filterChain.doFIlter()' method
```	
```
STEP-5) create JwtAuthResponse (Here we will keep the token only as a field and annotate the class with @Data)

STEP-6) Configure JWT in spring security Config (in SecurityConfig class)

