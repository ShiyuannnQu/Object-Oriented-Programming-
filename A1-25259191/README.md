# A1 Same-Day Healthcare Appointment System

This Java console demonstration creates collaborating objects to display health professional details and manage the current day's appointments. It contains 11 source classes and requires no external Java libraries.

- Student ID: `25259191`
- Project name: `A1-25259191`
- Development environment: VS Code, JDK 21
- GitHub: [Object-Oriented-Programming-](https://github.com/ShiyuannnQu/Object-Oriented-Programming-)
- Delivery archive: `output/A1-25259191.zip` in the workspace

The submission checklist records the actual status of the GitHub upload and remote verification.

## Class Responsibilities

| Class | Main responsibility |
| --- | --- |
| `HealthProfessional` | Abstract base class; stores a stable numeric ID, name and fixed working days; validates data, provides controlled access and compares identities by ID |
| `GeneralPractitioner` | Required GP type; adds the bulk billing attribute and a general consultation service description |
| `GeneralSurgeon` | Represents a general surgeon; adds `surgicalFocus` |
| `OrthopaedicDoctor` | Represents an orthopaedic doctor; adds `orthopaedicFocus` |
| `Neurologist` | Represents a neurologist; adds `neurologicalFocus` |
| `Paediatrician` | Represents a paediatrician; adds `maximumPatientAge` |
| `Nurse` | Represents a nurse; adds `registrationLevel` |
| `Patient` | Stores validated, immutable patient name and mobile phone details |
| `Appointment` | Links a patient, any health professional subtype and a time; checks basic completeness, converts time values and compares appointments by time |
| `AppointmentManager` | Manages the appointment collection and slot schedule; adds, checks conflicts, filters, sorts, cancels and formats results |
| `AssignmentOne` | Provides the `main` entry point; uses fictional data to demonstrate successful and unsuccessful operations, while leaving core booking rules to the model and manager |

All six health professional subclasses directly extend the shared base class. Each has a three-argument constructor with default professional details and a four-argument constructor. Times are stored as integer minutes since midnight, so ordering does not depend on the alphabetical order of time strings.

## The Four Pillars of OOP

| Pillar | Location in the code | Reason for using it here |
| --- | --- | --- |
| Encapsulation | Core fields are `private final`; constructors validate data; working-day and slot arrays are defensively copied; the manager returns collection copies | Preserves valid identities and object data, preventing callers from changing internal state through returned arrays or collections |
| Abstraction | `HealthProfessional` is abstract and declares `getProfessionalType()` and `describeService()` | Defines a common contract and prevents direct creation of a professional without a specific type |
| Inheritance | Six concrete professional classes use `extends HealthProfessional`; constructors call `super`, and detail output reuses the parent's `toString()` | Maintains shared identity, name, working days and validation in one place, while subclasses add their own data and behaviour |
| Polymorphism | `AssignmentOne` iterates over a `HealthProfessional[]` and calls overridden methods; `Appointment` stores a base-type reference | Supports different professional types through the same processing steps without separate appointment code for every type |

Method overriding supports polymorphism. Constructor and manager method overloading are separate concepts and should not be confused with overriding.

## Current Design Assumptions

- Each manager represents the current day's appointments. There are no date fields, bookings for other days, automatic midnight clearing, user interface, menus, login or persistent storage.
- The default schedule is 08:00, 08:30, ... 16:00, including 16:00 as an appointment start time. This is the project's interpretation of the schedule's end point.
- A constructor can replace the entire schedule. A custom `int[]` must be nonempty and contain valid minutes within one day, in strictly increasing order without duplicates. The default half-hour interval is not additionally imposed on a replacement schedule.
- A matching professional ID and time creates a conflict. Different professionals may have appointments at the same time. Separate objects, including objects of different subclasses, represent the same identity when their IDs match.
- Working days are stored as fixed weekly details; bookings do not additionally check whether the professional works on the current day.
- Cancellation by mobile phone removes all matching appointments and reports the number removed. No additional rule prevents a patient from booking different professionals at the same time.
- Mobile phone numbers are stored as `String` values to preserve leading zeros. After surrounding whitespace is removed, only 7–15 ASCII digits are accepted. This is a simplified teaching rule rather than general international phone-number validation.
- A paediatrician's maximum patient age is limited to 1–18 as a teaching assumption. `Patient` does not store an age, and appointment management does not enforce age-based eligibility for paediatric bookings.
- Patient and professional names are fictional demonstration data. Service descriptions are not medical advice.

## Main Operation API

| Operation | Method and result |
| --- | --- |
| Create a complete appointment | `Appointment(Patient, int timeInMinutes, HealthProfessional)`; invalid basic details cause an `IllegalArgumentException` |
| Convert times | `Appointment.parseTime(String)` requires `HH:mm`; `formatTime(int)` returns a time label |
| Default/custom schedule | `AppointmentManager()` / `AppointmentManager(int[] allowedTimeSlots)` |
| Add | `addAppointment(Appointment)` or `addAppointment(String patientName, String mobilePhone, String time, HealthProfessional)`; returns success or `WARNING` text |
| View/filter | `getAppointments()`, `getAppointments(int professionalId)` and `getAppointments(String mobilePhone)`; return independent lists, empty when there are no matches |
| View/filter and sort | The corresponding three `getSortedAppointments(...)` overloads return list copies ordered from earliest to latest |
| Cancel a specific appointment | `cancelAppointment(int professionalId, int timeInMinutes)`; `parseTime` can convert the time first |
| Cancel by mobile phone | `cancelAppointment(String mobilePhone)`; removes all matches |
| Display results | `formatAppointments(ArrayList<Appointment>)`; returns a warning for empty results, with printing handled by the caller |
| Count and schedule | `getAppointmentCount()` and `getAllowedTimeSlots()` |

An appointment checks its own completeness and valid time within a day. The manager then checks the configured slots and conflicts in the collection. The internal appointment count increases only after a successful addition.

## Compile, Run and Verify

Open the entire project folder in VS Code, then run these commands in a PowerShell terminal in that folder:

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\run.ps1
powershell -NoProfile -ExecutionPolicy Bypass -File .\verify.ps1
```

`run.ps1` compiles `src` and runs `AssignmentOne`. `verify.ps1` recompiles the project and runs the behavioural checks in `checks`. The project uses JDK 21. The local scripts support the configured default JDK, `JAVA_HOME` or a `PATH` fallback. On another computer, check the JDK installation and VS Code Java configuration.

Equivalent commands for manual compilation and execution are shown below; adjust the JDK path to match the local installation:

```powershell
$a1Jdk = Join-Path $env:USERPROFILE '.jdks\jdk-21.0.13+9'
$a1Sources = @(Get-ChildItem -LiteralPath .\src -Filter '*.java' -File | ForEach-Object { $_.FullName })
New-Item -ItemType Directory -Path .\bin -Force | Out-Null
& (Join-Path $a1Jdk 'bin\javac.exe') --release 21 -encoding UTF-8 -Xlint:all -Werror -d .\bin @a1Sources
& (Join-Path $a1Jdk 'bin\java.exe') -cp .\bin AssignmentOne
```

Run the Java command only after compilation succeeds, to avoid accidentally running old `.class` files. Optionally generate Javadoc using the same variables:

```powershell
& (Join-Path $a1Jdk 'bin\javadoc.exe') -encoding UTF-8 -charset UTF-8 -Xdoclint:all -Werror -d .\javadoc @a1Sources
```

Local verification passed on 2026-11-16: all 11 production source files compiled successfully with `--release 21 -encoding UTF-8 -Xlint:all -Werror`; the behavioural checks reported `All 186 checks passed.`; and Javadoc for all 11 classes passed `-Xdoclint:all -Werror` without warnings.

The main demonstration completed successfully and produced 180 lines. There were 9 appointments after the additions and still 9 after rejected requests. Cancellation by ID and time removed 1, then cancellation by mobile phone removed the remaining 2 matches, leaving 6. The replacement schedule uses a separate manager. See the [verification record](reports/VERIFICATION.md), [demonstration output](reports/demo-output.txt) and [check output](reports/checks-output.txt).

## Requirements, Implementation and Demonstration

| Assessment brief section | Implementation | Evidence in `AssignmentOne` |
| --- | --- | --- |
| Part 1: Shared professional details | `HealthProfessional` | Displays numeric IDs, names and fixed working days |
| Part 2: At least four types, including GP | Six concrete professional subclasses | Displays full details and different service descriptions for all six types |
| Part 3: Process professional objects together | Base-type array and overridden methods | Processes eight objects in one loop: three GPs and one of each of the other five types |
| Part 4: Appointment associations and time ordering | `Patient`, `Appointment` | Creates linked objects and displays patient, time and full professional details; sorting uses the natural order |
| Part 5: Collection management | `AppointmentManager` | Demonstrates additions, both filters, sorting the full collection and both filtered subsets, both cancellation methods, empty-result warnings and a custom schedule |
| Part 6: End-to-end demonstration | `AssignmentOne` | Demonstrates conflicts for the same professional and time, successful simultaneous bookings for different professionals, invalid data, cancellation of nonexistent bookings, counts before and after rejected requests, and updated results after cancellation |

The project has been uploaded to the [A1-25259191 directory on GitHub](https://github.com/ShiyuannnQu/Object-Oriented-Programming-/tree/main/A1-25259191), and remote files have been individually verified against the local files.

The project-specific WakaTime report and personal explanation video are still outstanding. The CLI's `6 mins` is an account-wide total for the day, not a project-specific result, so it cannot serve as a complete assignment report; see [WakaTime status](reports/WAKATIME-STATUS.md). An ongoing development history requires actual progress commits; this upload alone does not establish a history covering the full assessment period.
