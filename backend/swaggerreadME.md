Access Swagger UI in your browser:

Local: http://localhost:8080/swagger-ui/index.html

EC2: http://3.148.173.108:8080/swagger-ui/index.html

public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
http
.csrf(csrf -> csrf.disable())
.authorizeHttpRequests(auth -> auth
.requestMatchers(
"/api/auth/**",
"/api/crypto/**",
"/v3/api-docs/**",
"/swagger-ui/**",
"/swagger-ui.html"
).permitAll()
.anyRequest().authenticated()


Allowed access to:

/api/auth/** → for login/register

/api/crypto/** → for public crypto APIs

/v3/api-docs/**, /swagger-ui/**, /swagger-ui.html → Swagger/OpenAPI access