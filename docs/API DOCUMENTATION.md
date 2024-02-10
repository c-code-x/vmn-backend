<details>
<summary> Authorization </summary>

### Base URL
http://localhost:8080/api

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Admin Authentication(Login)
**URL:** `/login`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>POST</code> <code><b>/</b></code> <code>(overwrites all in-memory stub and/or proxy-config)</code></summary>

##### Parameters
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | emailId   |  String   | object (JSON )          |Use Authorised admin EmailId for login                                 |
> | password  |  String   | object (JSON )          |Password for authenication                                             |


##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `text/plain;charset=UTF-8`        | 'http Status: Ok', 'message: Login successful'                      |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Unauthorized operation'       |

##### Example cURL

> ```javascript
>  curl -X POST -H "Content-Type: application/json" --data @post.json http://localhost:8889/
> ```

##### Request Body
    ```json
    {
        "emailId": "user@example.com",
        "password": "userpassword"
    }
    ```
##### Response Body Example
    ```json
    {
        "data": {
            "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c"
        },
        "meta": {
            "message": "Login successful",
            "status": "OK"
        }
    }
    ``` 

</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Renew Token
**URL:** `/renew-token`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
 <summary><code>GET</code> <code><b>/</b></code> <code>(gets all in-memory stub & proxy configs)</code></summary>

##### Parameters
>None

##### Description
>Generates a new token for the authorized login to verify and the send the invitation.

##### Responses

> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `JWT key`                         | 'http Status: Ok', 'message: Token Renewed'                         |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invalid Token'                |
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

### Authentication
- **Type:** JWT (JSON Web Token)
- **Authorization Header:** Provide the JWT token obtained during login as a bearer token in the request headers for authenticated endpoints.

### Error Handling
- In case of errors, the API will return appropriate HTTP status codes along with error details in the response body.

### Security
- This API utilizes JWT for authentication, ensuring secure transmission of data over the network.

### Note
- Ensure that proper authentication credentials are provided for accessing secured endpoints.
- Handle authentication tokens securely and avoid exposing them to unauthorized users.

------------------------------------------------------------------------------------------------------------------------------------------------------

</details>

<details>
<summary> Invitation </summary>


### Base URL
http://localhost:8080/api/invite

------------------------------------------------------------------------------------------------------------------------------------------------------

#### New Invitation
**URL:** `/new`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>POST</code> <code><b>/</b></code> <code>(overwrites all in-memory stub and/or proxy-config)</code></summary>

#### Parameters
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | userName  |  String   | object (JSON )          |Invitee Name                                                           |
> | emailId   |  String   | object (JSON )          |Invitee emailId                                                        |
> | role      |  String   | object (JSON )          |role of the invitee(Admin, Coaches, mentors, ventures, mentees)        |

#### Respones
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `text/plain;charset=UTF-8`        | 'http Status: Ok', 'message: Invitation sent successfully'          |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invitation already sent'      |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:User Exists'                  |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invitation to Same Role'      |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invitation to Invalid Role'   |
> | `500`         | `application/json`                |  'http Status: Bad request', 'message:Internal Server error'        |

#### Example cURL
>```javascript
>curl -X POST \
>https://your-api-domain.com/invite/new \
>-H 'Content-Type: application/json' \
>-d '{
>       "email": "user@example.com",
>       "role": "ROLE_USER"
>   }'
>```

#### Request Body
>```json
>{
>   "name":"user_name",
>   "email": "user@example.com",
>   "role": "ROLE_USER"
>}
>```

#### Response Body
>```json
>{
>   "message": "Invitation created successfully"
>}
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Resend Invitation
**URL:**  `/resend/{invId}`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>POST</code> <code><b>/</b></code> <code>(overwrites all in-memory stub and/or proxy-config)</code></summary>

#### Parameters
None

-The Invitation Id is to be provided in the URL.
 
#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `401`         | `application/json`                | 'http Status: Bad Request', 'message: Invalid Invitation'           |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invitation Already Accepted'  |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Unauthorized Operation'       |

#### Example cURL
>```javascript
>curl -X POST \
>http://localhost:8080/api/invite/resend/invId=invitation_id_here 
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Verify Invitation
**URL:** `/verifyInvite/{token}`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
 <summary><code>GET</code> <code><b>/</b></code> <code>(gets all in-memory stub & proxy configs)</code></summary>

#### Parameters
None

-The Invitation token is to be provided in the URL.

