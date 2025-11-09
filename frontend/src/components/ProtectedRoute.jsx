import { Navigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const ProtectedRoute = ({ children, requireAuth = true, role = null, redirectTo = '/login' }) => {
  const { isAuthenticated, hasRole, loading, user } = useAuth();

  if (loading) {
    return (
      <div className="loading">
        <div className="spinner"></div>
      </div>
    );
  }

  // If route requires authentication
  if (requireAuth) {
    if (!isAuthenticated()) {
      return <Navigate to={redirectTo} replace />;
    }

    // If specific role is required
    if (role && !hasRole(role)) {
      return <Navigate to={redirectTo} replace />;
    }
  } else {
    // If route should not be accessible when authenticated
    if (isAuthenticated()) {
      // Redirect based on role
      if (hasRole('ADMIN')) {
        return <Navigate to="/admin/dashboard" replace />;
      }
      return <Navigate to="/dashboard" replace />;
    }
  }

  return children;
};

export default ProtectedRoute;

