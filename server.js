const jsonServer = require('json-server');
const server = jsonServer.create();
const router = jsonServer.router('src/main/resources/db.json');
const middleware = jsonServer.defaults();
const bodyParser = require('body-parser');

server.use(bodyParser.json());
server.use(middleware);

server.post('/questions', (req, res, next) => {
    const { question, answer } = req.body;

    if(!question || !answer || question.trim() === "" || answer.trim() === "") {
        return res.status(400).json({ error: "Question and answer fields cannot be empty"});
    }

    next();
});

server.use((req, res, next) => {
    if(req.method === 'POST' && req.path === '/questions'){
        const db = router.db;
        const questions = db.get('questions').value();
        const newId = questions.length > 0 ? Math.max(...questions.map(q => parseInt(q.id, 10))) + 1 : 1;
        req.body.id = newId;
    }
    next();
});

server.use(router);

const PORT = 3000;
server.listen(PORT, () => {
    console.log(`JSON Server is running on http://localhost:${PORT}`);
});