import { useEffect, useState } from 'react';
import '../../../assets/styles/register.css';
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import AuthService from '../../../services/auth.service';

function Login() {
    const navigate = useNavigate();
    const {
        register,
        handleSubmit,
        formState: { errors }
    } = useForm();

    const [responseError, setResponseError] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        const currentUser = AuthService.getCurrentUser();

        if (currentUser?.roles.includes('ROLE_USER')) {
            navigate('/user/dashboard');
        } else if (currentUser?.roles.includes('ROLE_ADMIN')) {
            navigate('/admin/transactions');
        }
    }, [navigate]);

    const onSubmit = async (data) => {
        setIsLoading(true);

        await AuthService.login_req(data.email, data.password).then(
            () => {
                setResponseError('');

                setTimeout(() => {
                    if (AuthService.getCurrentUser().roles.includes('ROLE_USER')) {
                        navigate('/user/dashboard');
                    } else if (AuthService.getCurrentUser().roles.includes('ROLE_ADMIN')) {
                        navigate('/admin/transactions');
                    }
                }, 5000);

                localStorage.setItem(
                    'message',
                    JSON.stringify({ status: 'SUCCESS', text: 'Login successfull!' })
                );
            },
            (error) => {
                const message =
                    (error.response && error.response.data && error.response.data.message) ||
                    error.message ||
                    error.toString();

                setResponseError(
                    message === 'Bad credentials'
                        ? 'Invalid email or password!'
                        : 'Something went wrong: Try again later!'
                );
            }
        );

        setIsLoading(false);
    };

    return (
        <main className="login-page">
            <section className="login-story" aria-label="MyWallet overview">
                <Link to="/" className="login-brand" aria-label="MyWallet home">
                    <span className="login-brand-mark" aria-hidden="true">$</span>
                    <span>MyWallet</span>
                </Link>

                <div className="login-story-copy">
                    <span className="login-eyebrow">A clearer view of your money</span>
                    <h1>
                        Make room for
                        <br />
                        <span>what matters.</span>
                    </h1>
                    <p>
                        One simple place to understand your spending, build better
                        habits, and feel good about what comes next.
                    </p>
                </div>

                <div className="login-preview" aria-hidden="true">
                    <div className="login-preview-top">
                        <div>
                            <span className="login-preview-label">Total balance</span>
                            <strong>$12,480.50</strong>
                        </div>
                        <span className="login-preview-menu">•••</span>
                    </div>

                    <div className="login-preview-chart">
                        <div className="login-chart-grid">
                            <span />
                            <span />
                            <span />
                        </div>
                        <svg viewBox="0 0 420 112" preserveAspectRatio="none">
                            <defs>
                                <linearGradient id="login-chart-fill" x1="0" x2="0" y1="0" y2="1">
                                    <stop offset="0%" stopColor="#c9f27b" stopOpacity=".25" />
                                    <stop offset="100%" stopColor="#c9f27b" stopOpacity="0" />
                                </linearGradient>
                            </defs>
                            <path
                                d="M0 88 C28 82 32 60 64 68 S104 84 132 55 S174 61 200 42 S245 60 274 37 S316 49 344 26 S388 33 420 8 V112 H0Z"
                                fill="url(#login-chart-fill)"
                            />
                            <path
                                d="M0 88 C28 82 32 60 64 68 S104 84 132 55 S174 61 200 42 S245 60 274 37 S316 49 344 26 S388 33 420 8"
                                fill="none"
                                stroke="#c9f27b"
                                strokeLinecap="round"
                                strokeWidth="3"
                            />
                        </svg>
                    </div>

                    <div className="login-preview-bottom">
                        <span>Monthly spending</span>
                        <strong><i /> Looking good this month</strong>
                    </div>
                </div>

                <div className="login-story-foot">
                    <span className="login-story-dot" />
                    Your finances, in a better place.
                </div>
            </section>

            <section className="login-panel">
                <div className="login-panel-top">
                    <span>New to MyWallet?</span>
                    <Link to="/auth/register">Create an account</Link>
                </div>

                <div className="login-form-wrap">
                    <div className="login-mobile-brand">
                        <span className="login-brand-mark" aria-hidden="true">$</span>
                        <span>MyWallet</span>
                    </div>

                    <div className="login-heading">
                        <span className="login-eyebrow">WELCOME BACK</span>
                        <h2>Sign in to your account</h2>
                        <p>Your money dashboard is right where you left it.</p>
                    </div>

                    {responseError && (
                        <div className="login-error" role="alert">
                            <span aria-hidden="true">!</span>
                            {responseError}
                        </div>
                    )}

                    <form className="login-form" onSubmit={handleSubmit(onSubmit)} noValidate>
                        <div className="login-field">
                            <label htmlFor="login-email">Email address</label>
                            <input
                                id="login-email"
                                type="email"
                                autoComplete="email"
                                placeholder="you@example.com"
                                aria-invalid={!!errors.email}
                                aria-describedby={errors.email ? 'login-email-error' : undefined}
                                {...register('email', {
                                    required: 'Email is required.',
                                    pattern: {
                                        value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                                        message: 'Enter a valid email address.'
                                    }
                                })}
                            />
                            {errors.email && (
                                <small className="login-field-error" id="login-email-error">
                                    {errors.email.message}
                                </small>
                            )}
                        </div>

                        <div className="login-field">
                            <div className="login-password-label">
                                <label htmlFor="login-password">Password</label>
                                <Link to="/auth/forgotPassword">
                                    Forgot password?
                                </Link>
                            </div>
                            <input
                                id="login-password"
                                type="password"
                                autoComplete="current-password"
                                placeholder="Enter your password"
                                aria-invalid={!!errors.password}
                                aria-describedby={errors.password ? 'login-password-error' : undefined}
                                {...register('password', {
                                    required: 'Password is required.'
                                })}
                            />
                            {errors.password && (
                                <small className="login-field-error" id="login-password-error">
                                    {errors.password.message}
                                </small>
                            )}
                        </div>

                        <button className="login-submit" type="submit" disabled={isLoading}>
                            {isLoading ? 'Signing in…' : 'Sign in'}
                            {!isLoading && <span aria-hidden="true">→</span>}
                        </button>
                    </form>

                    <p className="login-register-prompt">
                        Don’t have an account? <Link to="/auth/register">Sign up free</Link>
                    </p>
                </div>

                <p className="login-legal">© MyWallet · A little more clarity, every day.</p>
            </section>
        </main>
    );
}

export default Login;
