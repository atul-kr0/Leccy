import { useState } from "react";

const API_BASE_URL = (
  import.meta.env.VITE_API_URL || "http://localhost:8080"
).replace(/\/+$/, "");

const CHECK_IN_ENDPOINT = `${API_BASE_URL}/api/check-in`;

export default function CheckIn() {
  const [token, setToken] = useState("");
  const [status, setStatus] = useState("idle");
  const [message, setMessage] = useState("");
  const [showToken, setShowToken] = useState(false);

  const isLoading = status === "loading";
  const isSuccess = status === "success";
  const isError = status === "error";

  async function handleSubmit(event) {
    event.preventDefault();

    const cleanToken = token.trim();

    // Validate the token before making the API request.
    if (!/^\d{10}$/.test(cleanToken)) {
      setStatus("error");
      setMessage("Please enter a valid 10-digit booking token.");
      return;
    }

    setStatus("loading");
    setMessage("");

    try {
      const response = await fetch(CHECK_IN_ENDPOINT, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "text/plain",
        },
        body: JSON.stringify({
          token: cleanToken,
        }),
      });

      // The backend produces text/plain, not JSON.
      const responseText = await response.text();

      if (!response.ok) {
        throw new Error(
          responseText ||
            `Request failed with status ${response.status}.`
        );
      }

      // A successful response should display the backend's message.
      setStatus("success");
      setMessage(
        responseText.trim() ||
          "Your charging session has started successfully."
      );
    } catch (error) {
      setStatus("error");

      if (error instanceof TypeError) {
        setMessage(
          "Unable to connect to the server. Check that your backend is running and try again."
        );
      } else {
        setMessage(
          error.message || "Something went wrong. Please try again."
        );
      }
    }
  }

  function resetForm() {
    setToken("");
    setMessage("");
    setStatus("idle");
    setShowToken(false);
  }

  function handleTokenChange(event) {
    // Keep only digits and limit the token to 10 characters.
    const cleanValue = event.target.value
      .replace(/\D/g, "")
      .slice(0, 10);

    setToken(cleanValue);

    if (status !== "idle") {
      setStatus("idle");
      setMessage("");
    }
  }

  return (
    <div className="checkin-page">
      <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap');

        .checkin-page {
          min-height: 100vh;
          box-sizing: border-box;
          display: grid;
          place-items: center;
          padding: 28px 16px;
          background:
            radial-gradient(ellipse at 10% 10%, #e1f9eb 0%, transparent 42%),
            radial-gradient(ellipse at 90% 90%, #e2f0ff 0%, transparent 40%),
            #f5f8fb;
          color: #092b45;
          font-family: Inter, sans-serif;
        }

        .checkin-page *,
        .checkin-page *::before,
        .checkin-page *::after {
          box-sizing: border-box;
        }

        .checkin-shell {
          width: 100%;
          max-width: 460px;
          animation: checkin-enter 550ms cubic-bezier(.2,.8,.2,1) both;
        }

        .checkin-brand {
          display: flex;
          align-items: center;
          justify-content: center;
          gap: 9px;
          margin-bottom: 22px;
          font-size: 25px;
          font-weight: 800;
          letter-spacing: 1.5px;
          color: #092b45;
        }

        .checkin-brand-icon {
          width: 37px;
          height: 37px;
          display: grid;
          place-items: center;
          border-radius: 12px;
          background: #00a443;
          color: white;
          font-size: 21px;
          box-shadow: 0 7px 18px #00a44330;
        }

        .checkin-card {
          overflow: hidden;
          border: 1px solid #e1e9ef;
          border-radius: 24px;
          background: white;
          box-shadow: 0 24px 70px #16344b12;
        }

        .checkin-header {
          padding: 30px 30px 25px;
          background: linear-gradient(135deg, #092b45, #104565);
          color: white;
        }

        .checkin-eyebrow {
          display: inline-flex;
          align-items: center;
          gap: 7px;
          margin-bottom: 15px;
          color: #76e7a0;
          font-size: 11px;
          font-weight: 800;
          letter-spacing: 1.5px;
          text-transform: uppercase;
        }

        .checkin-eyebrow-dot {
          width: 7px;
          height: 7px;
          border-radius: 50%;
          background: #39df7d;
          box-shadow: 0 0 12px #39df7d90;
        }

        .checkin-header h1 {
          margin: 0 0 10px;
          font-size: clamp(25px, 6vw, 31px);
          font-weight: 800;
          letter-spacing: -1.2px;
        }

        .checkin-header p {
          margin: 0;
          color: #c2d5e3;
          font-size: 13px;
          line-height: 1.7;
        }

        .checkin-body {
          padding: 30px;
        }

        .checkin-instruction {
          margin: 0 0 22px;
          color: #65798b;
          font-size: 13px;
          line-height: 1.7;
        }

        .checkin-label {
          display: block;
          margin-bottom: 10px;
          color: #17334b;
          font-size: 13px;
          font-weight: 700;
        }

        .checkin-input-wrap {
          position: relative;
        }

        .checkin-input {
          width: 100%;
          height: 60px;
          padding: 0 65px 0 17px;
          border: 1.5px solid #d9e3eb;
          border-radius: 13px;
          outline: none;
          background: #fbfcfd;
          color: #092b45;
          font-family: inherit;
          font-size: 20px;
          font-weight: 700;
          letter-spacing: 3px;
          transition:
            border-color 180ms,
            box-shadow 180ms,
            background 180ms;
        }

        .checkin-input::placeholder {
          color: #a5b3c0;
          font-size: 15px;
          font-weight: 400;
          letter-spacing: 1px;
        }

        .checkin-input:focus {
          border-color: #00a443;
          background: white;
          box-shadow: 0 0 0 4px #00a44315;
        }

        .checkin-input:disabled {
          opacity: .65;
        }

        .checkin-toggle {
          position: absolute;
          top: 50%;
          right: 12px;
          transform: translateY(-50%);
          padding: 8px;
          border: none;
          background: transparent;
          color: #64798c;
          font-family: inherit;
          font-size: 12px;
          font-weight: 600;
          cursor: pointer;
        }

        .checkin-toggle:disabled {
          cursor: not-allowed;
        }

        .checkin-hint {
          margin: 10px 0 23px;
          color: #8998a6;
          font-size: 11px;
        }

        .checkin-submit {
          display: flex;
          width: 100%;
          min-height: 55px;
          align-items: center;
          justify-content: center;
          gap: 10px;
          border: none;
          border-radius: 13px;
          background: #00a443;
          color: white;
          font-family: inherit;
          font-size: 14px;
          font-weight: 700;
          cursor: pointer;
          box-shadow: 0 8px 18px #00a44324;
          transition:
            transform 180ms,
            background 180ms,
            box-shadow 180ms;
        }

        .checkin-submit:hover:not(:disabled) {
          transform: translateY(-2px);
          background: #008e39;
          box-shadow: 0 12px 24px #00a44335;
        }

        .checkin-submit:active:not(:disabled) {
          transform: translateY(0);
        }

        .checkin-submit:disabled {
          cursor: not-allowed;
          opacity: .7;
        }

        .checkin-spinner {
          width: 17px;
          height: 17px;
          border: 2px solid #ffffff65;
          border-top-color: white;
          border-radius: 50%;
          animation: checkin-spin 700ms linear infinite;
        }

        .checkin-message {
          display: flex;
          align-items: flex-start;
          gap: 11px;
          margin-top: 18px;
          padding: 15px;
          border: 1px solid;
          border-radius: 12px;
          font-size: 12px;
          line-height: 1.7;
          overflow-wrap: anywhere;
          animation: checkin-enter 250ms ease both;
        }

        .checkin-message.success {
          border-color: #b8ebcb;
          background: #effbf3;
          color: #126b37;
        }

        .checkin-message.error {
          border-color: #ffd2cf;
          background: #fff4f3;
          color: #a72f2a;
        }

        .checkin-message-icon {
          flex-shrink: 0;
          font-size: 17px;
          font-weight: 800;
        }

        .checkin-reset {
          width: 100%;
          margin-top: 13px;
          padding: 12px;
          border: 1px solid #dce5ec;
          border-radius: 11px;
          background: white;
          color: #17334b;
          font-family: inherit;
          font-size: 13px;
          font-weight: 600;
          cursor: pointer;
          transition: background 150ms;
        }

        .checkin-reset:hover {
          background: #f3f7f9;
        }

        .checkin-footer {
          margin: 20px 0 0;
          color: #81909d;
          font-size: 11px;
          line-height: 1.8;
          text-align: center;
        }

        @keyframes checkin-enter {
          from {
            opacity: 0;
            transform: translateY(14px);
          }
          to {
            opacity: 1;
            transform: translateY(0);
          }
        }

        @keyframes checkin-spin {
          to {
            transform: rotate(360deg);
          }
        }

        @media (max-width: 480px) {
          .checkin-header {
            padding: 25px 22px 22px;
          }

          .checkin-body {
            padding: 24px 22px;
          }

          .checkin-card {
            border-radius: 19px;
          }
        }

        @media (prefers-reduced-motion: reduce) {
          .checkin-shell,
          .checkin-message,
          .checkin-spinner {
            animation: none;
          }

          .checkin-submit,
          .checkin-input {
            transition: none;
          }
        }
      `}</style>

      <main className="checkin-shell">
        <div className="checkin-brand">
          <span className="checkin-brand-icon">ϟ</span>
          LECCY
        </div>

        <section className="checkin-card">
          <header className="checkin-header">
            <div className="checkin-eyebrow">
              <span className="checkin-eyebrow-dot" />
              Charging station check-in
            </div>

            <h1>
              {isSuccess ? "You're all set!" : "Start charging"}
            </h1>

            <p>
              {isSuccess
                ? "Your charging session is now in progress."
                : "Enter your booking token to verify your turn and start your session."}
            </p>
          </header>

          <div className="checkin-body">
            {isSuccess ? (
              <div className="checkin-success-content">
                <div
                  style={{
                    width: 76,
                    height: 76,
                    margin: "0 auto 22px",
                    display: "grid",
                    placeItems: "center",
                    borderRadius: "50%",
                    background: "#e8f9ee",
                    color: "#00a443",
                    fontSize: 38,
                    animation: "checkin-enter 400ms ease both",
                  }}
                >
                  ✓
                </div>

                <h2
                  style={{
                    margin: "0 0 10px",
                    textAlign: "center",
                    fontSize: 20,
                  }}
                >
                  Session started
                </h2>

                <p
                  style={{
                    margin: "0 0 22px",
                    textAlign: "center",
                    color: "#65798b",
                    fontSize: 13,
                    lineHeight: 1.8,
                    overflowWrap: "anywhere",
                  }}
                >
                  {message}
                </p>

                <button
                  className="checkin-submit"
                  onClick={resetForm}
                  type="button"
                >
                  Verify another token <span>→</span>
                </button>
              </div>
            ) : (
              <>
                <p className="checkin-instruction">
                  Your token will be checked against your booking
                  status and assigned charger's availability.
                </p>

                <form onSubmit={handleSubmit}>
                  <label
                    className="checkin-label"
                    htmlFor="booking-token"
                  >
                    Booking token
                  </label>

                  <div className="checkin-input-wrap">
                    <input
                      id="booking-token"
                      className="checkin-input"
                      type={showToken ? "text" : "password"}
                      inputMode="numeric"
                      autoComplete="off"
                      placeholder="Enter your token"
                      value={token}
                      onChange={handleTokenChange}
                      disabled={isLoading}
                      maxLength={10}
                      required
                      aria-describedby="token-hint"
                      aria-invalid={isError}
                    />

                    <button
                      className="checkin-toggle"
                      type="button"
                      onClick={() =>
                        setShowToken((previous) => !previous)
                      }
                      disabled={isLoading}
                      aria-label={
                        showToken ? "Hide token" : "Show token"
                      }
                    >
                      {showToken ? "Hide" : "Show"}
                    </button>
                  </div>

                  <p className="checkin-hint" id="token-hint">
                    Enter the 10-digit token from your Leccy booking.
                  </p>

                  <button
                    className="checkin-submit"
                    type="submit"
                    disabled={isLoading || token.length !== 10}
                  >
                    {isLoading ? (
                      <>
                        <span
                          className="checkin-spinner"
                          aria-hidden="true"
                        />
                        Verifying token...
                      </>
                    ) : (
                      <>
                        Verify &amp; Start Charging <span>→</span>
                      </>
                    )}
                  </button>
                </form>

                {isError && (
                  <div
                    className="checkin-message error"
                    role="alert"
                    aria-live="assertive"
                  >
                    <span className="checkin-message-icon">!</span>

                    <div>
                      <strong>Unable to start charging</strong>
                      <div>{message}</div>
                    </div>
                  </div>
                )}
              </>
            )}
          </div>
        </section>

        <p className="checkin-footer">
          LECCY · Smart charging, zero hassle.
          <br />
          Only enter a token for the booking you intend to check in.
        </p>
      </main>
    </div>
  );
}