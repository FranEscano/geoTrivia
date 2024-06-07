# Geography Trivia Game

A simple geography trivia game implemented in Java, with questions and answers served from a JSON RESTful API.

## Description

This is a basic console-based geography trivia game where users are asked a series of geography-related questions. 
The user's answers are compared to the correct answers, and a final score is displayed at the end. The question and 
answers are served from a RESTful API using `json-server`.


## Features

- Geography-related questions served from a JSON RESTful API
- Case-insensitive answer checking
- Score tracking

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Node.js installed
- `json-server` installed globally
- Gson library for JSON parsing (added as a dependency)

### Installation

1. Clone the repository or download the source code.

```sh
git clone https://github.com/FranEscano/geoTrivia.git
```

2. Navigate to the project directory.
```sh
cd client.geoTrivia
```

3. Install `json-server` if you haven't already
```sh
npm install -g json-server
```

4. Ensure you have the Gson library. If you are using Maven, add the following dependency to your `pom.xml:`
```sh
<dependency>
  <groupId<com.google.code.gson</groupId>
  <artifactId>gson</artifactId>
  <version>2.8.6</version> 
```
If you are not using Maven, download the Gson jar from https://github.com/google/gson and add it to your 
project's classpath

## Running the Game

1. Start the JSON server.
```sh
json-server --watch db.json
```

2. Compile the Java source file.
```sh
mvn compile
```

3. Run the compiled Java program
If using Maven:
```sh
mvn exec:java -Dexec.mainClass="client.GeoTriviaClient"
```

## How to Play
 - The game will display a series of geography-related questions. 
 - Type your answer and press Enter. 
 - The game will inform you if your answer is correct or incorrect. 
 - At the end of the game, your final score will be displayed.

### Example Questions
    1. What is the capital of France?
    2. What is the longest river in the world?
    3. What is the largest country in the world?
    4. What is the largest desert in the world?
    5. On which continent is Argentina located?

### Example Output
```
What is the capital of France?
Paris
Correct!
What is the longest river in the world?
Amazon
Incorrect. The correct answer is Nile.
...
Your final score is: 4 out of 5
```


## Running Tests

### Prerequisites

- Ensure the JSON server is running:
```shell
json-server --watch db.json
```

### Running Tests with Maven
To run the tests, execute the following command:
```shell
mvn test
```
This will run tests using RestAssured to verify that the questions are being correctly fetched from the API.