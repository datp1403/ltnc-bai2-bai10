# Bài 10 – The Broken Pipeline

Đây là dự án ShippingCalculator theo đề Google Docs. Thư mục hiện tại là bản **đã sửa** để chạy được JUnit 5 với Maven.

## Các lỗi cần kiểm tra lần lượt

1. **Thiếu bước checkout:** xóa bước checkout trong `shipping-ci.yml` rồi chạy thử, quan sát việc không tìm thấy mã nguồn hoặc `pom.xml`. Sau đó thêm lại `actions/checkout`.
2. **Sai phiên bản Logback:** đổi `logback-classic` thành `9.9.9`, đọc log lỗi không tìm thấy dependency, sau đó đưa về `1.4.11`.
3. **Surefire quá cũ:** đổi từ `3.2.5` về `2.12.4`, xem JUnit 5 có được phát hiện không. Nếu Maven không báo đỏ mà chạy 0 test, phải ghi đúng đây là lỗi không chạy test, không được nói là build fail.
4. **Lỗi tự tạo:** sửa expected ở test STANDARD từ `15000.0` thành `16000.0`. Test sẽ sai; sửa lại để pipeline xanh.

Mình đặt các đoạn code cần dùng trong thư mục `scenarios/` để không phá bản đang nộp. Mỗi lần thử chỉ tạo một lỗi, lưu URL run và ảnh trước/sau sửa.

Chạy riêng dự án này:

```bash
mvn -DfailIfNoTests=true clean verify
```

[Shipping CI đang chạy thành công](https://github.com/datp1403/ltnc-bai2-bai10/actions/runs/42334223035). **Các lần chạy đỏ theo yêu cầu chưa được thực hiện đầy đủ**, cần bổ sung trước khi nộp.
