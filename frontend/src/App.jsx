import { Routes, Route, Navigate } from 'react-router-dom';
import { useAuth } from './context/AuthContext';

// Customer Pages
import CustomerLogin from './pages/customer/Login';
import Register from './pages/customer/Register';
import CustomerDashboard from './pages/customer/Dashboard';
import TransferFunds from './pages/customer/TransferFunds';
import TransactionHistory from './pages/customer/TransactionHistory';
import Profile from './pages/customer/Profile';

// Admin Pages
import AdminLogin from './pages/admin/Login';
import AdminDashboard from './pages/admin/Dashboard';
import ManageCustomers from './pages/admin/ManageCustomers';
import ManageAccounts from './pages/admin/ManageAccounts';
import AdminTransactions from './pages/admin/Transactions';

// Protected Route Component
import ProtectedRoute from './components/ProtectedRoute';

function App() {
  return (
    <Routes>
      {/* Landing - Redirect to login */}
      <Route path="/" element={<Navigate to="/login" replace />} />

      {/* Customer Routes */}
      <Route 
        path="/login" 
        element={
          <ProtectedRoute requireAuth={false} redirectTo="/dashboard">
            <CustomerLogin />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/register" 
        element={
          <ProtectedRoute requireAuth={false} redirectTo="/dashboard">
            <Register />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/dashboard" 
        element={
          <ProtectedRoute requireAuth={true} role="CUSTOMER" redirectTo="/login">
            <CustomerDashboard />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/transfer" 
        element={
          <ProtectedRoute requireAuth={true} role="CUSTOMER" redirectTo="/login">
            <TransferFunds />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/transactions" 
        element={
          <ProtectedRoute requireAuth={true} role="CUSTOMER" redirectTo="/login">
            <TransactionHistory />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/profile" 
        element={
          <ProtectedRoute requireAuth={true} role="CUSTOMER" redirectTo="/login">
            <Profile />
          </ProtectedRoute>
        } 
      />

      {/* Admin Routes */}
      <Route 
        path="/admin/login" 
        element={
          <ProtectedRoute requireAuth={false} redirectTo="/admin/dashboard">
            <AdminLogin />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/admin/dashboard" 
        element={
          <ProtectedRoute requireAuth={true} role="ADMIN" redirectTo="/admin/login">
            <AdminDashboard />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/admin/customers" 
        element={
          <ProtectedRoute requireAuth={true} role="ADMIN" redirectTo="/admin/login">
            <ManageCustomers />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/admin/accounts" 
        element={
          <ProtectedRoute requireAuth={true} role="ADMIN" redirectTo="/admin/login">
            <ManageAccounts />
          </ProtectedRoute>
        } 
      />
      <Route 
        path="/admin/transactions" 
        element={
          <ProtectedRoute requireAuth={true} role="ADMIN" redirectTo="/admin/login">
            <AdminTransactions />
          </ProtectedRoute>
        } 
      />

      {/* 404 */}
      <Route path="*" element={<Navigate to="/login" replace />} />
    </Routes>
  );
}

export default App;

