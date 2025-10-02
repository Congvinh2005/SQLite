# Ứng dụng Đăng Nhập SQLite

## Tổng quan

Đây là một ứng dụng Android hoàn chỉnh với chức năng đăng nhập/đăng ký sử dụng SQLite làm cơ sở dữ liệu nội bộ. Ứng dụng được xây dựng với kiến trúc sạch, phân chia rõ ràng giữa giao diện người dùng và lớp dữ liệu.

## Tính năng chính

- **Đăng nhập người dùng**: Xác thực người dùng với tài khoản và mật khẩu được lưu trong SQLite
- **Đăng ký người dùng mới**: Cho phép người dùng tạo tài khoản mới với tên đầy đủ, email, tài khoản và mật khẩu
- **Hiển thị thông tin người dùng**: Sau khi đăng nhập thành công, hiển thị thông tin chi tiết của người dùng
- **Xem thông tin hồ sơ người dùng**: Hiển thị tên đầy đủ và email
- **Đăng xuất**: Cho phép người dùng đăng xuất và trở về màn hình đăng nhập
- **Cơ sở dữ liệu mẫu**: Tự động thêm dữ liệu mẫu khi chạy ứng dụng lần đầu tiên

## Cấu trúc dự án

```
com.example.sqlitedemo/
├── ui/                    # Các lớp giao diện người dùng
│   ├── LoginActivity.java    # Màn hình đăng nhập
│   ├── RegisterActivity.java # Màn hình đăng ký
│   └── HomeActivity.java     # Màn hình chính sau đăng nhập
├── data/                  # Các lớp quản lý dữ liệu
│   ├── LoginDatabase.java    # SQLite helper cho đăng nhập
│   └── MyDatabase.java       # SQLite helper cho các chức năng khác
├── MainActivity.java      # Điểm bắt đầu ứng dụng
└── ...
```

## Cấu trúc cơ sở dữ liệu

### Bảng User
- **id**: INTEGER PRIMARY KEY AUTOINCREMENT
- **username**: TEXT UNIQUE (tài khoản duy nhất)
- **password**: TEXT (mật khẩu)
- **fullname**: TEXT (tên đầy đủ)
- **email**: TEXT (địa chỉ email)

## Dữ liệu mẫu

Ứng dụng bao gồm các tài khoản mẫu để thử nghiệm:

| Tài khoản | Mật khẩu | Họ tên | Email |
|-----------|----------|--------|-------|
| admin | 123456 | Admin User | admin@example.com |
| user1 | password1 | User One | user1@example.com |
| test | 123456 | Test User | test@example.com |

## Các lớp chính & chức năng

### 1. LoginDatabase.java (trong package `data`)
- `insertUser(username, password, fullname, email)`: Thêm người dùng mới
- `checkUser(username, password)`: Kiểm tra thông tin đăng nhập
- `layThongTinNguoiDung(username)`: Lấy thông tin người dùng
- `themDuLieuMauNeuChuaTonTai()`: Thêm dữ liệu mẫu nếu chưa tồn tại

### 2. LoginActivity.java (trong package `ui`)
- `dangNhapNguoiDung()`: Xử lý đăng nhập người dùng
- `khoiTaoGiaoDien()`: Khởi tạo các thành phần giao diện
- Hiển thị thông báo lỗi nếu tài khoản/mật khẩu không đúng
- Điều hướng đến màn hình đăng ký nếu người dùng chưa có tài khoản

### 3. RegisterActivity.java (trong package `ui`)
- `dangKyNguoiDung()`: Xử lý đăng ký người dùng mới
- Kiểm tra sự tồn tại của tài khoản trước khi đăng ký
- Hiển thị thông báo thành công hoặc thất bại

### 4. HomeActivity.java (trong package `ui`)
- Hiển thị thông tin người dùng sau khi đăng nhập
- Cung cấp nút đăng xuất để quay lại màn hình đăng nhập

### 5. MainActivity.java
- Thêm dữ liệu mẫu vào database khi ứng dụng khởi động
- Điều hướng người dùng đến màn hình đăng nhập

## Cách sử dụng

1. **Chạy ứng dụng**: Ứng dụng sẽ tự động thêm dữ liệu mẫu vào cơ sở dữ liệu
2. **Đăng nhập**: Sử dụng một trong các tài khoản mẫu hoặc đăng ký tài khoản mới
3. **Thử nghiệm**: Thử đăng nhập với tài khoản đúng và sai để xem thông báo
4. **Khám phá**: Sau khi đăng nhập, xem thông tin hồ sơ và thử đăng xuất

## Cơ chế bảo mật

- Kiểm tra tài khoản duy nhất trong quá trình đăng ký
- So sánh đầy đủ tài khoản và mật khẩu với cơ sở dữ liệu
- Không lưu mật khẩu ở dạng rõ trong code

## Kiến trúc & Thiết kế

- **Phân lớp rõ ràng**: Giao diện người dùng (UI) và quản lý dữ liệu (Data) được tách biệt
- **Code tiếng Việt**: Tên phương thức và biến đều được đặt bằng tiếng Việt để dễ hiểu
- **Tái sử dụng code**: Các phương thức được thiết kế để dễ tái sử dụng
- **Xử lý lỗi**: Có cơ chế xử lý lỗi và hiển thị thông báo phù hợp

## Yêu cầu hệ thống

- Android API 24 trở lên
- Android Studio
- Gradle 8.7.3
- Android Gradle Plugin 8.7.3

## Công nghệ sử dụng

- **Ngôn ngữ**: Java
- **Cơ sở dữ liệu**: SQLite
- **UI Framework**: Android SDK, Material Design Components
- **Kiến trúc**: Package structure (ui, data)

## Các tệp tài nguyên

- `activity_login.xml` - Giao diện đăng nhập
- `activity_register.xml` - Giao diện đăng ký
- `activity_home.xml` - Giao diện màn hình chính
- `colors.xml` - Định nghĩa màu sắc
- `AndroidManifest.xml` - Khai báo hoạt động và quyền

## Ghi chú phát triển

- Dữ liệu được lưu trữ cục bộ trên thiết bị
- Cơ sở dữ liệu tự động tạo nếu chưa tồn tại
- Ứng dụng sẽ thêm dữ liệu mẫu nếu chưa có dữ liệu nào
- Có thể dễ dàng mở rộng thêm các tính năng như quên mật khẩu, xác thực OTP, v.v.

## Tác giả

Dự án được phát triển như một ứng dụng mẫu để học tập và thử nghiệm chức năng đăng nhập với SQLite.

## Giấy phép

[MIT License](LICENSE)