const jsonServer = require('json-server');
const server = jsonServer.create();
const router = jsonServer.router('src/main/resources/db.json');
const middleware = jsonServer.defaults();
const fs = require('fs');
const logFile = 'activity.log';
const bodyParser = require('body-parser');

server.use(bodyParser.json());

const authMiddleware = (req, res, next) => {
    const auth = { login: 'admin', password: 'password' };
    const b64auth = (req.headers.authorization || '').split(' ')[1] || '';
    const [login, password] = Buffer.from(b64auth, 'base64').toString().split(':');

    if (login && password && login === auth.login && password === auth.password) {
        return next();
    }

    res.set('WWW-Authenticate', 'Basic realm="401"');
    res.status(401).send('Authentication required.');
};

const validationMiddleware = ((req, res, next) => {
    if (req.method === 'POST' || req.method === 'PUT') {
        const { question, answer } = req.body;
        if (!question || question.trim() === '' || !answer || answer.trim() === '') {
            return res.status(400).json({ error: 'Question and answer fields cannot be empty' });
        }
    }
    next();
});

const logMiddleware = (req, res, next) => {
    const logEntry = `${new Date().toISOString()} - ${req.method} ${req.url}\n`;
    fs.appendFile(logFile, logEntry, (err) => {
        if (err) {
            console.error('Failed to write to log file:', err);
        }
    });
    next();
};

server.use(authMiddleware);
server.use(validationMiddleware);
server.use(logMiddleware);

server.get('/search', (req, res) => {
    const { q } = req.query;
    const questions = router.db.get('questions').value();
    const filteredQuestions = questions.filter(question =>
        question.question && question.question.toLowerCase().includes(q.toLowerCase())
    );
    res.json(filteredQuestions);
});

server.get('/questions', (req, res, next) => {
    let { page, limit } = req.query;
    page = parseInt(page, 10) || 1;
    limit = parseInt(limit, 10) || 10;

    const questions = router.db.get('questions').value();
    const startIndex = (page - 1) * limit;
    const endIndex = page * limit;

    const paginatedQuestions = questions.slice(startIndex, endIndex);
    res.json(paginatedQuestions);
});


server.use(middleware);
server.use(router);

const PORT = 3000;
server.listen(PORT, () => {
    console.log(`JSON Server is running on http://localhost:${PORT}`);
});