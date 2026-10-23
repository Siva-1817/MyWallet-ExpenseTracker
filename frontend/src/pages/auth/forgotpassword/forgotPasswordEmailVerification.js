import React, { useRef, useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { useForm } from 'react-hook-form';
import AuthService from '../../../services/auth.service';
import Logo from '../../../components/utils/Logo';
import '../../../assets/styles/forgot-password.css';

const ForgotPassword = () => {
    const navigate = useNavigate();
    const { register, handleSubmit, watch, formState } = useForm();
    const password = useRef("");
    password.current = watch('password', "");
    const [responseError, setResponseError] = useState("");
    const [isLoading, setIsLoading] = useState(false);

    const onSubmit = async (data) => {
        setIsLoading(true);
        setResponseError("");
        try {
            const response = await AuthService.resetPassword(
                data.email,
                data.password,
                data.confirmPassword
            );
            if (response.data.status === 'SUCCESS') {
                navigate('/auth/login', { replace: true });
            } else {
                setResponseError(response.data.response || 'Password reset failed.');
            }
        } catch (error) {
            setResponseError(
                error.response?.data?.response || 'Password reset failed. Please try again.'
            );
        } finally {
            setIsLoading(false);
        }
    }

  return (
    <div className='container forgot-password-page'>
            <form className="auth-form" onSubmit={handleSubmit(onSubmit)}>
                <Logo/>
                <h3 style={{textAlign:'center'}}>Reset your password</h3><br/>
                <div className='msg' style={{textAlign: 'center', fontWeight: 600}}>
                    Enter your registered email and choose a new password.
                </div>
                <p className='msg' role='note'>
                    Local demo only: reset by email does not verify account ownership. Do not use this feature in production.
                </p><br/>

                {
                    responseError && <p role='alert'>{responseError}</p>
                }
                
                <div className='input-box'>
                    <label>Email address</label><br/>
                    <input
                        type='email'
                        autoComplete='email'
                        placeholder='Email address'
                        {...register('email', {
                            required: "Email is required.",
                            pattern: { value: /^[\w-.]+@([\w-]+\.)+[\w-]{2,4}$/, message: "Enter a valid email address." }
                        })}
                    />
                    {formState.errors.email && <small>{formState.errors.email.message}</small>}
                </div>

                <div className='input-box'>
                    <label>New password</label><br/>
                    <input
                        type='password'
                        autoComplete='new-password'
                        {...register('password', {
                            required: 'New password is required.',
                            minLength: { value: 8, message: 'Password must have at least 8 characters.' },
                            maxLength: { value: 20, message: 'Password must have at most 20 characters.' }
                        })}
                    />
                    {formState.errors.password && <small>{formState.errors.password.message}</small>}
                </div>

                <div className='input-box'>
                    <label>Confirm new password</label><br/>
                    <input
                        type='password'
                        autoComplete='new-password'
                        {...register('confirmPassword', {
                            required: 'Please confirm your new password.',
                            validate: value => value === password.current || 'Passwords do not match.'
                        })}
                    />
                    {formState.errors.confirmPassword && <small>{formState.errors.confirmPassword.message}</small>}
                </div>

                <div className='input-box'>
                    <input type='submit' value={isLoading ? "Resetting" : 'Reset password'}
                     className={isLoading ? "button button-fill loading" : "button button-fill"}
                     disabled={isLoading}
                    />
                </div>
                <br/>
                <div className='msg'> <Link to={'/auth/login'} className='inline-link'>Back to Login</Link></div>

            </form>
    </div>
  );
};

export default ForgotPassword;
