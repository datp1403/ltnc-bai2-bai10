# Kết quả kiểm tra tại thời điểm chuẩn bị bộ bài

Ngày chuẩn bị: 2026-11-07.

## Đã kiểm tra được

- Có cấu trúc Maven cho BankSystem (Bài 2–9), ShippingCalculator (Bài 10).
- Cả ba file GitHub Actions workflow được kiểm tra cú pháp YAML và vị trí thư mục `.github/workflows/`.
- POM Maven được phân tích XML (cú pháp cấu trúc).
- **Toàn bộ Java của hai dự án biên dịch với `javac --release 17` khi dùng API giả lập cho SLF4J và JUnit.**
- **23/23 phương thức test BankSystem** trong ba lớp `BankSystemTest`, `BankSystemExtendedTest`, `PathCompatibilityTest` đều qua trong trình chạy phản chiếu cục bộ với API JUnit giả lập.
- **4/4 phương thức test ShippingCalculator** qua cùng trình chạy cục bộ.
- Không có dòng Java chính vượt quá 100 ký tự theo phép kiểm tra đơn giản.

## Chưa kiểm tra được (phải làm trước khi nộp)

- Chưa chạy Maven thực `mvn clean verify`, `mvn checkstyle:check` do môi trường chuẩn bị không có lệnh `mvn`, không tải được phụ thuộc từ Maven Central.
- Chưa có % JaCoCo đo được; **không được ghi coverage >=80% cho đến khi chạy thật**.
- Chưa xác nhận JAR thực thi thực tế từ Maven Shade.
- Chưa có GitHub Actions run, job xanh/đỏ, thông tin caching, bình luận PR, hay Branch Protection vì chưa có repository của sinh viên.
- Dữ liệu tại `EVIDENCE/` chỉ là vị trí để bổ sung minh chứng, **không phải chứng cứ đã chạy**.

Các kiểm tra cục bộ không thay thế Maven/JUnit/JaCoCo/Checkstyle thực; nếu GitHub workflow báo lỗi cần sửa và chạy lại, không tắt công cụ để vượt lỗi.
