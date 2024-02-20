# React + Vite

What is CORS
specification implemented by browsers that specifies what kind of cross domain requests are authorised.
CORS specification distinguishes between preflight, simple and actual requests.

Taking an example of application-1 on (domain A) and application-2 on (domain B). 
- if app-1 has to make a POST call to app-2, before the actual request a preflight(options) call is made.
- If the call is valid, app-2 sents additional headers to app-1, the additional headers can be like:
    cross-control-allow-origin:app1-url
    cross-control-allow-method:method-name(post, delete)


>  How to pass data from react to spring boot. 
- create any object, containing fields which should be similar to the dto object expected
  in the backend application. 

> How to communicate spring boot response to react. 
- to send a response with status is done in a regular way. The data to be use on the frontend side can be found in response.data.<> fields.


> How to route between pages react.
- Navigate library. Also use services to store session info and all, which can be used to put up conditionals. 

> fetch data and display it on a form.
- //todo:

