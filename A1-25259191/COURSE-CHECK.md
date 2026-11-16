# Course Coverage Checklist

This project uses JDK 21. The concepts actually used in the code are listed below to support revision against Modules 1–3. These materials have not yet been checked item by item; a pending status does not mean a concept is outside the course scope.

## Technical Inventory

| Concept used in the implementation | Source location or purpose | Status |
| --- | --- | --- |
| Classes, objects, `private`, `protected`, `public`, `static`, `final` | Fields, methods and constants across 11 classes | Pending review |
| Constructors, `this(...)`, `super(...)` and constructor overloading | The health professional superclass, six subclasses and manager class | Pending review |
| `abstract` classes and abstract methods | `HealthProfessional` | Pending review |
| Inheritance, method overriding, `@Override`, superclass references and dynamic binding | Six subclasses, `Appointment` and the entry-point loop | Pending review |
| Basic arrays, enhanced `for` loops, standard loops and defensive copying | Working days, fixed time slots and the array of health professionals | Pending review |
| `ArrayList<Appointment>` and basic generics | Appointment collection, filtered results and returned copies | Pending review |
| `Comparable<Appointment>`, `compareTo`, `Collections.sort` | Natural ordering of appointments and sorting in the manager class | Pending review |
| `equals(Object)`, `hashCode()`, `instanceof` and type casting | Comparing health professional identities by ID | Pending review |
| Method overloading | Adding, filtering, sorting and cancelling appointments | Pending review |
| `IllegalArgumentException`, `throw`, `try/catch` | Constructor validation, time parsing and feedback from management operations | Pending review |
| Strings, `trim`, `charAt`, `substring`, `Integer.parseInt` | Validation of names, mobile phone numbers and `HH:mm` times | Pending review |
| `toString()`, `System.out`, `System.lineSeparator()` | Displaying details and demonstrating the system in the console | Pending review |
| Javadoc and necessary inline comments | Source documentation | Pending review against course materials |

The current production source does not use `java.util.Arrays`; arrays are copied with basic loops. It also does not use `enum`, Stream, lambda, record, date/time APIs, a GUI, a database or external frameworks. Review the concepts actually used in the source; there is no need to add techniques merely to increase their number.

## JDK and Implementation Assumptions

- [x] Use the existing JDK 21 installation with compilation target `--release 21`.
- [x] The local JDK, VS Code Java configuration and scripts have been verified locally.
- [ ] Be able to explain the decision to allow 16:00 as the final appointment start time.
- [ ] Be able to explain why cancellation by mobile phone number removes all matching appointments.
- [ ] Be able to explain the scope of storing fixed working days without adding a rule that rejects bookings based on the current day of the week.
- [ ] Be able to explain the simplified mobile phone rules, the permitted ranges for paediatric attributes and the purpose of a custom schedule.

During revision, record the relevant module or activity locations. If technical choices or business rules change, repeat compilation, the demonstration and the behaviour checks.