#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `401`         | `application/json`                | 'http Status: Bad Request', 'message: Invalid Invitation Token'     |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invitation Already Accepted'  |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invitation expired'           |

#### Example cURL
>```javascript
>curl -X GET \
> 'https://your-api-domain.com/invite/verifyInvite/token=invitation_token_here'
>```

#### Response Body Example
>```json
>{
>   "data": {
>       "id": "invitation_id",
>       "email": "user@example.com",
>       "role": "ROLE_USER"
>   }
>}
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Accept Invitation and Create User
**URL:** `/acceptInvitation/{token}`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>POST</code> <code><b>/</b></code> <code>(overwrites all in-memory stub and/or proxy-config)</code></summary>

#### Parameters
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | userName  |  String   | object (JSON )          |User Name                                                              |
> | emailId   |  String   | object (JSON )          |Use Authorised EmailId for login                                       |
> | password  |  String   | object (JSON )          |Password for authenication                                             |
> | contact   |  String   | object (JSON )          |User contact                                                           |
> | Linkedin  |  String   | object (JSON )          |Social Media                                                           |
> | Role      |  String   | object (JSON )          |Role provided by the admin                                             |

-The Invitation token is to be provided in the URL.

#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `text/plain;charset=UTF-8`        | 'http Status: Ok', 'message: User created successfully'             |
> | `201`         | `text/plain;charset=UTF-8`        | 'http Status: Ok', 'message: Invitation Accepted'                   |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invalid Invitation '          |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Invitation Expired'           |
> | `500`         | `application/json`                |  'http Status: Internal Server Error', 'message:Error occured while processing the request'        |

#### Example cURL
>```javascript
>curl -X POST \
>'https://your-api-domain.com/invite/acceptInvitation/token=invitation_token_here' \
> -H 'Content-Type: application/json' \
> -d '{
>       "username": "newuser",
>       "password": "password"
>   }'
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

#### New Mentee using ventureId
**URL:** 'new/mentee/{ventureId}'
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>POST</code> <code><b>/</b></code> <code>(overwrites all in-memory stub and/or proxy-config)</code></summary>

#### Parameters
> | name      |  type     | data type               | description                                                           |
> |-----------|-----------|-------------------------|-----------------------------------------------------------------------|
> | userName  |  String   | object (JSON )          |Invitee Name                                                           |
> | emailId   |  String   | object (JSON )          |Invitee emailId                                                        |
> | role      |  String   | object (JSON )          |role of the invitee(Admin, Coaches, mentors, ventures, mentees)        |

-The unique VentureId to be provided in the URL

#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `application/json`                | 'http Status: Created', 'message: Mentee invitation sent successfully'  |
> | `400`         | `application/json`                | 'http Status: Bad Request', 'message: Invalid request body'          |
> | `404`         | `application/json`                | 'http Status: Not Found', 'message: Venture not found'               |
> | `500`         | `application/json`                | 'http Status: Internal Server Error', 'message: Server error'        |
> | `401`         | `application/json`                |  'http Status: Bad Request', 'message:Unauthorized Operation'        |

#### Example cURL
>```javascript
>curl -X POST \
>http://localhost:8080/api/invite/new/mentee/venture_id_here \
>-H 'Content-Type: application/json' \
>-d '{
>       "name":"user_name",
>       "email": "user@example.com",
>       "role": "ROLE_USER"
>   }'
>```

#### Request Body
>```json
>{
>   "name":"user_name",
>   "email": "user@example.com",
>   "role": "ROLE_USER"
>}
>```

#### Response Body
>```json
>{
>   "message": "Mentee invitation sent successfully"
>}
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

</details>

<details>
<summary> Coach </summary>

### Base URL
http://localhost:8080/api/coach

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Create Venture
**URL:** `/venture`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>POST</code> <code><b>/</b></code> <code>(overwrites all in-memory stub and/or proxy-config)</code></summary>

#### Parameters
> | name           |  type     | data type               | description                                                           |
> |----------------|-----------|-------------------------|-----------------------------------------------------------------------|
> | ventureName    |  String   | object (JSON )          |Name of the venture                                                    |
> | ventureStage   |  String   | object (JSON )          |Stage of the venture                                                   |

#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `201`         | `application/json`                | 'http Status: Created', 'message: Venture created successfully'     |
> | `500`         | `application/json`                | 'http Status: Internal Server Error', 'message: Server error'       |

#### Example cURL
>```javascript
>curl -X POST \
>http://localhost:8080/api/coach/venture \
>-H 'Content-Type: application/json' \
>-d '{
>       "ventureName": "New Venture",
>       "ventureStage": "RSG"
>   }'
>```

#### Request Body
>```json
>{
>       "ventureName": "New Venture",
>       "ventureStage": "RSG"
>}
>```

#### Response Body
>```json
>{
>   "message": "Venture created successfully"
>}
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Get All Ventures
**URL:**  `/venture`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
 <summary><code>GET</code> <code><b>/</b></code> <code>(gets all in-memory stub & proxy configs)</code></summary>

