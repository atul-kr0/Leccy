import { useState } from "react";

const API_BASE = "http://localhost:8080/api";

// Adjust these paths to match your actual Spring Boot controllers.
const VERIFY_TOKEN_URL = `${API_BASE}/charging-sessions/verify-token`;
const START_SESSION_URL = `${API_BASE}/charging-sessions/start`;

const getAuthToken = () =>
  localStorage.getItem("token") ||
  localStorage.getItem("authToken") ||
  localStorage.getItem("jwt");

export default function StartCharging() {
  const [stationId, setStationId] = useState("");
  const [queueToken, setQueueToken] = useState("");
  const [chargerId, setChargerId] = useState("");

  const [verifiedBooking, setVerifiedBooking] = useState(null);
  const [session, setSession] = useState(null);

  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");

  const resetMessages = () => {
    setError("");
    setSuccess("");
  };

  const handleStationChange = (value) => {
    setStationId(value);
    setVerifiedBooking(null);
    setSession(null);
    resetMessages();
  };

  const handleTokenChange = (value) => {
    setQueueToken(value.replace(/\D/g, "").slice(0, 10));
    setVerifiedBooking(null);
    setSession(null);
    resetMessages();
  };

  const handleVerify = async (event) => {
    event.preventDefault();
    resetMessages();
    setVerifiedBooking(null);
    setSession(null);

    if (!stationId.trim()) {
      setError("Enter the charging station ID.");
      return;
    }

    if (!/^\d{10}$/.test(queueToken)) {
      setError("Enter a valid 10-digit queue token.");
      return;
    }

    const authToken = getAuthToken();

    if (!authToken) {
      setError("Your login session is missing. Please log in again.");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(VERIFY_TOKEN_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authToken}`,
        },

        // Adjust these fields to match VerifyTokenRequestDTO.
        body: JSON.stringify({
          stationId: Number(stationId),
          tokenNumber: queueToken,
        }),
      });

      const data = await response.json().catch(() => ({}));

      if (!response.ok) {
        throw new Error(
          data.message ||
          data.error ||
          `Token verification failed (${response.status}).`
        );
      }

      // Adapt this to your actual verification response DTO.
      setVerifiedBooking(data);

      setSuccess("Token verified. You can now start charging.");
    } catch (err) {
      setError(err.message || "Unable to verify the token.");
    } finally {
      setLoading(false);
    }
  };

  const handleStartSession = async () => {
    resetMessages();

    if (!verifiedBooking) {
      setError("Verify the queue token before starting a session.");
      return;
    }

    const authToken = getAuthToken();

    if (!authToken) {
      setError("Your login session is missing. Please log in again.");
      return;
    }

    setLoading(true);

    try {
      const response = await fetch(START_SESSION_URL, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${authToken}`,
        },

        // Adapt this payload to your StartSessionRequestDTO.
        body: JSON.stringify({
          stationId: Number(stationId),
          tokenNumber: queueToken,
          chargerId: chargerId ? Number(chargerId) : null,
        }),
      });

      const data = await response.json().catch(() => ({}));

      if (!response.ok) {
        throw new Error(
          data.message ||
          data.error ||
          `Could not start the session (${response.status}).`
        );
      }

      setSession(data);
      setSuccess("Charging session started successfully.");
    } catch (err) {
      setError(err.message || "Unable to start the charging session.");
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setStationId("");
    setQueueToken("");
    setChargerId("");
    setVerifiedBooking(null);
    setSession(null);
    resetMessages();
  };

  return (
    <div className="leccy-console">
      <style>{`
        .leccy-console {
          --green: #00a63e;
          --green-dark: #008c35;
          --navy: #08243e;
          --muted: #718096;
          --border: #e2e8f0;
          min-height: calc(100vh - 80px);
          padding: 36px 24px 60px;
          background: #f5f7fa;
          color: var(--navy);
          font-family: inherit;
        }

        .lc-shell {
          max-width: 1050px;
          margin: 0 auto;
        }

        .lc-heading {
          display: flex;
          justify-content: space-between;
          align-items: flex-start;
          gap: 20px;
          margin-bottom: 28px;
          animation: lcEnter .45s ease both;
        }

        .lc-eyebrow {
          display: inline-flex;
          align-items: center;
          gap: 8px;
          color: var(--green);
          font-size: 12px;
          font-weight: 800;
          letter-spacing: 1.6px;
          text-transform: uppercase;
          margin-bottom: 12px;
        }

        .lc-dot {
          width: 8px;
          height: 8px;
          border-radius: 50%;
          background: var(--green);
          box-shadow: 0 0 0 4px #dcfce7;
        }

        .lc-heading h1 {
          font-size: clamp(27px, 4vw, 38px);
          letter-spacing: -1.3px;
          line-height: 1.15;
          margin: 0 0 10px;
          font-weight: 850;
        }

        .lc-heading p {
          margin: 0;
          color: var(--muted);
          font-size: 14px;
          line-height: 1.7;
        }

        .lc-secure {
          display: flex;
          align-items: center;
          gap: 8px;
          white-space: nowrap;
          padding: 11px 14px;
          background: white;
          border: 1px solid var(--border);
          border-radius: 12px;
          color: #475569;
          font-size: 12px;
          font-weight: 650;
        }

        .lc-layout {
          display: grid;
          grid-template-columns: minmax(0, 1.15fr) minmax(280px, .85fr);
          gap: 22px;
          align-items: start;
        }

        .lc-card {
          background: white;
          border: 1px solid var(--border);
          border-radius: 20px;
          padding: 28px;
          box-shadow: 0 8px 30px rgba(8,36,62,.045);
          animation: lcEnter .5s ease both;
        }

        .lc-card h2 {
          margin: 0 0 8px;
          font-size: 20px;
          font-weight: 800;
          letter-spacing: -.4px;
        }

        .lc-subtitle {
          margin: 0 0 25px;
          color: var(--muted);
          font-size: 13px;
          line-height: 1.6;
        }

        .lc-field {
          margin-bottom: 20px;
        }

        .lc-field label {
          display: block;
          margin-bottom: 9px;
          font-size: 13px;
          font-weight: 700;
          color: #263b50;
        }

        .lc-input {
          width: 100%;
          box-sizing: border-box;
          min-height: 49px;
          padding: 13px 15px;
          border: 1px solid #d7e0e8;
          border-radius: 11px;
          outline: none;
          background: #fff;
          color: var(--navy);
          font: inherit;
          font-size: 14px;
          transition: border-color .2s, box-shadow .2s;
        }

        .lc-input:focus {
          border-color: var(--green);
          box-shadow: 0 0 0 4px rgba(0,166,62,.10);
        }

        .lc-input:disabled {
          background: #f1f5f9;
          color: #64748b;
        }

        .lc-token {
          font-size: 25px;
          font-weight: 800;
          letter-spacing: 7px;
          font-variant-numeric: tabular-nums;
        }

        .lc-hint {
          margin: 8px 0 0;
          color: #94a3b8;
          font-size: 11px;
        }

        .lc-button {
          display: flex;
          justify-content: center;
          align-items: center;
          gap: 9px;
          width: 100%;
          min-height: 50px;
          border: 0;
          border-radius: 11px;
          padding: 14px 18px;
          background: var(--green);
          color: white;
          font: inherit;
          font-size: 14px;
          font-weight: 750;
          cursor: pointer;
          transition: background .2s, transform .2s, box-shadow .2s;
        }

        .lc-button:hover:not(:disabled) {
          background: var(--green-dark);
          transform: translateY(-1px);
          box-shadow: 0 7px 18px rgba(0,166,62,.18);
        }

        .lc-button:disabled {
          opacity: .55;
          cursor: not-allowed;
        }

        .lc-button.secondary {
          background: var(--navy);
          margin-top: 12px;
        }

        .lc-button.secondary:hover:not(:disabled) {
          background: #123c5d;
          box-shadow: 0 7px 18px rgba(8,36,62,.15);
        }

        .lc-button.outline {
          background: white;
          color: #475569;
          border: 1px solid var(--border);
          margin-top: 10px;
        }

        .lc-button.outline:hover:not(:disabled) {
          background: #f8fafc;
          box-shadow: none;
        }

        .lc-message {
          margin: 18px 0;
          padding: 13px 15px;
          border-radius: 11px;
          font-size: 13px;
          line-height: 1.6;
          overflow-wrap: anywhere;
          animation: lcEnter .25s ease both;
        }

        .lc-error {
          color: #b91c1c;
          background: #fef2f2;
          border: 1px solid #fecaca;
        }

        .lc-success {
          color: #166534;
          background: #f0fdf4;
          border: 1px solid #bbf7d0;
        }

        .lc-divider {
          height: 1px;
          background: var(--border);
          margin: 25px 0;
        }

        .lc-steps {
          display: grid;
          gap: 18px;
        }

        .lc-step {
          display: flex;
          gap: 14px;
          align-items: flex-start;
        }

        .lc-step-icon {
          flex: 0 0 38px;
          width: 38px;
          height: 38px;
          display: grid;
          place-items: center;
          border-radius: 12px;
          background: #eaf9ef;
          color: var(--green-dark);
          font-size: 17px;
          font-weight: 800;
        }

        .lc-step h3 {
          margin: 2px 0 5px;
          font-size: 14px;
          font-weight: 750;
        }

        .lc-step p {
          margin: 0;
          color: var(--muted);
          font-size: 12px;
          line-height: 1.65;
        }

        .lc-status {
          display: inline-flex;
          align-items: center;
          gap: 7px;
          padding: 7px 10px;
          border-radius: 8px;
          background: #f1f5f9;
          color: #64748b;
          font-size: 11px;
          font-weight: 800;
          text-transform: uppercase;
          letter-spacing: .5px;
        }

        .lc-status.ready {
          color: #15803d;
          background: #dcfce7;
        }

        .lc-status.active {
          color: #166534;
          background: #dcfce7;
        }

        .lc-detail {
          display: flex;
          justify-content: space-between;
          gap: 14px;
          padding: 13px 0;
          border-bottom: 1px solid #edf1f5;
          font-size: 13px;
        }

        .lc-detail:last-child {
          border-bottom: 0;
        }

        .lc-detail span {
          color: var(--muted);
        }

        .lc-detail strong {
          text-align: right;
          overflow-wrap: anywhere;
        }

        .lc-spinner {
          width: 15px;
          height: 15px;
          border: 2px solid rgba(255,255,255,.45);
          border-top-color: white;
          border-radius: 50%;
          animation: lcSpin .7s linear infinite;
        }

        @keyframes lcSpin {
          to { transform: rotate(360deg); }
        }

        @keyframes lcEnter {
          from { opacity: 0; transform: translateY(9px); }
          to { opacity: 1; transform: translateY(0); }
        }

        @media (max-width: 760px) {
          .leccy-console { padding: 24px 14px 40px; }
          .lc-layout { grid-template-columns: 1fr; }
          .lc-heading { flex-direction: column; }
          .lc-secure { white-space: normal; }
          .lc-card { padding: 22px; border-radius: 16px; }
        }

        @media (prefers-reduced-motion: reduce) {
          .leccy-console *,
          .leccy-console *::before,
          .leccy-console *::after {
            animation-duration: .01ms !important;
            transition-duration: .01ms !important;
          }
        }
      `}</style>

      <div className="lc-shell">
        <header className="lc-heading">
          <div>
            <div className="lc-eyebrow">
              <span className="lc-dot" />
              LECCY · STATION OPERATIONS
            </div>

            <h1>Start a charging session</h1>

            <p>
              Verify the customer's queue token and get their vehicle
              charging.
            </p>
          </div>

          <div className="lc-secure">
            <span>🔒</span>
            Authenticated session
          </div>
        </header>

        <div className="lc-layout">
          <section className="lc-card">
            <h2>Verify customer token</h2>
            <p className="lc-subtitle">
              Enter the station details and the 10-digit token
              provided by the customer.
            </p>

            <form onSubmit={handleVerify}>
              <div className="lc-field">
                <label htmlFor="stationId">
                  Charging station ID
                </label>

                <input
                  id="stationId"
                  className="lc-input"
                  type="number"
                  min="1"
                  placeholder="e.g. 45"
                  value={stationId}
                  onChange={(e) =>
                    handleStationChange(e.target.value)
                  }
                  disabled={loading || !!session}
                  required
                />
              </div>

              <div className="lc-field">
                <label htmlFor="chargerId">
                  Charger ID <span style={{ color: "#94a3b8" }}>
                    (optional)
                  </span>
                </label>

                <input
                  id="chargerId"
                  className="lc-input"
                  type="number"
                  min="1"
                  placeholder="Enter charger ID if required"
                  value={chargerId}
                  onChange={(e) => {
                    setChargerId(e.target.value);
                    setVerifiedBooking(null);
                    setSession(null);
                    resetMessages();
                  }}
                  disabled={loading || !!session}
                />
              </div>

              <div className="lc-field">
                <label htmlFor="queueToken">
                  Customer queue token
                </label>

                <input
                  id="queueToken"
                  className="lc-input lc-token"
                  type="text"
                  inputMode="numeric"
                  autoComplete="off"
                  placeholder="0000000000"
                  maxLength={10}
                  value={queueToken}
                  onChange={(e) =>
                    handleTokenChange(e.target.value)
                  }
                  disabled={loading || !!session}
                  required
                />

                <p className="lc-hint">
                  Enter the 10-digit token shown in the customer's
                  Leccy app.
                </p>
              </div>

              {error && (
                <div className="lc-message lc-error" role="alert">
                  <strong>Something went wrong</strong>
                  <br />
                  {error}
                </div>
              )}

              {success && (
                <div className="lc-message lc-success" role="status">
                  ✓ {success}
                </div>
              )}

              {!session && (
                <button
                  className="lc-button"
                  type="submit"
                  disabled={loading || !!verifiedBooking}
                >
                  {loading && !verifiedBooking ? (
                    <>
                      <span className="lc-spinner" />
                      Verifying token...
                    </>
                  ) : verifiedBooking ? (
                    "✓ Token verified"
                  ) : (
                    <>Verify token <span>→</span></>
                  )}
                </button>
              )}

              {verifiedBooking && !session && (
                <button
                  className="lc-button secondary"
                  type="button"
                  onClick={handleStartSession}
                  disabled={loading}
                >
                  {loading ? (
                    <>
                      <span className="lc-spinner" />
                      Starting session...
                    </>
                  ) : (
                    <>⚡ Start charging session</>
                  )}
                </button>
              )}

              {session && (
                <button
                  className="lc-button outline"
                  type="button"
                  onClick={handleReset}
                >
                  Start another session
                </button>
              )}
            </form>
          </section>

          <aside className="lc-card">
            <h2>Session overview</h2>
            <p className="lc-subtitle">
              Current verification and charging status.
            </p>

            <div className="lc-steps">
              <div className="lc-step">
                <div className="lc-step-icon">01</div>
                <div>
                  <h3>Station identified</h3>
                  <p>
                    {stationId
                      ? `Station #${stationId}`
                      : "Waiting for station ID"}
                  </p>
                </div>
              </div>

              <div className="lc-step">
                <div className="lc-step-icon">02</div>
                <div>
                  <h3>Token verification</h3>
                  <p>
                    {verifiedBooking
                      ? "The backend accepted the token."
                      : "The token has not been verified yet."}
                  </p>
                </div>
              </div>

              <div className="lc-step">
                <div className="lc-step-icon">03</div>
                <div>
                  <h3>Charging session</h3>
                  <p>
                    {session
                      ? "The backend confirmed the session."
                      : "The session has not started."}
                  </p>
                </div>
              </div>
            </div>

            <div className="lc-divider" />

            <div style={{ marginBottom: 18 }}>
              <span className={`lc-status ${
                session ? "active" : verifiedBooking ? "ready" : ""
              }`}>
                <span>●</span>
                {session
                  ? "Charging active"
                  : verifiedBooking
                  ? "Ready to start"
                  : "Awaiting verification"}
              </span>
            </div>

            {verifiedBooking && (
              <>
                <div className="lc-detail">
                  <span>Booking ID</span>
                  <strong>
                    {verifiedBooking.bookingId ??
                     verifiedBooking.id ??
                     "Verified"}
                  </strong>
                </div>

                <div className="lc-detail">
                  <span>Station ID</span>
                  <strong>{stationId}</strong>
                </div>

                {verifiedBooking.chargerId != null && (
                  <div className="lc-detail">
                    <span>Charger ID</span>
                    <strong>{verifiedBooking.chargerId}</strong>
                  </div>
                )}

                {verifiedBooking.status && (
                  <div className="lc-detail">
                    <span>Booking status</span>
                    <strong>{verifiedBooking.status}</strong>
                  </div>
                )}
              </>
            )}

            {session && (
              <>
                <div className="lc-divider" />

                <div className="lc-detail">
                  <span>Session ID</span>
                  <strong>
                    {session.sessionId ?? session.id ?? "Started"}
                  </strong>
                </div>

                {session.status && (
                  <div className="lc-detail">
                    <span>Session status</span>
                    <strong>{session.status}</strong>
                  </div>
                )}
              </>
            )}

            {!verifiedBooking && !session && (
              <div
                style={{
                  background: "#f8fafc",
                  borderRadius: 12,
                  padding: 15,
                  color: "#718096",
                  fontSize: 12,
                  lineHeight: 1.7,
                }}
              >
                <strong style={{ color: "#334155" }}>
                  Before you begin
                </strong>
                <br />
                Make sure the customer is at the correct station
                and has provided their queue token.
              </div>
            )}
          </aside>
        </div>
      </div>
    </div>
  );
}