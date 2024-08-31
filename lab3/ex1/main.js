// // Nhập liệu
// process.stdin.resume();
// process.stdin.setEncoding("utf-8");

// var input_data = "";
// process.stdin.on("data", function(input) {
//   input_data += input; // Đọc đầu vào từ STDIN
// });

// // Lắng nghe sự kiện 'end' khi kết thúc nhập liệu
// process.stdin.on("end", function() {
//     console.log(input_data)
//     calculateAvg(input_data); // Gọi hàm tính toán trung bình
//     process.exit();
// });

// function print(input) {
//   process.stdout.write(input + '\n'); // In ra kết quả
// }

// function calculateAvg(input) {
//     // Tách dữ liệu đầu vào thành mảng các số
//     var numbers = input.trim().split(/\s+|,/); // Tách bằng khoảng trắng hoặc dấu phẩy
//     if (numbers.length !== 2) {
//         print("nhap sai");
//         return;
//     }

//     // Chuyển đổi hai số từ chuỗi sang số
//     var num1 = parseFloat(numbers[0]);
//     var num2 = parseFloat(numbers[1]);

//     // Kiểm tra xem cả hai đều là số hợp lệ
//     if (isNaN(num1) || isNaN(num2)) {
//         print("nhap sai");
//         return;
//     }

//     // Tính trung bình của hai số
//     var avg = (num1 + num2) / 2;

//     // In kết quả
//     print(avg);
// }

// // const readline = require('readline');

// // const rl = readline.createInterface({
// //     input: process.stdin,
// //     output: process.stdout
// // });

// // rl.question('', (answer) => {
// //     calculateAvg(answer);
// //     rl.close();
// // });

// // function print(input) {
// //     process.stdout.write(input + '\n'); // In ra kết quả
// // }

// // function calculateAvg(input) {
// //     // Tách dữ liệu đầu vào thành mảng các số
// //     var numbers = input.trim().split(/\s+|,/); // Tách bằng khoảng trắng hoặc dấu phẩy
// //     if (numbers.length !== 2) {
// //         print("nhap sai");
// //         return;
// //     }

// //     // Chuyển đổi hai số từ chuỗi sang số
// //     var num1 = parseFloat(numbers[0]);
// //     var num2 = parseFloat(numbers[1]);

// //     // Kiểm tra xem cả hai đều là số hợp lệ
// //     if (isNaN(num1) || isNaN(num2)) {
// //         print("nhap sai");
// //         return;
// //     }

// //     // Tính trung bình của hai số
// //     var avg = (num1 + num2) / 2;

// //     // In kết quả
// //     print(avg);
// // }

process.stdin.on('data', data => { 
    console.log(`You typed ${data.toString()}`); 
    process.exit(); 
  });

  