# ⚡ Leccy

> A full-stack EV charging platform for discovering real-world charging
> stations, finding compatible charging options, receiving intelligent
> recommendations, and managing booking and charging-session workflows.

[Features](#features) · [Screenshots](#screenshots) ·
[Architecture](#architecture) · [Tech Stack](#tech-stack) ·
[Setup](#setup) · [Roadmap](#roadmap)

---

## 📸 Screenshots

| Home | Find Charger |
|---|---|
| ![Home](screenshots/home.png) | ![Find Charger](screenshots/find-charger.png) |

| Smart Recommendations | Vehicles |
|---|---|
| ![Recommendations](screenshots/smart-charger-results.png) | ![Vehicles](screenshots/vehicles.png) |

| Booking | History |
|---|---|
| ![Booking](screenshots/booking.png) | ![History](screenshots/history.png) |

<details>
<summary>More screenshots</summary>

![Station Details](screenshots/station-details.png)

![Recommendation Preferences](screenshots/smart-charger-preferences.png)

![Notifications](screenshots/notifications.png)

</details>

---

## 🚗 Features

- **Charging Station Discovery** — Search real-world charging stations
  and explore charger information on an interactive map.
- **EV Compatibility** — Match vehicles with compatible charger types.
- **Smart Recommendations** — Rank charging options based on vehicle,
  battery level, distance, charging time, price and other factors.
- **Booking & Queue Management** — Manage application-level charging
  bookings and queue states.
- **Charging Sessions** — Track the application-managed charging journey.
- **History & Notifications** — Review previous bookings and receive
  booking/session updates.
- **Vehicle Management** — Store and manage EV profiles and specifications.

---

## 🏗️ Architecture

```text
React + Vite
     │
     │ REST API
     ▼
Spring Boot
     │
     ▼
   MySQL