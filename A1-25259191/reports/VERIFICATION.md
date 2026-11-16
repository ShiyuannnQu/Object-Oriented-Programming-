# Local Verification Record

Verification date: 2026-11-16 (Asia/Shanghai). Student ID: `25259191`. Project: `A1-25259191`. This record documents the completed local compilation, demonstration and behaviour checks.

| Check | Observed result |
|---|---|
| Java environment | Temurin JDK 21.0.13 |
| Production source | 11 Java files with no third-party runtime dependencies |
| Compilation | Passed with `--release 21 -encoding UTF-8 -Xlint:all -Werror` |
| Behaviour checks | `All 186 checks passed.` |
| Scripted demonstration | Completed normally with 180 output lines; 9 appointments after adding, still 9 after rejected requests, and 6 after cancellations |
| Professional types | Three GPs and one each of general surgeon, orthopaedic doctor, neurologist, paediatrician and nurse |
| Schedule | Accepted the 08:00 and 16:00 boundaries; rejected 08:15, 07:30 and 16:30; accepted 09:15 with a replacement schedule |
| Encapsulation and identity | Checks cover defensive copies of working-day arrays, slot arrays and query lists, together with ID equality and booking conflicts |
| Documentation | Javadoc for all 11 classes passed `-Xdoclint:all -Werror` |
| VS Code | All three project JSON files are valid; the default build task and a separate behaviour-check task are configured |
| Independent extraction | The project ZIP was extracted to a separate directory and recompiled; the 180-line demonstration and all 186 checks passed |
| GitHub | Uploaded to `main/A1-25259191`; repository files were verified individually against the local files |

`demo-output.txt` contains the demonstration output, and `checks-output.txt` contains the behaviour-check output.

Generated Javadoc is available locally at `javadoc/index.html`. It is build output and is excluded from the source archive.

The final archive is saved to `output/A1-25259191.zip` in the workspace. The file list, SHA-256 checksum and extraction results are recorded in `output/A1-25259191-VERIFICATION.md`.

GitHub repository: [Object-Oriented-Programming-](https://github.com/ShiyuannnQu/Object-Oriented-Programming-). The code and documentation have been uploaded and verified. See the project submission checklist for the submission platform status.
