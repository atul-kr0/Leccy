# ⚡ Leccy

> A full-stack EV charging platform for discovering charging stations, finding compatible chargers, receiving intelligent charging recommendations, and managing application-level booking and charging-session workflows.

[Features](#-features) · [Screenshots](#-screenshots) · [How It Works](#-how-it-works) · [Architecture](#-architecture) · [Tech Stack](#-tech-stack) · [Setup](#-getting-started) · [Roadmap](#-roadmap)

---

## 🚗 What is Leccy?

Leccy is a full-stack Smart EV Charging Station Management System designed to simplify the process of finding suitable charging options and managing the charging journey from a single application.

The platform combines:

- Real-world charging station and charger information
- EV and charger compatibility
- Battery-aware charger recommendations
- Application-level booking and queue management
- Charging-session workflows
- Vehicle management
- Booking history and notifications

The overall workflow is:

**Discover → Compare → Recommend → Book → Manage Session → Track**

---

## ⚠️ Current Data & Availability Scope

**Important:** Leccy currently does **not** connect to charging-network operators or physical charging infrastructure.

The station, charger, and booking information displayed by the application is **not live or real-time**.

### Currently available

- Real-world charging station information
- Charger and connector information
- Station locations and pricing information
- Application-managed charger availability states
- Application-managed booking and queue states
- Application-managed charging-session states

### Not currently available

- ❌ Real-time charger availability
- ❌ Live station occupancy
- ❌ Live charger status from charging networks
- ❌ Real-world reservation execution
- ❌ Communication/control of physical chargers
- ❌ Real-time charging-network data

Bookings and charging sessions shown in Leccy represent the **application workflow**, rather than reservations or charging sessions executed against physical charging infrastructure.

Real-time charging-network integration is part of the project's roadmap.

---

## 📸 Screenshots

### Home Dashboard

![Leccy Home](screenshots/home.png)

### Find a Charging Station

![Find Charger](screenshots/find-charger.png)

### Smart Charger Recommendations

![Smart Recommendations](screenshots/smart-charger-results.png)

<details>
<summary>View more screenshots</summary>

### Station Details

![Station Details](screenshots/station-details.png)

### Recommendation Preferences

![Recommendation Preferences](screenshots/smart-charger-preferences.png)

### My Vehicles

![My Vehicles](screenshots/vehicles.png)

### Booking / Charging Session

![Booking](screenshots/booking.png)

### Charging History

![History](screenshots/history.png)

### Notifications

![Notifications](screenshots/notifications.png)

</details>

---

## 🚀 Features

### 🔍 Charging Station Discovery

- Search charging stations
- Explore stations on an interactive map
- View station details and charger information
- View connector types, pricing and application-managed availability
- Inspect station distance and estimated travel information

### ⚡ Smart Charger Recommendations

Leccy's recommendation workflow evaluates charging options using factors such as:

- Vehicle compatibility
- Current battery level
- Target battery level
- Distance
- Charging time
- Waiting time
- Charging price
- User preferences

The system ranks available application data and presents the most suitable charging options.

### 🚘 Vehicle Management

Users can:

- Register their EVs
- Store vehicle specifications
- Manage multiple vehicles
- Use vehicle information when finding compatible chargers

### 📅 Booking & Queue Management

Users can:

- Select a suitable charger
- Create a charging booking
- View booking status
- Join application-managed queues
- Track charger assignment
- Receive booking updates

### 🔋 Charging Session Workflow

Leccy models the charging journey through application-level states:

```text
Booking Confirmed
       ↓
Charger Assigned
       ↓
Charging Ready
       ↓
Charging Starts
       ↓
Session Completed