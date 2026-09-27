import React from "react";

import {
    BrowserRouter,
    Routes,
    Route,
    Navigate,
} from "react-router-dom";

// Layout
import AppLayout from "./layouts/AppLayout";

// Public pages
import Landing from "./pages/Landing";
import Login from "./pages/Login";
import Register from "./pages/Register";
import HowItWorks from "./pages/HowItWorks";
import Pricing from "./pages/Pricing";
import Contact from "./pages/Contact";
import About from "./pages/About";
import Feature from "./pages/Feature";
import MapTest from "./pages/MapTest";
import CheckIn from "./pages/CheckIn";

// Protected pages
import Home from "./pages/Home";
import MyVehicles from "./pages/MyVehicles";
import Bookings from "./pages/Bookings";
import FindCharger from "./pages/FindCharger";
import StationDetails from "./pages/StationDetails";
import History from "./pages/History";
import Support from "./pages/Support";
import Profile from "./pages/Profile";
import Settings from "./pages/Settings";
import Notifications from "./pages/Notifications";


/* ============================================================
   PROTECTED ROUTE
   ============================================================ */

const ProtectedRoute = ({ children }) => {
    const token = localStorage.getItem("token");

    if (!token) {
        return <Navigate to="/login" replace />;
    }

    return children;
};


/* ============================================================
   APP
   ============================================================ */

const App = () => {
    return (
        <BrowserRouter>
            <Routes>

                {/* ================= PUBLIC PAGES ================= */}

                {/* Landing page — default website URL */}
                <Route
                    path="/"
                    element={<Landing />}
                />

                <Route
                    path="/login"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/how-it-works"
                    element={<HowItWorks />}
                />

                <Route
                    path="/pricing"
                    element={<Pricing />}
                />

                <Route
                    path="/contact"
                    element={<Contact />}
                />

                <Route
                    path="/about"
                    element={<About />}
                />

                <Route
                    path="/features"
                    element={<Feature />}
                />

                {/* Public check-in page */}
                <Route
                    path="/check-in"
                    element={<CheckIn />}
                />

                {/* Map testing */}
                <Route
                    path="/map-test"
                    element={<MapTest />}
                />


                {/* ============ PROTECTED APPLICATION ============ */}

                <Route
                    path="/home"
                    element={
                        <ProtectedRoute>
                            <AppLayout />
                        </ProtectedRoute>
                    }
                >

                    {/* /home */}
                    <Route
                        index
                        element={<Home />}
                    />

                    {/* /home/vehicles */}
                    <Route
                        path="vehicles"
                        element={<MyVehicles />}
                    />

                    {/* /home/bookings */}
                    <Route
                        path="bookings"
                        element={<Bookings />}
                    />

                    {/* /home/find-charger */}
                    <Route
                        path="find-charger"
                        element={<FindCharger />}
                    />

                    {/* /home/station/:id */}
                    <Route
                        path="station/:id"
                        element={<StationDetails />}
                    />

                    {/* /home/history */}
                    <Route
                        path="history"
                        element={<History />}
                    />

                    {/* /home/support */}
                    <Route
                        path="support"
                        element={<Support />}
                    />

                    {/* /home/profile */}
                    <Route
                        path="profile"
                        element={<Profile />}
                    />

                    {/* /home/settings */}
                    <Route
                        path="settings"
                        element={<Settings />}
                    />

                    {/* /home/notifications */}
                    <Route
                        path="notifications"
                        element={<Notifications />}
                    />

                </Route>


                {/* ================= UNKNOWN ROUTE ================= */}

                <Route
                    path="*"
                    element={<Navigate to="/" replace />}
                />

            </Routes>
        </BrowserRouter>
    );
};

export default App;