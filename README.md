# Simple Mock Server
This is a simple mock server created with Java and Spring Boot running on port 9000.
You can run it java and maven, or directly using Docker with the included dockerfile and docker-compose files.

## What does it do?
It allows you to receive a custom response that you previously inserted in the application either using the [/POST](http://localhost:9000/mock/db) method to temporally add it to the H2 database, or copying a JSON file inside the /mocks folder. This response can have the status code, the body response, and the headers that the mock is suposed to return in the answer.

If you are using the /mocks folder approach (recommended), you can return the json calling the endpoint ```http://localhost:9000/mock/<name_of_file_without_extension>```. To return the value using the database, you will have to use the ID returned on the /POST method instead of using the name of the file.

If you don't have postman you can also use [swagger](http://localhost:9000/swagger-ui/index.html) after starting up the server to start using it.

## How does it work?
You have to enter what you want the response to return on the json files or the [/POST](http://localhost:9000/mock/db) method. For either of both, you'd insert something along the lines of:

``` JSON
{
  "headers": {
    "headerKey": "headerValue"
  },
  "mockContent": "{\"sampleKey\": \"sampleValue\"}",
  "httpStatus": 200
}
```

This would return a response with a 200 status response, a "headerkey" header with "headerValue" value, and a body content of {"sampleKey": "sampleValue"}.

For simplicity any number value passed to the method that returns mocks will be understood as a database id, so try not to use json files that only have numbers in their filename (This is also intended to encourage not using generic number-only named files instead of proper named files for permanent mocks).

## Why use a temporary database instead of a permanent one?
The idea was to mainly use files for mocks that are permanent, since they are easier to manage and easier to move around, but sometimes one might want to add a mock just for a test and not use it anymore, thus why an H2 database was added for those cases.

You can check the available endpoints using [swagger](http://localhost:9000/swagger-ui/index.html) once the server has started. All endpoints which have the /db/** path belong to the database, while any other just return the mocked response from a file or a database.

## Installation
Just download the contents of the repo and use maven to compile it with mvn package or mvn clean install. 
After that, from the root folder either run the generated jar file directly with java with ```java -jar ./target/SimpleMockServer-1.0.0.jar```, or using maven run mvn spring-boot:run

