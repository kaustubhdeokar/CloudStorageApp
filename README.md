# CloudStorageApp
S3 file storage. UI access through a portal created in ReactJS. Handled in backend by Spring boot.

Todo:

Make detailed report of: 

- What is CORS
> Specification implemented by browsers that specifies what kind of cross domain requests are authorised.
CORS specification distinguishes between preflight, simple and actual requests.

Taking an example of application-1 on (domain A) and application-2 on (domain B). 
- if app-1 has to make a POST call to app-2, before the actual request a preflight(options) call is made.
- If the call is valid, app-2 sents additional headers to app-1, the additional headers can be like:
    cross-control-allow-origin:app1-url
    cross-control-allow-method:method-name(post, delete)

- How to enable cors for spring.
> To enable cors in the spring backend - added this piece of code.
```
  @Bean CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173/**", "http://localhost:5173/"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

```

>  How to pass data from react to spring boot. 

> How to communicate spring boot response to react. 

> How to route between pages react.
