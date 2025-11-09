import { createContext, useContext, useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../services/api';
import toast from 'react-hot-toast';

const AuthContext = createContext(null);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);
  const [token, setToken] = useState(localStorage.getItem('token'));

  useEffect(() => {
    // Check if user is already logged in
    const storedToken = localStorage.getItem('token');
    const storedUser = localStorage.getItem('user');
    
    if (storedToken && storedUser) {
      try {
        const userData = JSON.parse(storedUser);
        setUser(userData);
        setToken(storedToken);
        api.defaults.headers.common['Authorization'] = `Bearer ${storedToken}`;
      } catch (error) {
        console.error('Error parsing stored user data:', error);
        localStorage.removeItem('token');
        localStorage.removeItem('user');
      }
    }
    setLoading(false);
  }, []);

  const login = async (email, password, isAdmin = false) => {
    try {
      const endpoint = isAdmin ? '/admin/login' : '/customers/login';
      const response = await api.post(endpoint, { email, password });
      
      const { token: authToken, user: userData } = response.data;
      
      // Store token and user
      localStorage.setItem('token', authToken);
      localStorage.setItem('user', JSON.stringify(userData));
      
      setToken(authToken);
      setUser(userData);
      
      // Set default authorization header
      api.defaults.headers.common['Authorization'] = `Bearer ${authToken}`;
      
      toast.success(`Welcome back, ${userData.firstName || 'User'}!`);
      return { success: true, user: userData };
    } catch (error) {
      const message = error.response?.data?.message || error.message || 'Login failed. Please check your credentials.';
      toast.error(message);
      return { success: false, error: message };
    }
  };

  const logout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('user');
    setToken(null);
    setUser(null);
    delete api.defaults.headers.common['Authorization'];
    toast.success('Logged out successfully');
  };

  const isAuthenticated = () => {
    return !!token && !!user;
  };

  const hasRole = (role) => {
    if (!user || !user.roles) return false;
    // Handle both Set and Array formats
    if (Array.isArray(user.roles)) {
      return user.roles.includes(role);
    }
    if (user.roles instanceof Set) {
      return user.roles.has(role);
    }
    return false;
  };

  const value = {
    user,
    token,
    loading,
    login,
    logout,
    isAuthenticated,
    hasRole,
    setUser,
  };

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
};

