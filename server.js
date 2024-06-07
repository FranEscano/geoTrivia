const jsonServer = require('json-server');
const server = jsonServer.create();
const router = jsonServer.router('src/main/resources/db.json');
const middleware = jsonServer.defaults();
const validate = require('./middleware/validate');

server.use(middleware);
server.use(jsonServer.bodyParser);
server.use(validate);
server.use(router);

server.listen(3000, () => {
    console.log('JSON Server is running');
});