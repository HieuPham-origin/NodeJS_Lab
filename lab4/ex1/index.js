const express = require('express')
const hbs = require('express-handlebars')
const app = express()
const https = require("https")
const session = require('express-session'); 
require("dotenv").config();
var bodyParser = require('body-parser')
app.use(bodyParser.urlencoded({ extended: true }));
// .env
const PORT = process.env.PORT;
const EMAIL = process.env.EMAIL;
console.log(EMAIL)
const PASSWORD = process.env.PASSWORD;

app.engine('handlebars', hbs.engine({
    defaultLayout: 'main',
    helpers: {
        eq: (a, b) => a === b,
        add: (a, b) => a + b,
        subtract: (a, b) => a - b
    }
}));

app.use(session({
    secret: 'mysecret',  
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false } 
}));

app.set('view engine', 'handlebars')

var users = []

// GET '/'
app.get('/', (req, res) => {
    if (!req.session.user) {
        res.redirect('/login');
    } else {
        const page = req.query.page ? parseInt(req.query.page) : 1
        const perPage = 20
        https.get(`https://gorest.co.in/public/v2/users?page=${page}&per_page=${perPage}`, (response) => {
            let data = ''
            response.on('data', (chunk) => {
                data += chunk;
            });
            response.on('end', ()=> {
                try {
                    users = JSON.parse(data);
                } catch(e){
                    console.error(e);
                }
                res.render('home', { user: req.session.user, users: users });
            })
        })
    }
});

// login
app.get('/login', (req, res) => {
    res.render('login', { error: null });
})

//Method GET route '/add' Hiển thị giao diện
app.get('/add', (req, res) => {
    res.render('add', { error: null });
})

//Method POST route '/login'. Thực hiện đăng nhập và redirect về home
app.post('/login', (req, res) => {
    const { email, password } = req.body;
    console.log(email);
    console.log(EMAIL);
    if (email === EMAIL && password === PASSWORD) {
        req.session.user = { email };
        console.log(req.session.user);
        res.redirect('/');
    } else {
        res.render('login', { error: 'Email hoặc mật khẩu không đúng!' });
    }
});

// 404
app.use((req, res) => {
    res.status(404)
    res.render('404')
})
// 500
app.use((err, req, res, next) => {
    console.error(err.message)
    res.status(500)
    res.render('500')
})

app.listen(PORT, () =>
    console.log(
        'Express started on http://localhost:' + PORT + '; ' +
        'press Ctrl-C to terminate. '))