# Bai 10 - The broken pipeline

File `ShippingCalculator.java`, tests va `pom.xml` trong thu muc nay la **ban da sua**.
Doi chieu file goc trong Google Docs: https://docs.google.com/document/d/1ERxmVLpqZbTBTI15N40oB5uHmu57HllpGK2oDSr-L50/

## Huong dan gay loi va sua tung loi theo thu tu

1. **Thieu checkout**: trong `.github/workflows/shipping-ci.yml`, tam thoi xoa step `Checkout code`, push, mo Actions de lay log `pom.xml` khong ton tai (vi job dang o thu muc con, co the gap loi working-directory tu dau). Sua bang them `actions/checkout@v7`, push lai cho xanh.
2. **Sai dependency**: doi `logback-classic` version thanh `9.9.9`, push, doc log `Could not find artifact ... 9.9.9`. Sua ve `1.4.11`, push xanh.
3. **Surefire qua cu**: doi plugin `maven-surefire-plugin` tu `3.2.5` sang `2.12.4`, chay `mvn -DfailIfNoTests=true clean verify`; JUnit 5 co the khong duoc phat hien: ghi lai `Tests run: 0`/`No tests were executed` neu xuat hien. Sua ve `3.2.5`, chung minh `Tests run: 4`.
4. **Loi tu tao**: doi assert gia cuoc STANDARD tu `15000.0` thanh `16000.0`, push, chup CI do + log `expected ... but was ...`. Phuc hoi 15000.0, push xanh.

**Quan trong:** Khong chup anh gia hay viet log khong thuc su xuat hien. Neu loi 3 khong lam job do, ghi ro no la *test-discovery defect* (build xanh gia) va bao cao so luong test chay 0; khong tu tuyen bo no la build failure.

## Chay test local

```powershell
cd Bai10-Broken-Pipeline
mvn -DfailIfNoTests=true clean verify
```

Ghi chung minh: anh Actions run, step do/xanh, dong log thuc te, commit fix.
