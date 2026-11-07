# Cách chạy và lấy ảnh nộp bài

## Chạy trên máy

1. Clone repo hoặc tải ZIP từ GitHub, mở thư mục gốc bằng IntelliJ.
2. Chọn JDK 17, tải lại Maven nếu IntelliJ hỏi.
3. Mở Terminal ở thư mục có `pom.xml` rồi chạy `mvn clean verify`.
4. Chạy `mvn checkstyle:check` để xem kết quả bài 2.
5. Chạy `java -jar target/bank-system-1.0-SNAPSHOT.jar` cho bài 8–9.
6. Mở `logs/bank-system.log` và `target/site/jacoco/index.html` để xem log, coverage.

Bài 10 ở thư mục con, phải chạy `cd Bai10-Broken-Pipeline` trước khi dùng Maven.

## Xem GitHub Actions

Vào tab [Actions](https://github.com/datp1403/ltnc-bai2-bai10/actions), chọn `BankSystem CI - Bai 3-6-8` hoặc `Shipping CI - Bai 10` để xem các job. Nhấn vào job để xem từng bước. Cuối trang run có phần Artifacts để tải JAR và báo cáo JaCoCo.

Khi cần minh chứng bài 3, 4, 7 và 10, mình sẽ làm theo cách:

1. Tạo nhánh thử nghiệm, chỉ thêm **một lỗi** từ thư mục `scenarios/`.
2. Push lên GitHub (mở PR vào `main` nếu cần thử workflow PR).
3. Chụp ảnh job đỏ và copy đoạn log chỉ rõ lỗi.
4. Sửa lỗi, push lại, chụp job xanh.
5. Không đưa lỗi cố ý vào nhánh `main` khi nộp bản cuối.

Riêng bài 6 cần lưu thời gian hai lần chạy, xem log có dòng `Cache restored` / `Cache hit` rồi mới so sánh. Bài 7 còn phải bật Ruleset/Branch Protection trên GitHub để kiểm tra chặn merge.

**Lưu ý:** Có workflow và có code mẫu gây lỗi chưa được tính là đã làm xong phần minh chứng. Phải chạy thật thì mới có ảnh/log để nộp.
