# Bài tập Lập trình nâng cao - Bài 2 đến Bài 10

Repo này mình dùng để làm các bài thực hành về Maven, kiểm tra code và GitHub Actions.
Mình dùng **BankSystem** cho bài 2 đến bài 9 vì các bài có liên quan với nhau. Bài 10 dùng **ShippingCalculator** theo đề riêng.

## Có những gì trong repo?

- `src/main/java/bai2`: chương trình ngân hàng và các lớp liên quan.
- `src/test/java/bai2`: test cho các thao tác nạp, rút tiền, đọc dữ liệu khách hàng và xử lý đường dẫn.
- `pom.xml`: Maven, Checkstyle, JUnit, JaCoCo và đóng gói JAR.
- `.github/workflows/`: các workflow GitHub Actions.
- `Bai10-Broken-Pipeline/`: dự án của bài 10.
- `scenarios/`: các ví dụ để thử gây lỗi rồi sửa lại theo yêu cầu.
- `BAO_CAO.md`: mình ghi lại cách làm và kết quả của từng bài.

## Cách chạy

Cần JDK 17 và Maven. Mở terminal tại thư mục repo và chạy:

```bash
mvn clean verify
mvn checkstyle:check
java -jar target/bank-system-1.0-SNAPSHOT.jar
```

Bài 10 chạy riêng:

```bash
cd Bai10-Broken-Pipeline
mvn clean verify
```

File log BankSystem được lưu tại `logs/bank-system.log`. Báo cáo JaCoCo nằm ở `target/site/jacoco/index.html`.

## Kết quả mình đã kiểm tra trên GitHub

Ngày 07/11/2026, GitHub Actions chạy thành công:

- [BankSystem CI](https://github.com/datp1403/ltnc-bai2-bai10/actions/runs/42334223051): Ubuntu, Windows và macOS đều xanh. Checkstyle 0 lỗi, 23 test đạt, bước kiểm tra JaCoCo đạt ngưỡng cấu hình.
- [Shipping CI](https://github.com/datp1403/ltnc-bai2-bai10/actions/runs/42334223035): build thành công, 4 test đạt.

Mình vẫn cần lưu ảnh minh chứng cho các lần **cố tình tạo lỗi rồi sửa** ở bài 3, 4, 7 và 10; phần này không thể thay bằng ảnh tự tạo. Hướng dẫn xem thêm trong `CACH_CHAY.md`.
