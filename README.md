# blog-app-apis
 Blog Application (Backend) using Spring Boot
 
# Important Points
1. In Entities and its respective DTOs, the variable names (i.e. field names) should be same (example : if in Post entity, there is a field named as "User", in the PostDto as well, the field name should be "User" only, not "UserDto")
2. Always assign a normal role to the newly registered user generally

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
STEP-7) create a login API to return token

```
	i. create class JwtAuthRequest (annotate the class with @Data)
	ii. create class JwtAuthResponse (annotate the class with @Data)
	iii. create Log In API for creating token (create a method 'createToken()' in AuthController)
```

STEP-8) make the login url public (available for all) (in SecurityConfig class)

```
		http
		.authorizeHttpRequests()
		.requestMatchers("/auth/login").permitAll()
```

STEP-9) test the application

```
	i. log in :
		a. go to the GET request : "/auth/login" with the request body
		{
			"username" : "<userEmail>",
			"password" : "<userPassword>"
		}
	
	ii. in case of correct username and password, you will get a token generated for the particular user
	
	iii.now go to any other API, in the 'Header' section, provide the token as : 'Bearer <token>' and hit the request, and you'll be able to access the API
```
`
<br>
<br>
#Role Specific APIs

STEP-1) Configuration of Roles

CASE-I: In case roles will be created manually in DB
Check the DB -> 
		i. user table -> check if for all the users, passwords are encoded, and know the decoded passwords
		ii. role table -> check if roles 'ROLE_ADMIN' and 'ROLE_NORMAL' are already created, if not created, then create the roles
		iii. user_role table -> check if specific roles are assigned to specific users or not, if not then assign

CASE-II: In case roles will be created through code as defined in the class BlogApplication.java (main class) 
		-> it is already implemented in the main class
		-> also need to create RoleRepository
		-> also need to create RoleDto for Register API
		
STEP-2) annotate the class 'SecurityConfig' with @EnableGlobalMethodSecurity(prePostEnabled=true) -> now we can apply security in each method

STEP-3) go to the specific HTTP method controller for which you want to apply this, annotate the method @Preauthorize("hasRole('<ROLE>')")

STEP-4) if you want to secure a bunch of APIs with specific URL pattern, then secure them in the SecurityConfig ->
		code : antMatchers/requestMathers("<URL-pattern>").hasRole('<ROLE>')
		example: requestMatchers("/users/delete").hasRole('ADMIN')

STEP-5) if you want to un-secure (publicly accessible) any specific type of Requests, then un-secure them in the SecurityConfig ->
		code : antMatchers/requestMatchers(HttpMethod.<HTTP-METHOD>).permitAll()
		example : requestMatchers(HttpMethod.GET).permitAll()
		


# Swagger Configuration (Old method : using springfox)
STEP-1) add dependency : springfoc-boot-starter

```
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-boot-starter</artifactId>
    <version>3.0.0</version>
</dependency>
```

STEP-2) annotate the security configuration class (SecurityConfig.java) with @EnableWebMVC

STEP-3) make the URL ("/v3/api-docs") publicly accessible in security configuration class (SecurityConfig.java) [note : v3 -> as we are using swagger version 3.0.0) as mentioned in the dependency]

STEP-4) make the below mentioned URLs publicly accessible :
	
	i. "/v2/api-docs"
	ii. "/swagger-resources/**"
	iii. "/swagger-ui/**"
	iv. "/webjars/**"
	
	NOTE : You can put all these URls, which you need to make publicly accessible, in an array
	
STEP-5) Do the necessary Swagger Configurations in SwaggerConfid.java	
	
STEP-6) now you can run the application and if you hit the URLs mentioned below you can the respective results :

	i. Postman : http://localhost:8080/v3/api-docs : complete API details in the Back-end Form
	ii. Browser : http://localhost:8080/swagger-ui/index.html : complete API documentation in UI Form 
	
	
# Migration from SpringFox to SpringDoc : 

```
Reference Documentation : https://springdoc.org/migrating-from-springfox.html
Reference Video Link : https://www.youtube.com/watch?v=UvIWQSKz8kE
```
	
# Swagger Configuration (New method : using springdoc)

STEP-1) add dependency : springdoc-openapi-starter-webmvc-ui

```
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-boot-starter -->
   <dependency>
      <groupId>org.springdoc</groupId>
      <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
      <version>2.6.0</version>
   </dependency>
```

STEP-2) annotate the security configuration class (SecurityConfig.java) with @EnableWebMVC

STEP-3) make the URL ("/v3/api-docs") publicly accessible in security configuration class (SecurityConfig.java) [note : v3 -> as we are using swagger version 3.0.0) as mentioned in the dependency]

STEP-4) make the below mentioned URLs publicly accessible :
	
	i. "/swagger-resources/**"
	ii. "/swagger-ui/**"
	iii. "/webjars/**"
	
	NOTE : You can put all these URls, which you need to make publicly accessible, in an array
	
STEP-5) Do the necessary Swagger Configurations in SwaggerConfig.java : create a method to return OpenAPI object

STEP-6) add swagger configurations in application.properties file :

```
springdoc.packages-to-scan=com.bikram.blog.controllers
springdoc.paths-to-match=/auth/**, /users/**, /categories/**, /posts/**, /comments/**, /user/**, /category/**, /post/**, /files/**, /comments/** 
```
	
STEP-7) now you can run the application and if you hit the URLs mentioned below you can the respective results :

	i. Postman : http://localhost:8080/v3/api-docs : complete API details in the Back-end Form
	ii. Browser : http://localhost:8080/swagger-ui/index.html : complete API documentation in UI Form 

	
# Implementation of Security in API Docs
There are 2 Processes to implement Swagger Configuration with Security in API Docs

Process-1) By declaration of Bean

in Swagger configuration File, in the method openAPI(), while returning OpenAPI object, add 'addSecurityItem' and 'components' part to the object

```
SwaggerConfig.java > openAPI() > add componenets() part :

new OpenAPI()
	.addSecurityItem(new SecurityRequirement().addList(schemeName))
	.components(new Component()
		.addSecuritySchemes(schemeName, new SecurityScheme()
			.name("")
			.type("")
			.bearerFormat(SecurityScheme.Type.{TYPE})
			.scheme("")))
	.info(new Info().title("...")....)

```

```
NOTE : in Swagger-ui page, we don't need to put "Bearer" from out side while providing the token 
```


Process-2) Using Annotations

STEP-I) Do necessary configuration using Annotation in Swagger Configuration class(SwaggerConfig.java)

```
@Configuration
@SecurityScheme(
		name = "bearerScheme",
		type = SecuritySchemeType.HTTP,
		bearerFormat = "JWT",
		scheme = "bearer"
		)
@OpenAPIDefinition(
		info = @Info(
				title = "Blogging Appilacation",
				description = "This project is developed by SDE Bikram Dey",
				version = "1.0",
				contact = @Contact(
						name = "Bikram Dey",
						email = "bikramdeyofficial@gmail.com",
						url = "https://www.linkedin.com/in/bikramdey/"
						),
				license = @License(
						name = "OPEN License"
						)
				),
		externalDocs = @ExternalDocumentation(
				description = "Software Development Engineer : Bikram Dey",
				url = "https://www.linkedin.com/in/bikramdey/"
				))
public class SwaggerConfig {

}
```

STEP-II) Annotate all the Controller Classes with the Annotation "@SecurityRequirement"

```
@SecurityRequirement(name = "bearerScheme")	// for implementation of security in swagger
```