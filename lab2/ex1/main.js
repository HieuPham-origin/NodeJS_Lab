const http = require('http');
const url = require('url');

const server = http.createServer((req, res) => {
    if (req.method === 'GET' && req.url === '/') {
        res.writeHead(200, { 'Content-Type': 'text/html' });
        res.write(`
      <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Document</title>
        </head>
        <body>
          <form action="/result" method="get">
            <div style="display: flex">
                <p>Số hạng 1</p>
                <input type="number" name="num1" placeholder="Enter first number" required>
            </div>
            <div style="display: flex">
                <p>Số hạng 2</p>
                <input type="number" name="num2" placeholder="Enter first number" required>
            </div>
            <div style="display: flex">
                <p>Phép tính</p>
                <select name="operation">
                    <option value="" disabled selected hidden>Chọn phép tính</option>
                    <option value="+">+</option>
                    <option value="-">-</option>
                    <option value="*">*</option>
                    <option value="/">/</option>
                </select>
            </div>
            <button type="submit">Tính</button>
          </form>
        </body>
      </html>
    `);
        res.end();
    } else if (req.method === 'GET' && req.url.startsWith('/result')) {
        const queryObject = url.parse(req.url, true).query;
        console.log(queryObject)
        const num1 = parseFloat(queryObject.num1);
        const num2 = parseFloat(queryObject.num2);
        const operation = queryObject.operation;
        console.log(operation)
        let result;

        if (isNaN(num1) || isNaN(num2)) {
            res.writeHead(400, { 'Content-Type': 'text/html' });
            res.write(`
          <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
            </head>
            <body>
              <p>Bạn chưa nhập số hạng.</p>
              <a href="/">Quay lại</a>
            </body>
          </html>
        `);
            res.end();
        } else if (!operation) {
            res.writeHead(400, { 'Content-Type': 'text/html' });
            res.write(`
          <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
            </head>
            <body>
              <p>Bạn chưa chọn phép toán.</p>
              <a href="/">Quay lại</a>
            </body>
          </html>
        `);
            res.end();
        } else {
            switch (operation) {
                case '+':
                    result = num1 + num2;
                    break;
                case '-':
                    result = num1 - num2;
                    break;
                case '*':
                    result = num1 * num2;
                    break;
                case '/':
                    result = num2 !== 0 ? num1 / num2 : 'Không thể chia cho 0';
                    break;
                default:
                    result = 'Invalid operation';
            }
            res.writeHead(200, { 'Content-Type': 'text/html' });
            res.write(`
          <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
            </head>
            <body>
              <h1>Kết quả</h1>
              <p>${num1} ${operation} ${num2} = <b>${result}</b></p>
              <a href="/">Quay lại</a>
            </body>
          </html>
        `);
            res.end();
        }
    } else {
        res.writeHead(404, { 'Content-Type': 'text/html' });
        res.write('<h1>Đường dẫn không hợp lệ</h1>');
        res.end();
    }
});

server.listen(80, () => {
    console.log('Server running at http://localhost:80/');
});