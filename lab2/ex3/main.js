const http = require('http');
const fs = require('fs');
const path = require('path');

const filePath = path.join(__dirname, 'students.json');

const readStudentsFromFile = () => {
    const data = fs.readFileSync(filePath);
    console.log(JSON.parse(data))
    return JSON.parse(data);
};

const writeStudentsToFile = (students) => {
    const fileContent = fs.readFileSync(filePath, 'utf-8');
    const parsedContent = JSON.parse(fileContent);
    
    parsedContent.data = students;
    
    fs.writeFileSync(filePath, JSON.stringify(parsedContent, null, 2));
};

const server = http.createServer((req, res) => {
    const { method, url } = req;
    console.log(url)
    const urlParts = url.split('/');
    console.log(urlParts)
    const id = parseInt(urlParts[2]);

    if (url === '/students' && method === 'GET') {
        const studentsJSON = readStudentsFromFile();
        const students = studentsJSON.data;
        res.writeHead(200, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({
            success: true,
            message: "Lấy danh sách thành công",
            data: students
        }));
    } else if (url === '/students' && method === 'POST') {
        let body = '';
        req.on('data', chunk => {
            body += chunk.toString();
        });
        req.on('end', () => {
            const studentsJSON = readStudentsFromFile();
            const students = studentsJSON.data;
            console.log(students);
            const data = JSON.parse(body);
            data.id = students.length ? students[students.length - 1].id + 1 : 1;
            const newStudent = { id: data.id, name: data.name, age: data.age};
            students.push(newStudent);

            writeStudentsToFile(students); 
            res.writeHead(201, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                success: true,
                message: "Thêm sinh viên thành công",
                data: newStudent
            }));
        });
    } else if (urlParts[1] === 'students' && !isNaN(id) && method === 'GET') {
        const studentsJSON = readStudentsFromFile();
        const students = studentsJSON.data;
        const student = students.find(s => s.id === id);
        
        if (student) {
            res.writeHead(200, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                success: true,
                message: "Lấy thông tin sinh viên thành công",
                data: student
            }));
        } else {
            res.writeHead(404, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                success: false,
                message: "Không tìm thấy sinh viên",
            }));
        }
    } else if (urlParts[1] === 'students' && !isNaN(id) && method === 'PUT') {
        let body = '';
        req.on('data', chunk => {
            body += chunk.toString();
        });
        req.on('end', () => {
            const studentsJSON = readStudentsFromFile();
            const students = studentsJSON.data;
            const updatedStudent = JSON.parse(body);
            const studentIndex = students.findIndex(s => s.id === id);
            if (studentIndex !== -1) {
                students[studentIndex] = { id, ...updatedStudent };
                writeStudentsToFile(students);
                res.writeHead(200, { 'Content-Type': 'application/json' });
                res.end(JSON.stringify({
                    success: true,
                    message: "Cập nhật thông tin sinh viên thành công",
                    data: students[studentIndex]
                }));
            } else {
                res.writeHead(404, { 'Content-Type': 'application/json' });
                res.end(JSON.stringify({
                    success: false,
                    message: "Không tìm thấy sinh viên",
                }));
            }
        });
    } else if (urlParts[1] === 'students' && !isNaN(id) && method === 'DELETE') {
        const studentsJSON = readStudentsFromFile();
        const students = studentsJSON.data;
        const studentIndex = students.findIndex(s => s.id === id);
        if (studentIndex !== -1) {
            students.splice(studentIndex, 1);
            writeStudentsToFile(students);
            res.writeHead(200, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                success: true,
                message: "Xóa sinh viên thành công",
            }));
        } else {
            res.writeHead(404, { 'Content-Type': 'application/json' });
            res.end(JSON.stringify({
                success: false,
                message: "Không tìm thấy sinh viên",
            }));
        }
    } else {
        res.writeHead(404, { 'Content-Type': 'application/json' });
        res.end(JSON.stringify({
            success: false,
            message: 'Endpoint không hợp lệ',
        }));
    }
});

server.listen(80, () => {
    console.log('Server is running at http://localhost:80');
});
