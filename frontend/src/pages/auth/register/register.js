
import { useState } from 'react';
import '../../../assets/styles/register.css';
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import AuthService from '../../../services/auth.service';

function Register() {
    const navigate = useNavigate();

    const {
        register,
        handleSubmit,
        watch,
        formState: { errors }
    } = useForm();

    const [responseError, setResponseError] = useState('');
    const [isLoading, setIsLoading] = useState(false);

    const password = watch('password', '');

    const onSubmit = async (data) => {
        setIsLoading(true);
        setResponseError('');

        try {
            const response = await AuthService.register_req(
                data.username,
                data.email,
                data.password
            );

            if (response.data.status === 'SUCCESS') {
                navigate('/auth/login', { replace: true });
            } else {
                setResponseError(
                    response.data.response ||
                    'Registration failed. Please try again.'
                );
            }
        } catch (error) {
            setResponseError(
                error.response?.data?.response ||
                'Unable to register. Please try again.'
            );
        } finally {
            setIsLoading(false);
        }
    };

    return (
        <main className="login-page register-page">
            <section className="login-story" aria-label="MyWallet overview">
                <Link to="/" className="login-brand" aria-label="MyWallet home">
                    <span className="login-brand-mark" aria-hidden="true">$</span>
                    <span>MyWallet</span>
                </Link>

                <div className="login-story-copy">
                    <span className="login-eyebrow">A better relationship with money</span>
                    <h1>
                        Start with a
                        <br />
                        <span>fresh outlook.</span>
                    </h1>
                    <p>
                        Bring your income, expenses, and everyday money decisions
                        together in one calm, clear space.
                    </p>
                </div>

                <div className="login-preview" aria-hidden="true">
                    <div className="login-preview-top">
                        <div>
                            <span className="login-preview-label">Your next step</span>
                            <strong>More clarity</strong>
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
                                <linearGradient id="register-chart-fill" x1="0" x2="0" y1="0" y2="1">
                                    <stop offset="0%" stopColor="#c9f27b" stopOpacity=".25" />
                                    <stop offset="100%" stopColor="#c9f27b" stopOpacity="0" />
                                </linearGradient>
                            </defs>
                            <path
                                d="M0 88 C28 82 32 60 64 68 S104 84 132 55 S174 61 200 42 S245 60 274 37 S316 49 344 26 S388 33 420 8 V112 H0Z"
                                fill="url(#register-chart-fill)"
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
                        <span>Small steps</span>
                        <strong><i /> A clearer picture, every day</strong>
                    </div>
                </div>

                <div className="login-story-foot">
                    <span className="login-story-dot" />
                    Your finances, in a better place.
                </div>
            </section>

            <section className="login-panel register-panel">
                <div className="login-panel-top">
                    <span>Already have an account?</span>
                    <Link to="/auth/login">Log in</Link>
                </div>

                <div className="login-form-wrap register-form-wrap">
                    <div className="login-mobile-brand">
                        <span className="login-brand-mark" aria-hidden="true">$</span>
                        <span>MyWallet</span>
                    </div>

                    <div className="login-heading">
                        <span className="login-eyebrow">GET STARTED</span>
                        <h2>Create your account</h2>
                        <p>Build a clearer picture of your money, starting today.</p>
                    </div>

                    {responseError && (
                        <div className="login-error" role="alert">
                            <span aria-hidden="true">!</span>
                            {responseError}
                        </div>
                    )}

                    <form
                        className="login-form register-form"
                        onSubmit={handleSubmit(onSubmit)}
                        noValidate
                    >
                        <div className="login-field">
                            <label htmlFor="username">Username</label>
                            <input
                                id="username"
                                type="text"
                                autoComplete="username"
                                placeholder="Choose a username"
                                aria-invalid={!!errors.username}
                                aria-describedby={errors.username ? 'register-username-error' : undefined}
                                {...register('username', {
                                    required: 'Username is required.'
                                })}
                            />
                            {errors.username && (
                                <small className="login-field-error" id="register-username-error">
                                    {errors.username.message}
                                </small>
                            )}
                        </div>

                        <div className="login-field">
                            <label htmlFor="register-email">Email address</label>
                            <input
                                id="register-email"
                                type="email"
                                autoComplete="email"
                                placeholder="you@example.com"
                                aria-invalid={!!errors.email}
                                aria-describedby={errors.email ? 'register-email-error' : undefined}
                                {...register('email', {
                                    required: 'Email address is required.',
                                    pattern: {
                                        value: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,
                                        message: 'Enter a valid email address.'
                                    }
                                })}
                            />
                            {errors.email && (
                                <small className="login-field-error" id="register-email-error">
                                    {errors.email.message}
                                </small>
                            )}
                        </div>

                        <div className="login-field">
                            <label htmlFor="register-password">Password</label>
                            <input
                                id="register-password"
                                type="password"
                                autoComplete="new-password"
                                placeholder="At least 8 characters"
                                aria-invalid={!!errors.password}
                                aria-describedby={errors.password ? 'register-password-error' : undefined}
                                {...register('password', {
                                    required: 'Password is required.',
                                    minLength: {
                                        value: 8,
                                        message: 'Password must have at least 8 characters.'
                                    }
                                })}
                            />
                            {errors.password && (
                                <small className="login-field-error" id="register-password-error">
                                    {errors.password.message}
                                </small>
                            )}
                        </div>

                        <div className="login-field">
                            <label htmlFor="cpassword">Confirm password</label>
                            <input
                                id="cpassword"
                                type="password"
                                autoComplete="new-password"
                                placeholder="Re-enter your password"
                                aria-invalid={!!errors.cpassword}
                                aria-describedby={errors.cpassword ? 'register-confirm-error' : undefined}
                                {...register('cpassword', {
                                    required: 'Please confirm your password.',
                                    validate: value =>
                                        value === password || 'Passwords do not match.'
                                })}
                            />
                            {errors.cpassword && (
                                <small className="login-field-error" id="register-confirm-error">
                                    {errors.cpassword.message}
                                </small>
                            )}
                        </div>

                        <button className="login-submit" type="submit" disabled={isLoading}>
                            {isLoading ? 'Creating account…' : 'Create account'}
                            {!isLoading && <span aria-hidden="true">→</span>}
                        </button>

                        <p className="register-terms">
                            By creating an account, you agree to use MyWallet responsibly.
                        </p>
                    </form>
                </div>

                <p className="login-legal">© MyWallet · A little more clarity, every day.</p>
            </section>
        </main>
    );
}

export default Register;
