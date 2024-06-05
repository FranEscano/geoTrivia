# Geography Trivia Game

A simple geography trivia game implemented in Java, with questions and answers served from a RESTful API built with Spark.

## Description

This is a basic console-based geography trivia game where users are asked a series of geography-related questions. The user's answers are compared to the correct answers, and a final score is displayed at the end. The question and answers are served from a RESTful API built with Spark in Java.

## Features

- Five geography-related questions
- Case-insensitive answer checking
- Score tracking

## Getting Started

### Prerequisites

- Java Development Kit (JDK) installed
- Maven installed

### Installation

1. Clone the repository or download the source code.

```sh
git clone https://github.com/FranEscano/client.geoTriviaClient.git
```

2. Navigate to the project directory.
```sh
cd client.geoTriviaClientClient
```

### Running the Game

1. Start the JSON server.
```sh
mvn compile exec:java
```

2. In another terminal, compile the client Java source file.
```sh
mvn compile exec:java -Dexec.mainClass="client.geoTriviaClient"
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
