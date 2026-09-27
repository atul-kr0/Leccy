
import { useState } from "react";

const API_BASE_URL = "http://localhost:8080";
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

    if (!cleanToken) {
      setStatus("error");
      setMessage("Please enter your booking token.");
      return;
    }

    setStatus("loading");
    setMessage("");

    try {
      const response = await fetch(CHECK_IN_ENDPOINT, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Accept: "text/plain, application/json",
        },
        body: JSON.stringify({
          token: cleanToken,
        }),
      });

      const responseText = await response.text();

      // Your controller returns plain text. Also support JSON
      // responses from validation errors or other exception handlers.
      let responseMessage = responseText.trim();

      if (responseMessage) {
        try {
          const data = JSON.parse(responseMessage);

          if (typeof data === "string") {
            responseMessage = data;
          } else {
            responseMessage =
              data.message ||
              data.error ||
              data.detail ||
              responseMessage;
          }
        } catch {
          // Not JSON. Keep the original plain-text message.
        }
      }

      if (!response.ok) {
        throw new Error(
          responseMessage ||
            `Request failed with status ${response.status}.`
        );
      }

      setStatus("success");
      setMessage(
        responseMessage ||
          "Check-in successful! Your charging session has started."
      );
    } catch (error) {
      setStatus("error");

      if (error instanceof TypeError) {
        setMessage(
          "Unable to connect to the server. Check that the backend is running and try again."
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

  return (
    <div className="checkin-page">
      <style>{`
        @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700;800&display=swap');

        .checkin-page {
          min-height: 100vh;
          display: grid;
          place-items: center;
          padding: 28px 16px;
          box-sizing: border-box;
          background:
            radial-gradient(ellipse at 5% 5%, #dff8e9 0%, transparent 40%),
            radial-gradient(ellipse at 95% 95%, #e1efff 0%, transparent 42%),
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
          animation: checkin-enter .5s ease both;
        }

        .checkin-brand {
          display: flex;
          align-items: center;
          justify-content: center;
          gap: 10px;
          margin-bottom: 22px;
          font-size: 25px;
          font-weight: 800;
          letter-spacing: 1.5px;
        }

        .checkin-brand-icon {
          display: grid;
          place-items: center;
          width: 38px;
          height: 38px;
          border-radius: 12px;
          background: #00a443;
          color: white;
          font-size: 23px;
          box-shadow: 0 7px 18px #00a44330;
        }

        .checkin-card {
          overflow: hidden;
          border: 1px solid #e1e9ef;
          border-radius: 22px;
          background: white;
          box-shadow: 0 24px 70px #16344b14;
        }

        .checkin-header {
          padding: 30px;
          color: white;
          background: linear-gradient(135deg, #092b45, #104565);
        }

        .checkin-eyebrow {
          display: flex;
          align-items: center;
          gap: 8px;
          margin-bottom: 15px;
          color: #76e7a0;
          font-size: 11px;
          font-weight: 800;
          letter-spacing: 1.4px;
          text-transform: uppercase;
        }

        .checkin-dot {
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
          letter-spacing: -1px;
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
          margin: 0 0 24px;
          color: #65798b;
          font-size: 13px;
          line-height: 1.8;
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
          padding: 0 68px 0 17px;
          border: 1.5px solid #d9e3eb;
          border-radius: 13px;
          outline: none;
          background: #fbfcfd;
          color: #092b45;
          font-family: inherit;
          font-size: 20px;
          font-weight: 700;
          letter-spacing: 3px;
          transition: border-color .2s, box-shadow .2s;
        }

        .checkin-input::placeholder {
          color: #a5b3c0;
          font-size: 14px;
          font-weight: 400;
          letter-spacing: 0;
        }

        .checkin-input:focus {
          border-color: #00a443;
          background: white;
          box-shadow: 0 0 0 4px #00a44315;
        }

        .checkin-toggle {
          position: absolute;
          top: 50%;
          right: 12px;
          transform: translateY(-50%);
          border: 0;
          padding: 8px;
          background: transparent;
          color: #64798c;
          font: 600 12px Inter, sans-serif;
          cursor: pointer;
        }

        .checkin-hint {
          margin: 10px 0 24px;
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
          font: 700 14px Inter, sans-serif;
          cursor: pointer;
          box-shadow: 0 8px 18px #00a44324;
          transition: transform .2s, background .2s;
        }

        .checkin-submit:hover:not(:disabled) {
          transform: translateY(-2px);
          background: #008e39;
        }

        .checkin-submit:disabled {
          cursor: not-allowed;
          opacity: .65;
        }

        .checkin-spinner {
          width: 17px;
          height: 17px;
          border: 2px solid #ffffff65;
          border-top-color: white;
          border-radius: 50%;
          animation: checkin-spin .7s linear infinite;
        }

        .checkin-message {
          display: flex;
          align-items: flex-start;
          gap: 12px;
          margin-top: 18px;
          padding: 15px;
          border: 1px solid;
          border-radius: 12px;
          font-size: 12px;
          line-height: 1.7;
          overflow-wrap: anywhere;
          animation: checkin-enter .25s ease both;
        }

        .checkin-message.error {
          border-color: #ffd2cf;
          background: #fff4f3;
          color: #a72f2a;
        }

        .checkin-message.success {
          border-color: #b8ebcb;
          background: #effbf3;
          color: #126b37;
        }

        .checkin-message-icon {
          flex-shrink: 0;
          font-size: 18px;
          font-weight: 800;
        }

        .checkin-message strong {
          display: block;
          margin-bottom: 3px;
        }

        .checkin-reset {
          width: 100%;
          margin-top: 14px;
          padding: 13px;
          border: 1px solid #dce5ec;
          border-radius: 11px;
          background: white;
          color: #17334b;
          font: 600 13px Inter, sans-serif;
          cursor: pointer;
        }

        .checkin-reset:hover {
          background: #f3f7f9;
        }

        .checkin-success-icon {
          display: grid;
          place-items: center;
          width: 76px;
          height: 76px;
          margin: 0 auto 22px;
          border-radius: 50%;
          background: #e8f9ee;
          color: #00a443;
          font-size: 38px;
          animation: checkin-enter .4s ease both;
        }

        .checkin-success-title {
          margin: 0 0 12px;
          text-align: center;
          font-size: 21px;
        }

        .checkin-success-text {
          margin: 0 0 24px;
          color: #65798b;
          font-size: 13px;
          line-height: 1.8;
          text-align: center;
          overflow-wrap: anywhere;
        }

        .checkin-footer {
          margin: 20px 0 0;
          color: #81909d;
          font-size: 11px;
          line-height: 1.8;
          text-align: center;
        }

        @keyframes checkin-enter {
          from { opacity: 0; transform: translateY(12px); }
          to { opacity: 1; transform: translateY(0); }
        }

        @keyframes checkin-spin {
          to { transform: rotate(360deg); }
        }

        @media (max-width: 480px) {
          .checkin-header { padding: 25px 22px; }
          .checkin-body { padding: 24px 22px; }
          .checkin-card { border-radius: 18px; }
        }

        @media (prefers-reduced-motion: reduce) {
          .checkin-shell,
          .checkin-message,
          .checkin-success-icon,
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
              <span className="checkin-dot" />
              Charging station check-in
            </div>

            <h1>{isSuccess ? "You're all set!" : "Start charging"}</h1>

            <p>
              {isSuccess
                ? "Your charging session has been started."
                : "Verify your booking token and start your charging session."}
            </p>
          </header>

          <div className="checkin-body">
            {isSuccess ? (
              <>
                <div className="checkin-success-icon">✓</div>

                <h2 className="checkin-success-title">
                  Session started
                </h2>

                <p className="checkin-success-text">{message}</p>

                <button
                  className="checkin-submit"
                  type="button"
                  onClick={resetForm}
                >
                  Verify another token →
                </button>
              </>
            ) : (
              <>
                <p className="checkin-instruction">
                  Enter the booking token you received when joining
                  the Leccy queue. We'll verify that it's your turn
                  and that your assigned charger is available.
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
                      placeholder="Enter 10-digit token"
                      value={token}
                      maxLength={10}
                      required
                      disabled={isLoading}
                      onChange={(event) => {
                        const value = event.target.value
                          .replace(/\D/g, "")
                          .slice(0, 10);

                        setToken(value);

                        if (status !== "idle") {
                          setStatus("idle");
                          setMessage("");
                        }
                      }}
                    />

                    <button
                      className="checkin-toggle"
                      type="button"
                      disabled={isLoading}
                      onClick={() =>
                        setShowToken((previous) => !previous)
                      }
                    >
                      {showToken ? "Hide" : "Show"}
                    </button>
                  </div>

                  <p className="checkin-hint">
                    Your booking token is 10 digits.
                  </p>

                  <button
                    className="checkin-submit"
                    type="submit"
                    disabled={isLoading || token.length !== 10}
                  >
                    {isLoading ? (
                      <>
                        <span className="checkin-spinner" />
                        Verifying token...
                      </>
                    ) : (
                      <>Verify & Start Charging <span>→</span></>
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
                      {message}
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
          Enter your booking token to check in.
        </p>
      </main>
    </div>
  );
}