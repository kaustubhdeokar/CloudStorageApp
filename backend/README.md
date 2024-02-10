Application to communicate with global Amazon S3 bucket.
Supports user auth.

## Endpoints

### User Auth
<hr>

#### POST /register:

- Functionality: Registers a new user by saving user information provided in the request body.<br>
<u>Response</u>:
- 200 OK: Registration successful, and a verification email is sent.
- 400 Bad Request: If a user with the same username or email already exists.

#### GET /users

- Functionality: Retrieves a list of all users and displays them using a Thymeleaf template.<br>
<u>Response</u>:
- Renders a view named "users" with the list of users.

#### POST /login:

- Functionality: Handles login requests by validating user credentials.<br>
<u>Response</u>
- 200 OK: Successful login.
- 400 Bad Request: If the provided email and password do not match any existing user.

<hr>

### S3 Bucket 

<hr>

#### POST /file/upload:

- Functionality: Uploads a file by accepting a multipart file in the request.<br>
<u>Response</u>:
- 200 OK: File upload successful, returns a message from the StorageService.

#### GET /file/download/{fileName}:

- Functionality: Downloads a file by providing the file name in the path variable.<br>
<u>Response:</u>
- 200 OK: Returns the requested file as a byte array with appropriate headers for download.

#### DELETE /file/delete/{fileName}:

- Functionality: Deletes a file by providing the file name in the path variable.<br>
<u>Response:</u>
- 200 OK: File deletion successful, returns a message from the StorageService.