#### Parameters
None

#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | 'http Status: OK', 'data: List of all ventures'                     |
> | `500`         | `application/json`                | 'http Status: Internal Server Error', 'message: Server error'       |

#### Example cURL
>```javascript
>curl -X GET \
>http://localhost:8080/api/coach/venture
>```

#### Response Body Example
>```json
>{
>   "data": [
>       {
>           "vid": "venture_id",
>           "coachId":"uuid",
>           "name": "venture",
>           "campus": "Blr",
>           "tag": "Venture Unique tag",
>           "stage": "RSG",
>           "serviceArea": "Transportation",
>           "bio": "about venture",
>           "sector": "industry/sector",
>       }
>   ]
>}
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

</details>

<details>
<summary> User </summary>

### Base URL
http://localhost:8080/api

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Get Profile
**URL:** `/user`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>GET</code> <code><b>/</b></code> <code>(gets all in-memory stub & proxy configs)</code></summary>

#### Parameters
None

#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | 'http Status: OK', 'data: Profile data'                             |
> | `401`         | `application/json`                | 'http Status: Unauthorized', 'message: Unauthorized operation'      |
> | `500`         | `application/json`                | 'http Status: Internal Server Error', 'message: Server error'        |

#### Example cURL
>```javascript
>curl -X GET \
>http://localhost:8080/api/user
>```

#### Response Body Example
>```json
>{
>   "data": {
>       "profileData": {
>           "uuid": "user_id",
>           "userName": "John Doe",
>           "email": "john.doe@example.com",
>           "role": "admin/mentee/etc",
>           "passoword": "Software_Engineer",
>           "contact": "1010011010",
>           "linkedin":"https://linkedin.com/johndoe",
>           "designation":"your designation"
>       }
>   },
>   "meta": {
>       "message": "Profile fetched!",
>       "status": "OK"
>   }
>}
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

#### Update Profile
**URL:** `/user`
------------------------------------------------------------------------------------------------------------------------------------------------------
<details>
<summary><code>PUT</code> <code><b>/</b></code> <code>(overwrites all in-memory stub and/or proxy-config)</code></summary>

#### Parameters
> | name              |  type     | data type               | description                                                           |
> |-------------------|-----------|-------------------------|-----------------------------------------------------------------------|
> | profileDataResDTO |  Body     | object (JSON )          |Profile data to be updated                                             |

#### Responses
> | http code     | content-type                      | response                                                            |
> |---------------|-----------------------------------|---------------------------------------------------------------------|
> | `200`         | `application/json`                | 'http Status: OK', 'message: Profile updated successfully'          |
> | `400`         | `application/json`                | 'http Status: Bad Request', 'message: Invalid request body'          |
> | `401`         | `application/json`                | 'http Status: Unauthorized', 'message: Unauthorized operation'      |
> | `500`         | `application/json`                | 'http Status: Internal Server Error', 'message: Server error'        |

#### Example cURL
>```javascript
>curl -X PUT \
>http://localhost:8080/api/user \
>-H 'Content-Type: application/json' \
>-d '{
>       "profileData": {
>            "uuid": "user_id",
>           "userName": "John Doe",
>           "email": "john.doe@example.com",
>           "role": "admin/mentee/etc",
>           "passoword": "Software_Engineer",
>           "contact": "1010011010",
>           "linkedin":"https://linkedin.com/johndoe",
>           "designation":"your designation"
>       }
>   }'
>```

#### Request Body
>```json
>{
>       "profileData": {
>            "uuid": "user_id",
>           "userName": "John Doe",
>           "email": "john.doe@example.com",
>           "role": "admin/mentee/etc",
>           "passoword": "Software_Engineer",
>           "contact": "1010011010",
>           "linkedin":"https://linkedin.com/johndoe",
>           "designation":"your designation"
>       }
>}
>```

#### Response Body
>```json
>{
>   "message": "Profile updated!"
>}
>```
</details>

------------------------------------------------------------------------------------------------------------------------------------------------------

</details>
