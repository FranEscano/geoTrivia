**GeoTrivia Server**

This project sets up a JSON server for a GeoTrivia application. It provides endpoints for fetching trivia questions, adding new questions, searching questions, and fetching paginated questions. The server includes authentication, request body validation, and logging functionalities.

### Setup Instructions

1. **Clone the Repository**:
   ```
   git clone https://github.com/FranEscano/geoTrivia.git
   ```

2. **Install Dependencies**:
   ```
   cd geoTrivia
   npm install
   ```

3. **Run the Server**:
   ```
   npm start
   ```

4. **Access the API**:
    - Trivia questions are available at `http://localhost:3000/questions`.
    - Use basic authentication with username `admin` and password `password`.

### Endpoints

- **GET `/questions`**: Fetches paginated trivia questions.
    - Query Parameters:
        - `page` (optional): Page number (default: 1).
        - `limit` (optional): Number of questions per page (default: 10).

- **GET `/search?q=query`**: Searches for trivia questions containing the specified query string.

- **POST `/questions`**: Adds a new trivia question.
    - Request Body:
      ```json
      {
        "question": "What is the capital of France?",
        "answer": "Paris"
      }
      ```

### Authentication

- Basic authentication is required to access the API.
- Username: `admin`
- Password: `password`

### Middleware

- **Authentication Middleware**: Ensures that endpoints are accessed with valid credentials.
- **Validation Middleware**: Validates request bodies for POST and PUT requests.
- **Log Middleware**: Logs request activity to a file named `activity.log`.

### Dependencies

- [json-server](https://www.npmjs.com/package/json-server): Used to create a mock JSON server.
- [body-parser](https://www.npmjs.com/package/body-parser): Middleware for parsing JSON request bodies.
- [fs](https://nodejs.org/api/fs.html): File system module for logging request activity.

### Usage

- Use the provided endpoints to interact with the GeoTrivia server.
- Fetch, add, search, and paginate trivia questions as needed.
- Ensure proper authentication and valid request bodies for adding questions.

### Contributors

- [Francisco Bejarano Escano](https://github.com/FranEscano)

Feel free to contribute to this project by submitting pull requests or opening issues. Happy Trivia Gaming!