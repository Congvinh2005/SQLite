# Tài liệu ứng dụng đăng nhập SQLite - Tiếng Việt

## Tổng quan
Đây là ứng dụng đăng nhập hoàn chỉnh sử dụng SQLite để lưu trữ thông tin người dùng. Ứng dụng bao gồm đăng nhập, đăng ký và màn hình chính sau khi đăng nhập.

## Cấu trúc package
- **com.example.sqlitedemo.ui**: Chứa các lớp giao diện người dùng
- **com.example.sqlitedemo.data**: Chứa các lớp quản lý dữ liệu
- **com.example.sqlitedemo**: Chứa điểm bắt đầu ứng dụng

## Các lớp chính và chức năng

### 1. LoginDatabase.java (Trong package data)
- **themDuLieuMauNeuChuaTonTai()**: Thêm dữ liệu mẫu vào database nếu chưa tồn tại
- **layThongTinNguoiDung(String username)**: Lấy thông tin người dùng từ database
- **insertUser(String username, String password, String fullname, String email)**: Thêm người dùng mới vào database
- **checkUser(String username, String password)**: Kiểm tra thông tin đăng nhập có hợp lệ không

### 2. LoginActivity.java (Trong package ui)
- **dangNhapNguoiDung()**: Thực hiện chức năng đăng nhập, so sánh với database
- **khoiTaoGiaoDien()**: Khởi tạo các thành phần giao diện
- **nhapTaiKhoan, nhapMatKhau**: Trường nhập tài khoản và mật khẩu
- **nutDangNhap**: Nút thực hiện đăng nhập

### 3. RegisterActivity.java (Trong package ui)
- **dangKyNguoiDung()**: Thực hiện chức năng đăng ký người dùng mới
- **khoiTaoGiaoDien()**: Khởi tạo các thành phần giao diện
- **nhapHoTen, nhapEmail, nhapTaiKhoan, nhapMatKhau**: Trường nhập thông tin đăng ký

### 4. HomeActivity.java (Trong package ui)
- **hienThiThongTinNguoiDung()**: Hiển thị thông tin người dùng sau khi đăng nhập
- **khoiTaoGiaoDien()**: Khởi tạo các thành phần giao diện

### 5. MainActivity.java
- Gọi phương thức thêm dữ liệu mẫu khi ứng dụng khởi động
- Chuyển hướng đến LoginActivity

## Dữ liệu mẫu trong database
- **Tài khoản admin**: 
  - Tài khoản: admin
  - Mật khẩu: 123456
- **Tài khoản user1**:
  - Tài khoản: user1
  - Mật khẩu: password1
- **Tài khoản test**:
  - Tài khoản: test
  - Mật khẩu: 123456

## Cơ chế hoạt động
1. Khi ứng dụng khởi động, MainActivity sẽ kiểm tra và thêm dữ liệu mẫu vào database nếu chưa tồn tại
2. Ứng dụng chuyển đến màn hình đăng nhập
3. Người dùng nhập tài khoản và mật khẩu
4. Ứng dụng so sánh với dữ liệu trong database
5. Nếu đúng: đăng nhập thành công và chuyển đến màn hình chính
6. Nếu sai: hiển thị thông báo lỗi

## Các thành phần giao diện đã đổi tên sang tiếng Việt
- editTextUsername -> nhapTaiKhoan (Trường nhập tài khoản)
- editTextPassword -> nhapMatKhau (Trường nhập mật khẩu)
- buttonLogin -> nutDangNhap (Nút đăng nhập)
- textViewRegister -> nhanDangKy (Nhãn chuyển đến đăng ký)
- textViewForgotPassword -> nhanQuenMatKhau (Nhãn quên mật khẩu)
- và các thành phần khác trong các activity