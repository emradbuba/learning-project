## TODO LIST
### _ _ _Spring Security_ _ _

* :white_check_mark: Refresh security notes
* :white_check_mark: Identify Swagger/Actuator endpoints to ensure they are accessible
* :white_check_mark: Create SpringBoot configuration
  * :white_check_mark: Basic authentication
  * :white_check_mark: USER role for all rest action but the delete actions
  * :white_check_mark: ADMIN role for USER plus deleting actions
* :white_check_mark: Debug session
* :white_check_mark: BasicAuthenticationFilter vs UsernamePasswordFilter - add notes
* :white_check_mark: Create extra SecurityFilterChain as a second configuration - extract Swagger
* :white_check_mark: Create extra AuthenticationProvider for LDAP like fake implementation - think about details
  * :white_check_mark: Create external library with autoconfig
  * :white_check_mark: Use library, set security in both
  
 
* :red_circle: [Authorization Architecture](https://docs.spring.io/spring-security/reference/servlet/authorization/architecture.html) - create notes
* :red_circle: [Password storage](https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/storage.html) options - create notes
* :red_circle: About [Exploits](https://docs.spring.io/spring-security/reference/features/exploits/index.html)
* :red_circle: Migration Guides -> 6.2 / 7.0, ldap  --> [docu](https://docs.spring.io/spring-security/reference/migration-7/index.html)
* :red_circle: JWT, token OAuth... ?
   
### Overall: 
* :red_circle: Create parent pom for lib with properties and dependency management