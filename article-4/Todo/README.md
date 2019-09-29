# Todo API

Example accompanying this [blog post](https://medium.com/@JohanneA/api-101-the-basics-of-building-a-restful-api-6f4a2f6afbaf)

### How to run

Clone from github and open in IntelliJ.

Open the build.gradle file which should prompt a `Do you want import this gradle project`. 
Click yes and wait while it gets all the dependencies. Then navigate to src/kotlin/TodoAPI and
run the main method. 


Or download the repository, then you can open Intellij and choose File > Open and then navigate to the 
folder. If you click on the `build.gradle` file Intellij should prompt you to import it is a project. 
Click yes and watch the magic happen. 

### Authentication

Send a POST request to `localhost:8080/login` with the body
```json
{
	"name": "Hi",
	"password": "123456"
}
```

You could enter whatever credentials you want since we don't have a data store yet. 

This returns a token, which you need to put in the authentication header of any requests to the resources
like so:
```json
Authentication Bearer <token>
```