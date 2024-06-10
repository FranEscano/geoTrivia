const jsonServer = require('json-server');
const server = jsonServer.create();
const router = jsonServer.router('src/main/resources/db.json');
const middleware = jsonServer.defaults();
const fs = require('fs');
const logFile = 'activity.log';
const bodyParser = require('body-parser');

server.use(bodyParser.json()); // Using body-parser middleware to parse JSON requests

// Middleware for authentication
const authMiddleware = (req, res, next) => {
    const auth = { login: 'admin', password: 'password' }; // Defining authentication credentials
    // Extracting base64 encoded credentials from request headers
    const b64auth = (req.headers.authorization || '').split(' ')[1] || '';
    const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':'); // Decoding credentials

    if (login && password && login === auth.login && password === auth.password) { // Checking if credentials match
        return next();
    }

    res.set('WWW-Authenticate', 'Basic realm="401"'); // Setting response header for authentication challenge
    res.status(401).send('Authentication required.'); // Sending 401 status code for unauthorized access
};

// Middleware for request body validation
const validationMiddleware = ((req, res, next) => {
    if (req.method === 'POST' || req.method === 'PUT') {
        const { question, answer } = req.body; // Extracting question and answer from request body
        if (!question || question.trim() === '' || !answer || answer.trim() === '') {
        // Sending 400 status code for bad requests
            return res.status(400).json({ error: 'Question and answer fields cannot be empty' });
        }
    }
    next();
});

// Middleware for logging request activity
const logMiddleware = (req, res, next) => {
    const logEntry = `${new Date().toISOString()} - ${req.method} ${req.url}\n`; // Creating log entry
    fs.appendFile(logFile, logEntry, (err) => { // Appending log entry to the log file
        if (err) {
            console.error('Failed to write to log file:', err); // Loggin error message to console
        }
    });
    next();
};

server.use(authMiddleware);
server.use(validationMiddleware);
server.use(logMiddleware);

// Endpoint for searching questions
server.get('/search', (req, res) => {
    const { q } = req.query; // Extracting query parameter 'q' for search
    const categories = router.db.get('categories').value();
    const questions = categories.flatMap(category => category.questions); // Fetching questions from the database
    const filteredQuestions = questions.filter(question => // Filtering questions based on search query
        question.question && question.question.toLowerCase().includes(q.toLowerCase())
    );
    res.json(filteredQuestions); // Sending filtered questions as JSON response
});

// Endpoint for fetching paginated questions
server.get('/categories/:category/questions', (req, res, next) => {
    const { category } = req.param;
    let { page, limit } = req.query; // Extracting query parameters 'page' and 'limit' for pagination
    page = parseInt(page, 10) || 1; // Parsing page number as integer (default: 1)
    limit = parseInt(limit, 10) || 10; // Parsing limit as integer (default: 10)

    const questions = router.db.get('categories').find({name: category}).get('questions').value();
    const startIndex = (page - 1) * limit;
    const endIndex = page * limit;

    const paginatedQuestions = questions.slice(startIndex, endIndex).map(question => {
        const { id, question: text, options, answer } = question;
        return { id, text, options, answer};
    });
    res.json(paginatedQuestions);
});


server.use(middleware);
server.use(router);

const PORT = 3000;
server.listen(PORT, () => {
    console.log(`JSON Server is running on http://localhost:${PORT}`);
});