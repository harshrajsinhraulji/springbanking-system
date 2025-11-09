import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';
import './Layout.css';

const Layout = ({ children, isAdmin = false }) => {
  const location = useLocation();
  const navigate = useNavigate();
  const { user, logout } = useAuth();

  const handleLogout = () => {
    logout();
    navigate(isAdmin ? '/admin/login' : '/login');
  };

  const customerNavItems = [
    { path: '/dashboard', label: 'Home', icon: '🏠' },
    { path: '/transfer', label: 'Transfer', icon: '💸' },
    { path: '/transactions', label: 'Transactions', icon: '📊' },
    { path: '/profile', label: 'Profile', icon: '👤' },
  ];

  const adminNavItems = [
    { path: '/admin/dashboard', label: 'Overview', icon: '📈' },
    { path: '/admin/customers', label: 'Customers', icon: '👥' },
    { path: '/admin/accounts', label: 'Accounts', icon: '💳' },
    { path: '/admin/transactions', label: 'Transactions', icon: '📋' },
  ];

  const navItems = isAdmin ? adminNavItems : customerNavItems;

  return (
    <div className="layout">
      <nav className="navbar">
        <div className="navbar-brand">
          <span className="logo-icon">🏦</span>
          <span className="brand-name">Spring Banking</span>
        </div>
        <div className="navbar-nav">
          {navItems.map((item) => (
            <Link
              key={item.path}
              to={item.path}
              className={`nav-link ${location.pathname === item.path ? 'active' : ''}`}
            >
              <span className="nav-icon">{item.icon}</span>
              <span className="nav-label">{item.label}</span>
            </Link>
          ))}
        </div>
        <div className="navbar-user">
          <span className="user-name">{user?.firstName || 'User'}</span>
          <button className="btn-logout" onClick={handleLogout}>
            Logout
          </button>
        </div>
      </nav>
      <main className="main-content">
        {children}
      </main>
    </div>
  );
};

export default Layout;

