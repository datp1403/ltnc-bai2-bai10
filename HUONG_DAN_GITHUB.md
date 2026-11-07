# Tao repository GitHub cho nguoi chua tung tao repo

1. Dang nhap https://github.com . Neu chua co tai khoan, dang ky.
2. Mo https://github.com/new . Repository name: `ltnc-bai2-bai10`.
3. Chon **Public** (thuan tien cho Actions/branch protection trong tai khoan Free).
   Khong tick tao README hay .gitignore vi thu muc ma nguon da co.
4. Bam **Create repository**. GitHub hien dia chi URL cua repo.
5. Giai nen zip `LTNC_Bai2_den_Bai10.zip` vao PC, mo Terminal tai thu muc
   `LTNC_Bai2_den_Bai10` (co file `pom.xml`).
6. Chay lan luot, thay `<USERNAME>` bang ten GitHub ban:

```powershell
git init
git add .
git commit -m "Initial LTNC BankSystem CI pipeline"
git branch -M main
git remote add origin https://github.com/<USERNAME>/ltnc-bai2-bai10.git
git push -u origin main
```

Neu Git bao chua co ten/email: `git config --global user.name "Ten cua ban"` va
`git config --global user.email "email-da-dang-ky-github"`, roi commit lai.
GitHub co the yeu cau dang nhap trinh duyet/credential manager, khong dung password thong thuong.
Neu chua cai Git, cai Git for Windows/GitHub Desktop truoc.

7. Mo trang repo > tab **Actions** > `BankSystem CI - Bai 3-6-8` > job `build-ubuntu-latest`,
   `build-windows-latest`, `build-macos-latest`. Duoi mot run thanh cong co **Artifacts**.
8. Tab **Actions** > `Shipping CI - Bai 10`; workflow nay chay khi file Bai10 thay doi,
   hoac chay thu cong qua `Run workflow`.
9. Neu Actions do, mo job va doc step loi; chup anh **log thuc te**, sua va push lai.

## Co the dung GitHub Desktop (de hon)

GitHub Desktop: File > Add Local Repository > chon thu muc giai nen >
Create a repository here (neu chua co .git) > Commit > Publish repository.
Chu y publish branch `main` va giu nguyen thu muc an `.github/workflows`.

## Branch protection - Bai 7

Sau khi co mot lan PR de status check hien len:
Repo > Settings > Branches (hoac Rules > Rulesets), tao rule cho `main` >
Require a pull request before merging + Require status checks to pass >
chon checkstyle-review va nhung check CI build phu hop > Save.
Neu Settings khong cho enforce, kiem tra quyen quan tri va repo Public.

## Bang chung khong the tao offline

Workflow run URL, anh pipeline xanh/do, comments PR, artifact tren GitHub,
cache hit va execution logs chi co sau khi ban push va GitHub chay.
