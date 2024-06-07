module.exports = (req, res, next) => {
    if(req.method === 'POST' || req.method === 'PUT'){
        const { question, answer } = req.body;

        if(!question || !answer){
            return res.status(400).json({ error: "Both question and answer fields are required." });
        }

        if(question.trim() === '' || answer.trim() === ''){
            return res.status(400).json({ error: "Question and answer cannot be empty."});
        }
    }
    next();
};