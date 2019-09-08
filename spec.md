# Todo list API

**Requirements**
 - CRUD on a to do list
 - Multiple users
 - Users can share lists


 ### Endpoints
 `{}` means optional


###### Users
 ```
GET /users/{id}

Response body
200 OK 
{
    "self": string,
    "id": integer
    "name": string,
    "todoLists": todoList[]
}

400 Bad request, 404 Not found
 ```


```
GET /users/<id>/todo-lists

Response body
200 OK 
{
    {
        "id": integer,
        "self": string,
        "owner": user,
        "numberOfItems": integer,
        "todoItems": todoItem[]
    },
    ...
}
400 Bad request
 ```

 ```
POST /users
Request body: 
{
  "username": string,
  "email": string,
  "password": string
}

Response body
201 Created
{
    "name": string
}

400 Bad request
 ```


 ###### Todo list
 ```
 GET /todo-lists/{id}

 Response body
 200 OK
 {
     "self": string,
     "id": integer,
     "name": string,
     "owners": string,
     "ownerLink": string,
     "createdDate: datetime,
     "todoItems": todoItem[]
 }

400 Bad request, 404 Not found
 ```

 ```
 POST /todo-lists/
 
 Request body
 {
    "name": string
 }

Response body
201 Created
{
    "self": string,
    "id": integer,
    "name": string,
    "owner": string,
    "ownerLink": string
}
400 Bad request
 ```

 ```
 PATCH /todo-lists/<id>
 Request body
 {
     //name of field you want to change
     "name": string,
 }

 200 OK, 400 Bad request, 404 Not found
 ```

 ```
 DELETE /todo-lists/<id>
 
 Request body
 200 OK, 404 Not found
 ```

###### Todo item
 ```
 GET /todo-items/{id}

 Response body
 200 OK
 {
     "self": string,
     "id": integer,
     "ownerId": string,
     "ownerLink": user,
     "parentId": string,
     "parentLink": todoListLink,
     "title": string,
     "description": string,
     "createdDate": datetime,
     "deadline": datetime,
     "completed": boolean
 }
 ```

 ```
 POST /todo-items/

 Request body
 {
     "title": string,
     "description: string,
     "deadline": datetime,
     "parentId": integer
 }
 
 Response body
 201 Created
 {
    "self": string,
    "id": integer,
    "title": string,
    "parent": string,
    "parentLink": string
}
400 Bad request
 ```

 ```
 PATCH /todo-items/<id>
  Request body
 {
     //fields you want to change
     "title": string,
     "completed": boolean,
     "description": string,
     "parentid" integer
 }

 Response body
 200 OK, 400 Bad request, 404 Not found
 ```

 ```
 DELETE /todo-items/<id>

Response body
200 OK, 404 Not found
 ```

