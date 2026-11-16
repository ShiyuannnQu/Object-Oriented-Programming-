# Video Preparation and Self-Check

The brief requires you to personally create a video of no more than 5 minutes, explaining each of the four pillars of OOP in turn: its name, the concept in your own words, its code location and the reason for using it. This document provides study questions and a timing plan only. It is not a word-for-word script and does not replace your own preparation, explanation or recording.

## Suggested Duration: 4 Minutes 30 Seconds

| Time | Content to prepare yourself |
| --- | --- |
| 00:00–00:20 | Briefly explain the objects modelled by the system and its same-day appointment scope |
| 00:20–01:15 | Encapsulation: locate the fields, constructor validation and defensive copies, and explain exactly what they protect |
| 01:15–02:00 | Abstraction: locate the abstract superclass and shared method contracts, and explain why a generic superclass object is not created |
| 02:00–02:45 | Inheritance: locate the superclass, subclasses, `super` calls and reuse of shared information, and explain where professional differences are represented |
| 02:45–03:40 | Polymorphism: locate the superclass-typed array and superclass references in appointments, and explain which subclass behaviour is actually called |
| 03:40–04:10 | Choose one successful and one failed operation, explaining the expected results and the responsible classes |
| 04:10–04:30 | Briefly explain one design assumption and its trade-off |

Allow 30 seconds for switching views and pauses. Do not sacrifice explanations of the four pillars to cover every line of output.

## Questions You Should Be Able to Answer Without Notes

- Why is constructor validation still needed when fields are `private`? What would happen if the working-days array were returned directly?
- Why does an ID remain unchanged? Why are two different health professional objects with the same ID considered equal in this model?
- What contract do the abstract methods of `HealthProfessional` define? How does abstraction differ from merely hiding fields?
- Which information is stored by the superclass, and which belongs to concrete types? What does `super` do in constructors and when displaying details?
- What is the difference between method overriding and overloading? Which examples demonstrate each one?
- How can one loop using the superclass type display six professional types? Which locations would need to change if a seventh type were added?
- Why does `Appointment` not define six separate fields for the six health professional types?
- Why is appointment constructor validation separate from the manager's double-booking check?
- Why are appointments sorted using integer minutes? How do `compareTo` and `Collections.sort` work together?
- Why can different professionals be booked at the same time, while a second booking for the same professional ID at that time is rejected?
- When cancelling multiple adjacent matches by mobile phone number, what problem does removing entries in reverse order solve?
- Which explicit design interpretations cover the default 16:00 endpoint, cancelling all matches by mobile phone number and storing working days without additional booking restrictions?
- Which behaviours do the existing 186 checks cover? What other boundary cases are worth discussing?

First write your own keywords, then practise answering without a script and locate the corresponding source code. Study and debug anything you cannot explain before recording.
