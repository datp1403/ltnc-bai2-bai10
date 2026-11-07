# LTNC - Bai 2 den Bai 10: BankSystem + GitHub Actions + Broken Pipeline

**Can cu:** Google Docs `9 - CI-CD` do sinh vien cung cap.

## Cau truc

- Thu muc goc (pom.xml va `src/`) la **1 du an Maven BankSystem** lam Bai 2-9.
- `.github/workflows/ci.yml`: push/PR, OS matrix, Maven `verify`, cache Maven, upload JAR & JaCoCo.
- `.github/workflows/pr-checkstyle.yml`: PR Checkstyle comments (Bai 7).
- `Bai10-Broken-Pipeline/`: Maven project ShippingCalculator doc lap, workflow CI rieng.
- `scenarios/`: huong dan **gay loi co y**, khong pha code nop cuoi.
- `docs/`: giai thich tung bai.
- `EVIDENCE/`: vi tri de anh va logs sau khi chay thuc te.

## Bat dau tu may Windows + IntelliJ

1. Giai nen zip vao mot thu muc moi, khong ghi de Bai1 hay Bai2 cu.
2. Mo IntelliJ > File > Open > chon thu muc `LTNC_Bai2_den_Bai10` (thu muc co pom.xml).
3. Chon JDK 17+ > Reload Maven project.
4. Trong Terminal o thu muc goc: `mvn clean verify`.
5. Xem `target/site/jacoco/index.html` (JaCoCo), `target/bank-system-1.0-SNAPSHOT.jar` (JAR).
6. Chay `java -jar target/bank-system-1.0-SNAPSHOT.jar` va xem `logs/bank-system.log`.
7. Chay `cd Bai10-Broken-Pipeline` roi `mvn -DfailIfNoTests=true clean verify`.
8. Doc file `HUONG_DAN_GITHUB.md` de tao repo va xem Actions.

## Trang thai xac minh

Da kiem tra cau truc tep, XML/YAML va Java syntax o muc local (neu duoc bao cao).
**Chua the khang dinh `mvn clean verify` / coverage >=80% / Checkstyle BUILD SUCCESS / GitHub Actions xanh,**
vi moi truong tao file khong cai Maven va khong truy cap Maven Central, va sinh vien chua tao repo GitHub.
Can chay thuc te va ghi anh tai `EVIDENCE/` truoc khi nop. 
