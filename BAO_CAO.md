# Báo cáo thực hành Bài 2–10

## 1. Giới thiệu

Mình làm các bài này bằng Java 17 và Maven. Thay vì tạo lại một ứng dụng cho mỗi bài, mình dùng dự án BankSystem ở bài 2 để làm tiếp các bài 3–9. Bài 10 dùng ShippingCalculator, đúng với mã nguồn trong đề.

## Bài 2 – Checkstyle và Logging

Đầu tiên mình cấu hình `maven-checkstyle-plugin` trong `pom.xml`, dùng `google_checks.xml`. Sau đó sửa lại code BankSystem cho thống nhất cách đặt tên, import và định dạng, nhưng không đổi cách nạp/rút tiền. Mình cũng thêm SLF4J + Logback để ghi lại lúc giao dịch thành công, bị từ chối hoặc gặp lỗi.

Mình dùng `INFO` cho hoạt động bình thường, `WARN` cho thao tác không hợp lệ, `ERROR` cho lỗi đọc dữ liệu và `DEBUG` cho thông tin kiểm tra chi tiết. Số tài khoản được che bớt khi ghi log.

**Kết quả:** Trong [log CI BankSystem](https://github.com/datp1403/ltnc-bai2-bai10/actions/runs/42334223051), Checkstyle báo **0 violations**.

## Bài 3 – GitHub Actions

Mình tạo `.github/workflows/ci.yml`, cho chạy khi `push` hoặc mở `pull_request` vào `main`. Workflow cài Java 17 rồi gọi `mvn clean verify` để kiểm tra code, chạy test và đóng gói JAR. Sau đó dùng `upload-artifact` để lưu JAR.

**Kết quả:** [Lần chạy CI đầu tiên](https://github.com/datp1403/ltnc-bai2-bai10/actions/runs/42334223051) đã thành công và có JAR artifact. Mình chưa tạo riêng lần chạy thất bại có chủ đích cho bài này.

## Bài 4 – Chạy trên nhiều hệ điều hành

Mình thêm `matrix` gồm `ubuntu-latest`, `windows-latest` và `macos-latest`. Cả ba đều chạy cùng bộ test nên có thể kiểm tra sự khác nhau giữa hệ điều hành. Trong code, mình dùng `Path.resolve()` thay vì ghép đường dẫn bằng ký tự `\` cố định.

**Kết quả:** Ba job trong [cùng lần chạy CI](https://github.com/datp1403/ltnc-bai2-bai10/actions/runs/42334223051) đều thành công. Ví dụ code gây lỗi đường dẫn nằm trong `scenarios/bai4/`; chưa có lần chạy đỏ để minh chứng lỗi này.

## Bài 5 – JaCoCo

Mình thêm plugin JaCoCo vào `pom.xml`. Pha `verify` vừa tạo báo cáo coverage vừa kiểm tra tỷ lệ dòng code được test phải đạt **từ 80%**. Nếu không đạt thì Maven sẽ báo lỗi. File báo cáo nằm trong `target/site/jacoco/index.html` và còn được upload trên GitHub.

**Kết quả:** Bước kiểm tra JaCoCo trong CI đã chạy qua. Mình chưa điền tỷ lệ % cụ thể vì cần xem bảng số liệu trong báo cáo HTML, không tự đoán con số.

## Bài 6 – Maven cache

Trong `actions/setup-java` mình bật `cache: maven` để các lần chạy sau không phải tải lại toàn bộ thư viện. Có thể kiểm tra thông báo khôi phục cache trong log của GitHub Actions.

**Cần bổ sung:** So sánh thời gian trước và sau bật cache bằng hai lần chạy thực tế, có ảnh log làm bằng chứng.

## Bài 7 – Review qua Pull Request

Mình tạo workflow `pr-checkstyle.yml` dùng `dbelyaev/action-checkstyle` để báo lỗi phong cách code trên PR. Muốn chặn merge khi CI/test lỗi thì còn phải thiết lập Branch Protection (hoặc Ruleset) cho nhánh `main`.

**Cần bổ sung:** Mở PR có lỗi cố ý, lưu ảnh bình luận của bot và xác nhận quy tắc chặn merge. Chưa có minh chứng thực tế cho phần này.

## Bài 8 – Đóng gói JAR

Mình dùng `maven-jar-plugin` để khai báo `Main-Class` là `bai2.BankSystemDemo`, và `maven-shade-plugin` để cho các thư viện logging vào JAR. Maven lưu sản phẩm build trong thư mục `target`.

Lệnh chạy thử trên máy:

```bash
mvn clean package
java -jar target/bank-system-1.0-SNAPSHOT.jar
```

**Kết quả:** GitHub CI đã tạo JAR artifact; mình chưa có ảnh terminal chạy lệnh `java -jar` trên máy để đính kèm.

## Bài 9 – Logging

Mình không dùng `System.out.println()` cho thông báo chạy chương trình mà dùng `LOGGER.info()`, `warn()`, `error()` hoặc `debug()`. Những câu log có dữ liệu được viết với `{}` thay vì nối chuỗi. `logback.xml` vừa in ra console vừa ghi vào `logs/bank-system.log`.

**Cần bổ sung:** Ảnh console và file log sau khi chạy chương trình ở máy cá nhân.

## Bài 10 – The Broken Pipeline

Đề cho ShippingCalculator và yêu cầu tìm các lỗi CI. Mình tách thành thư mục Maven riêng để dễ theo dõi. Bản đang nằm trên nhánh `main` là bản đã sửa: có bước checkout, phiên bản Logback hợp lệ và Surefire hỗ trợ JUnit 5.

**Kết quả:** [Shipping CI](https://github.com/datp1403/ltnc-bai2-bai10/actions/runs/42334223035) báo `BUILD SUCCESS` và 4 test đạt. Các trường hợp lỗi gốc và lỗi tự tạo được ghi trong `Bai10-Broken-Pipeline/scenarios/`; chưa chạy đầy đủ chuỗi đỏ → sửa → xanh để chụp bằng chứng theo đề.

## 2. Kết quả hiện tại và việc còn thiếu

- BankSystem: Checkstyle 0 lỗi, 23/23 test đạt, CI xanh trên cả ba hệ điều hành.
- ShippingCalculator: 4/4 test đạt, CI xanh.
- Đã upload được JAR và báo cáo JaCoCo lên GitHub Actions.
- Phần **chưa hoàn tất minh chứng**: tạo các lần chạy thất bại có chủ đích, chụp log trước/sau sửa, đo chênh lệch thời gian cache và kiểm tra PR/branch protection.

Mình sẽ điền ảnh và đường dẫn các run lỗi vào báo cáo sau khi thực hành trực tiếp trên GitHub; không dùng ảnh hoặc log giả.
