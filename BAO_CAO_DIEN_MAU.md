# Bao cao thuc hanh Bài 2–10 (mau de dien ket qua thuc te)

> **Canh bao:** Day la mau. Chua co bang chung GitHub Actions, khong duoc viet "da xanh" khi chua chay.

## Bai 2: Checkstyle
- Tool: maven-checkstyle-plugin 3.3.0; Google Style.
- Lenh: `mvn checkstyle:check`.
- Anh/log BUILD SUCCESS: [chen sau khi chay]
- Refactor: dat ten ro nghia, chuan hoa code, giu logic nhan/rut tien.
- Logging: INFO thanh cong, WARN giao dich bi tu choi, ERROR loi IO, DEBUG thong tin ho tro debug; tranh log du so tai khoan.

## Bai 3: CI
- Workflow: `.github/workflows/ci.yml`, trigger push & PR, `mvn clean verify` bao gom test + package.
- JAR artifact: `target/bank-system-1.0-SNAPSHOT.jar`.
- Test fail/fix: [run URLs + screenshot].

## Bai 4: Matrix
- OS: Ubuntu, Windows, macOS.
- Hard-coded path loi tren OS khong tuong thich: [run URLs].
- Fix: `Path.resolve`, `Files`.

## Bai 5: JaCoCo
- Line coverage: [dien x% theo bao cao thuc te, phai >=80%].
- Rules: `LINE` / `COVEREDRATIO` / minimum `0.80`, check tai verify.
- Report artifact: [screenshot].

## Bai 6: Dependency cache
- Lan truoc: [run ID, seconds].
- Lan sau: [run ID, seconds].
- Cache logs: [truoc/ sau, cache hit].

## Bai 7: PR Review
- PR URL: [chen].
- Checkstyle comment va required status checks: [anh].
- Sua loi: [commit fix].

## Bai 8: Executable JAR
- `mvn clean package` va `java -jar target/bank-system-1.0-SNAPSHOT.jar`.
- `target`: noi Maven tao class, report va goi JAR.
- Shade plugin dong goi ca Logback de JAR chay doc lap.

## Bai 9: Professional Logging
- Bo `System.out.println`, dung parameterized logging `{}`.
- Appender: Console + File; path: `logs/bank-system.log`.
- Anh `mvn test`, console & file log: [chen].

## Bai 10: Broken Pipeline
- Loi 1 checkout: [log, run URL, fix].
- Loi 2 dependency 9.9.9: [log, run URL, fix].
- Loi 3 surefire 2.12.4/JUnit 5: [so test chay thuc te, log, fix].
- Loi 4 assert mismatch: [log, run URL, fix].
- Final green: [anh].
