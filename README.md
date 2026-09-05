# ⚡ Leccy — Smart EV Charging Station Management System

<p align="center">
  <strong>Charge Smarter. Drive Further.</strong>
</p>

<p align="center">
  A full-stack EV charging platform for exploring real-world charging stations,
  finding compatible charging options, receiving intelligent recommendations,
  and managing application-level booking and charging-session workflows.
</p>

<p align="center">
  <a href="https://github.com/atul-kr0/Leccy">Repository</a>
  ·
  <a href="#features">Features</a>
  ·
  <a href="#architecture">Architecture</a>
  ·
  <a href="#tech-stack">Tech Stack</a>
  ·
  <a href="#getting-started">Getting Started</a>
  ·
  <a href="#roadmap">Roadmap</a>
</p>

---

## 🚗 What is Leccy?

**Leccy** is a full-stack Smart EV Charging Station Management System designed to simplify the process of discovering charging infrastructure and managing the charging workflow within a single application.

The platform combines **real-world charging station and charger information** with application-managed recommendations, booking, queue, and charging-session workflows.

The current system focuses on:

**Discover → Compare → Recommend → Reserve → Manage Session → Complete → Track**

Leccy brings together:

- Real-world charging station discovery
- EV and charger compatibility
- Intelligent charger recommendations
- Booking management
- Queue management
- Charging-session workflows
- Waiting-time estimation
- Charging history
- Notifications
- Responsive web interfaces

> **Data & Availability Note**
>
> Leccy uses real-world charging station and charger information for station discovery and exploration. However, it does **not currently receive live charger availability, live station status, or real-time occupancy data from charging-network operators**.
>
> Availability, booking, queue, and charging-session states shown within the application are currently **application-managed data**.

---

# ✨ Features

## 🔎 Smart Charging Station Discovery

Explore charging infrastructure using real-world station and charger information.

- Discover charging stations
- Search and explore charging infrastructure
- View station details
- View charger information
- Explore charging options on a map
- Filter charging options based on user requirements

---

## 🚘 Vehicle Catalogue

Leccy includes a vehicle catalogue to help users find charging options suitable for their EV.

- Browse supported electric vehicles
- Select a vehicle before finding charging options
- View vehicle-specific information
- Determine charger compatibility
- Use vehicle characteristics during charger recommendations

---

## 🧠 Smart Recommendation Engine

Instead of simply returning the nearest charging station, Leccy evaluates multiple factors to recommend suitable charging options.

Recommendations can consider:

- Vehicle compatibility
- Charging requirements
- Charging speed
- Estimated waiting time
- Distance
- Application-managed availability state
- User preferences

The objective is to identify a **more suitable charging option**, rather than relying on distance alone.

---

## 📅 Booking & Queue Management

Leccy provides an application-level booking and queue management workflow.

- Create charging reservations
- View and manage bookings
- Cancel bookings
- Manage charger queues
- Calculate estimated waiting times
- Handle queue positions
- Manage booking-to-session transitions

> Booking and queue states are currently managed within Leccy's application and are **not synchronized with physical charging-station operators**.

---

## ⚡ Charging Session Management

Leccy provides an application-level charging-session workflow for managing the lifecycle of a charging session.

- Start charging sessions
- Track active sessions
- Calculate charging duration
- Estimate completion time
- Automatically complete sessions
- Handle queue rebalancing
- Update session state throughout the workflow

The current implementation models the charging-session lifecycle within the application rather than communicating directly with physical EV charging hardware.

---

## 📜 History & Notifications

Users can track their previous charging activity and receive relevant application notifications.

- View booking history
- Track charging-session history
- Synchronize relevant history updates
- Receive booking/session notifications
- View previous charging activity

---

## 📱 Responsive Interface

The frontend is designed for different screen sizes and devices.

- Desktop
- Tablet
- Mobile
- Responsive navigation
- Mobile-friendly booking and history interfaces

---

# 🏗️ Architecture

Leccy uses a **full-stack monorepo architecture** where the frontend and backend live in the same Git repository while being deployed independently.

```text
                         ┌─────────────────────────┐
                         │          Leccy           │
                         │     Web Application      │
                         └────────────┬────────────┘
                                      │
                               HTTPS / REST API
                                      │
                     ┌────────────────▼────────────────┐
                     │         React Frontend          │
                     │            Vite + JS            │
                     │                                │
                     │  Pages • Components • Services │
                     └────────────────┬────────────────┘
                                      │
                                   REST API
                                      │
                     ┌────────────────▼────────────────┐
                     │        Spring Boot Backend      │
                     │                                │
                     │  Authentication                │
                     │  Stations & Chargers           │
                     │  Vehicle Catalogue             │
                     │  Recommendations               │
                     │  Bookings & Queues             │
                     │  Charging Sessions             │
                     │  History & Notifications       │
                     └────────────────┬────────────────┘
                                      │
                                      │ JPA / SQL
                                      ▼
                           ┌─────────────────────┐
                           │        MySQL        │
                           │       Database      │
                           └─────────────────────┘