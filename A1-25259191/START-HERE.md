# Getting Started

The project is named `A1-25259191`, uses student ID `25259191`, and runs with VS Code and JDK 21. It includes six concrete health professional types: general practitioner, general surgeon, orthopaedic doctor, neurologist, paediatrician and nurse. It also includes patient, appointment and appointment management classes, together with a scripted console demonstration.

The GitHub repository is [Object-Oriented-Programming-](https://github.com/ShiyuannnQu/Object-Oriented-Programming-). The final archive is saved to `output/A1-25259191.zip` in the workspace.

Use the project in the following order:

1. Read [COURSE-CHECK.md](COURSE-CHECK.md) and review the Java concepts used against Modules 1-3. The development version is JDK 21.
2. Open the entire project folder in VS Code. Run `powershell -NoProfile -ExecutionPolicy Bypass -File .\run.ps1` in the terminal and inspect both successful and rejected operations.
3. Read [README.md](README.md) alongside the source code to understand each class's responsibilities, the four pillars of OOP and the business assumptions.
4. Run `powershell -NoProfile -ExecutionPolicy Bypass -File .\verify.ps1` and inspect the results. All 186 behaviour checks have passed.
5. Keep the actual GitHub commits and WakaTime project records, and make sure you can explain each class, booking rule and failure case.
6. Use [VIDEO-PREPARATION.md](VIDEO-PREPARATION.md) to check your understanding, then prepare and record your explanation. That document is a preparation guide, not a word-for-word script.
7. Work through [SUBMISSION-CHECKLIST.md](SUBMISSION-CHECKLIST.md) to check the submission materials and remaining tasks.

The default appointment start times run from 08:00 to 16:00 in 30-minute intervals, including a 16:00 start. Cancellation by patient phone number removes every matching appointment. Fixed weekly working days are stored in professional details; bookings are not additionally rejected based on the day of the week. Be prepared to explain these design choices in the demonstration.

The project has been uploaded to GitHub and the remote files have been verified. A complete WakaTime project report and a personal explanation video of no more than five minutes are still required. See the submission checklist for the final submission status.
