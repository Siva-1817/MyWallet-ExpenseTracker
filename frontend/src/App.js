import './index.css';
import './assets/styles/header.css'
import './assets/styles/register.css'
import { Suspense, lazy, useEffect, useRef } from 'react';
import { Routes, Route, Navigate, Outlet, useLocation, useNavigate, useNavigationType } from "react-router-dom";
import './assets/styles/user.css'
import './assets/styles/modern-theme.css'
import Loading from './components/utils/loading';
import AuthService from './services/auth.service'
import { ThemeContext, useTheme } from './contexts/ThemeContext.js';
import NewSavedTransaction from './pages/user/newSavedTransaction.js';
import SavedTransactions from './pages/user/savedTransactions.js';
import EditSavedTransaction from './pages/user/editSavedTransaction.js';

const Welcome = lazy(() => import('./pages/welcome.js'))
const Login = lazy(() => import('./pages/auth/login/login.js'))
const Register = lazy(() => import('./pages/auth/register/register.js'))
const Dashboard = lazy(() => import('./pages/user/dashboard.js'))
const Transactions = lazy(() => import("./pages/user/transactions.js"))
const NewTransaction = lazy(() => import("./pages/user/newTransaction.js"))
const EditTransaction = lazy(() => import("./pages/user/editTransaction.js"))
const ForgotPassword = lazy(() => import('./pages/auth/forgotpassword/forgotPasswordEmailVerification.js'))
const UnAuthorizedAccessPage = lazy(() => import('./pages/auth/unAuthorized.js'))
const AdminTransactionsManagement = lazy(() => import('./pages/admin/transactions.js'))
const AdminUsersManagement = lazy(() => import('./pages/admin/users.js'))
const AdminCategoriesManagement = lazy(() => import('./pages/admin/categories.js'))
const NotFoundPage = lazy(() => import('./pages/auth/notFound'))
const NewCategory = lazy(() => import('./pages/admin/newCategory.js'))
const EditCategory = lazy(() => import('./pages/admin/editCategory.js'))
const AdminProfile = lazy(() => import('./pages/admin/adminProfile.js'))
const UserProfile = lazy(() => import('./pages/user/userProfile.js'))
const UserStatistics = lazy(() => import('./pages/user/statistics.js'))

function App() {

    const [isDarkMode, toggleTheme] = useTheme()

    const ProtectedRoute = ({ isAllowed, redirectPath = '/unauthorized', children }) => {
        if (!isAllowed) {
            return <Navigate to={redirectPath} replace />;
        }

        return children ? children : <Outlet />
    }

    return (
        <Suspense fallback={<LoadingSpinner />}>
            <ThemeContext.Provider value={{ isDarkMode, toggleTheme }}>
                <RoutesWrapper isDarkMode={isDarkMode}>
                    <AuthNavigationGuard />
                    <Routes>
                        <Route element={<ProtectedRoute isAllowed={!AuthService.getCurrentUser()} />}>
                            <Route path='/auth/forgotPassword' element={<ForgotPassword />} />
                            <Route path='/auth/forgetpassword/verifyEmail' element={<ForgotPassword />} />
                        </Route>

                        <Route element={<ProtectedRoute isAllowed={AuthService.getCurrentUser()?.roles?.includes("ROLE_USER")} redirectPath="/auth/login" />}>
                            <Route path="/user/dashboard" element={<Dashboard />} />
                            <Route path="/user/newTransaction" element={<NewTransaction />} />
                            <Route path="/user/transactions" element={<Transactions />} />
                            <Route path="/user/editTransaction/:transactionId" element={<EditTransaction />} />
                            <Route path="/user/savedTransactions" element={<SavedTransactions />} />
                            <Route path="/user/savedTransactions/new" element={<NewSavedTransaction />} />
                            <Route path="/user/editSavedTransaction/:transactionId" element={<EditSavedTransaction />} />
                            <Route path='/user/statistics' element={<UserStatistics />} />
                            <Route path='/user/settings' element={<UserProfile />} />
                        </Route>

                        <Route element={<ProtectedRoute isAllowed={AuthService.getCurrentUser()?.roles?.includes("ROLE_ADMIN")} redirectPath="/auth/login" />}>
                            <Route path='/admin/transactions' element={<AdminTransactionsManagement />} />
                            <Route path='/admin/users' element={<AdminUsersManagement />} />
                            <Route path='/admin/categories' element={<AdminCategoriesManagement />} />
                            <Route path='/admin/newCategory' element={<NewCategory />} />
                            <Route path='/admin/editCategory/:categoryId' element={<EditCategory />} />
                            <Route path='/admin/settings' element={<AdminProfile />} />
                        </Route>

                        <Route path="/" element={<Welcome />} />
                        <Route path="/auth/login" element={<Login />} />
                        <Route path="/auth/register" element={<Register />} />
                        <Route path="/unauthorized" element={<UnAuthorizedAccessPage />} />
                        <Route path="*" element={<NotFoundPage />} />
                    </Routes>
                </RoutesWrapper>
            </ThemeContext.Provider>
        </Suspense>

    );
}

function AuthNavigationGuard() {
    const location = useLocation();
    const navigate = useNavigate();
    const navigationType = useNavigationType();
    const historyIndex = useRef(window.history.state?.idx ?? 0);

    useEffect(() => {
        const currentIndex = window.history.state?.idx;
        if (typeof currentIndex !== 'number') {
            return;
        }

        const movedBack = navigationType === 'POP' && currentIndex < historyIndex.current;
        historyIndex.current = currentIndex;

        if (movedBack && AuthService.getCurrentUser()) {
            AuthService.logout_req();
        }

        if (!AuthService.getCurrentUser() && isProtectedPath(location.pathname)) {
            navigate('/auth/login', { replace: true });
        }
    }, [location.key, location.pathname, navigationType, navigate]);

    useEffect(() => {
        const redirectToLogin = () => navigate('/auth/login', { replace: true });
        const handleStorage = (event) => {
            if (event.key === 'user' && !event.newValue && isProtectedPath(window.location.pathname)) {
                redirectToLogin();
            }
        };

        const handlePageShow = () => {
            if (!AuthService.getCurrentUser() && isProtectedPath(window.location.pathname)) {
                redirectToLogin();
            }
        };

        window.addEventListener('storage', handleStorage);
        window.addEventListener('pageshow', handlePageShow);
        return () => {
            window.removeEventListener('storage', handleStorage);
            window.removeEventListener('pageshow', handlePageShow);
        };
    }, [navigate]);

    return null;
}

function isProtectedPath(pathname) {
    return pathname.startsWith('/user/') || pathname.startsWith('/admin/');
}

function RoutesWrapper({ children, isDarkMode }) {
    return (
        <div className={isDarkMode ? "dark" : "light"}>
            {children}
        </div>
    )

}

function LoadingSpinner() {
    return (
        <div style={{ width: '100%', height: '100vh' }}>
            <Loading />
        </div>
    )
}

export default App;
