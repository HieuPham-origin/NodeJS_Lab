const http = require('http');
const fs = require('fs');

const server = http.createServer((req, res) => {
    if (req.method === 'GET' && req.url === '/') {
        fs.readFile('index.html', (err, data) => {
            if (err) {
                res.writeHead(500, { 'Content-Type': 'text/html' });
                res.end('<h1>500 Internal Server Error</h1>');
            } else {
                res.writeHead(200, { 'Content-Type': 'text/html' });
                res.end(data);
            }
        });
    } else if (req.method === 'POST' && req.url === '/login') {
        let body = '';

        req.on('data', chunk => {
            body += chunk.toString();
        });

        req.on('end', () => {
            const formData = new URLSearchParams(body);
            const emailInput = formData.get('email');
            const passwordInput = formData.get('password');
            const password = 'userpassword123'
            if (passwordInput === password) {
                res.writeHead(200, { 'Content-Type': 'text/html' });
                res.end(`<html>
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>Document</title>
                                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
                                    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
                            </head>
                            <body>
                                <h1>Đăng nhập thành công</h1><a href="/">Quay lại</a>
                            </body>
                        </html>`);
            } else if (passwordInput != password) {
                res.writeHead(200, { 'Content-Type': 'text/html' });
                res.end(`<html>
                            <head>
                                <meta charset="UTF-8">
                                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                <title>Document</title>
                                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
                                    integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
                            </head>
                            <body>
                                <h1>Mật khẩu không hợp lệ</h1>
                                <a href="/">Quay lại</a>
                            </body>
                        </html>`);
            }
        });
    } else if (req.method === 'GET' && req.url === '/login') {
        res.writeHead(200, { 'Content-Type': 'text/html' });
        res.end(`<html>
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Document</title>
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
                            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
                    </head>
                    <body>
                        <h1>Phương thức GET không được hỗ trợ</h1>
                        <a href="/">Quay lại</a>
                    </body>
                </html>`);
    } else {
        res.writeHead(404, { 'Content-Type': 'text/html' });
        res.end(`<html>
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Document</title>
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
                            integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
                    </head>
                    <body>
                        <h1>Đường dẫn không hợp lệ</h1>
                        <a href="/">Quay lại</a>
                    </body>
                </html>`);
    }
});

server.listen(80, () => {
    console.log('Server running at http://localhost:80/');
});
