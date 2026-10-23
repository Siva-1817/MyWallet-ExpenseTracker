
import { Link } from 'react-router-dom';
import '../assets/styles/welcome.css';

function Welcome() {
    return (
        <main className="welcome-page">
            <nav className="welcome-navbar">
                <Link to="/" className="welcome-brand" aria-label="MyWallet home">
                    <span className="welcome-brand-mark" aria-hidden="true">$</span>
                    <span>MyWallet</span>
                </Link>
                <div className="welcome-nav-actions">
                    <span className="welcome-tagline">Your money, organized.</span>
                    <Link to="/auth/login" className="welcome-nav-login">Log in</Link>
                </div>
            </nav>

            <section className="hero-section">
                <div className="hero-content">
                    <span className="hero-eyebrow">
                        A CLEARER WAY TO MANAGE MONEY
                    </span>

                    <h1>
                        Make room for
                        <span> what matters.</span>
                    </h1>

                    <p className="hero-description">
                        Bring your income, expenses, and everyday money
                        decisions together in one calm, clear space.
                    </p>

                    <div className="hero-actions">
                        <Link
                            to="/auth/register"
                            className="hero-button hero-button-primary"
                        >
                            Get started for free
                            <span aria-hidden="true"> →</span>
                        </Link>

                        <Link
                            to="/auth/login"
                            className="hero-button hero-button-secondary"
                        >
                            Log in
                        </Link>
                    </div>

                    <p className="hero-note">
                        Simple expense tracking. Clearer financial decisions.
                    </p>
                </div>

                <div className="hero-visual" aria-hidden="true">
                    <div className="visual-glow"></div>

                    <div className="finance-card">
                        <div className="finance-card-header">
                            <span className="finance-card-icon">↗</span>
                            <span>Your money, at a glance</span>
                        </div>

                        <div className="finance-balance">
                            <span>Total balance</span>
                            <strong>$12,480.50</strong>
                            <small><i /> Looking good this month</small>
                        </div>

                        <div className="finance-chart">
                            <div className="chart-bar bar-one"></div>
                            <div className="chart-bar bar-two"></div>
                            <div className="chart-bar bar-three"></div>
                            <div className="chart-bar bar-four"></div>
                            <div className="chart-bar bar-five"></div>
                            <div className="chart-bar bar-six"></div>
                            <div className="chart-bar bar-seven"></div>
                        </div>

                        <div className="finance-card-footer">
                            <span>Jan</span>
                            <span>Feb</span>
                            <span>Mar</span>
                            <span>Apr</span>
                            <span>May</span>
                            <span>Jun</span>
                            <span>Jul</span>
                        </div>
                    </div>

                    <div className="floating-note">
                        <span className="floating-note-icon">✓</span>
                        <div>
                            <strong>Small steps add up</strong>
                            <p>Keep your goals in view</p>
                        </div>
                    </div>
                </div>
            </section>

            <footer className="welcome-footer">
                <span><span className="welcome-footer-dot" /> Your finances, in a better place.</span>
                <span>Spend thoughtfully. Live confidently.</span>
            </footer>
        </main>
    );
}

export default Welcome;
