const express = require('express')
const hbs = require('express-handlebars')
const app = express()
const session = require('express-session'); 
require("dotenv").config();
var bodyParser = require('body-parser')
// .env
const PORT = process.env.PORT;
const EMAIL = process.env.EMAIL;
const PASSWORD = process.env.PASSWORD;

//body parser
app.use(bodyParser.urlencoded({ extended: true }));

app.use(session({
    secret: 'mysecret',  
    resave: false,
    saveUninitialized: true,
    cookie: { secure: false } 
}));

const products = [
    { "id": 1, "name": "Iphone XS", "price": 1999 },
    { "id": 2, "name": "Iphone 12 Pro", "price": 1399 },
    { "id": 3, "name": `Macbook Pro 13" M1`, "price": 1299 },
    { "id": 4, "name": "Airpod Pro", "price": 499 }
];

// handlebar config
app.engine('handlebars', hbs.engine({
    defaultLayout: 'main',
    helpers: {
        formatPrice: (price) => {
            return price.toLocaleString('en-US'); 
        }
    }
}));
app.set('view engine', 'handlebars')

// GET '/'
app.get('/', (req, res) => {
    if (!req.session.user) {
        res.redirect('/login');
    } else {
        res.render('home', { user: req.session.user, products: products });
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

//Method POST route '/add'. Thực hiện thêm mới sản phẩm
app.post('/add', (req, res) => {
    const { name, price } = req.body;
    
    if (!name || !price) {
        return res.render('add', { error: 'Vui lòng nhập đầy đủ thông tin sản phẩm!' });
    }

    const newProduct = {
        id: products.length + 1,
        name,
        price: parseFloat(price)
    };

    products.push(newProduct);
    res.redirect('/');
});


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


//Method GET route '/:id'. Lấy thông tin product theo số id
app.get("/:id", (req, res) => {
    const productId = parseInt(req.params.id, 10);
    const product = products.find(p => p.id === productId);

    if (product) {
        res.render('detail', { product });
    } else {
        res.status(404).render('404');
    }
});

// Route GET '/edit/:id'. Hiển thị giao diện chỉnh sửa sản phẩm
app.get('/edit/:id', (req, res) => {
    const productId = parseInt(req.params.id);
    const product = products.find(p => p.id === productId);
    if (product) {
        res.render('edit', { product });
    } else {
        res.status(404).send('Sản phẩm không tồn tại.');
    }
});

// Route POST '/edit/:id'. Xử lý cập nhật sản phẩm
app.post('/edit/:id', (req, res) => {
    const productId = parseInt(req.params.id);
    const { name, price } = req.body;
    const productIndex = products.findIndex(p => p.id === productId);
    if (productIndex !== -1) {
        products[productIndex].name = name;
        products[productIndex].price = parseFloat(price);
        res.redirect('/');
    } else {
        res.status(404).send('Sản phẩm không tồn tại.');
    }
});

// Route GET '/delete/:id'. Xóa sản phẩm
app.get('/delete/:id', (req, res) => {
    const productId = parseInt(req.params.id);
    const productIndex = products.findIndex(p => p.id === productId);
    if (productIndex !== -1) {
        products.splice(productIndex, 1);
        res.redirect('/');
    } else {
        res.status(404).send('Sản phẩm không tồn tại.');
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