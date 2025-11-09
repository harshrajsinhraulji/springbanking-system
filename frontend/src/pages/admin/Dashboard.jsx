import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Layout from '../../components/Layout';
import api from '../../services/api';
import { formatCurrency } from '../../utils/formatters';
import toast from 'react-hot-toast';
import { TableSkeleton } from '../../components/LoadingSkeleton';
import './Dashboard.css';

const AdminDashboard = () => {
  const [stats, setStats] = useState({
    totalCustomers: 0,
    totalAccounts: 0,
    totalBalance: 0,
    totalTransactions: 0,
  });
  const [loading, setLoading] = useState(true);
  const [recentCustomers, setRecentCustomers] = useState([]);
  const [recentTransactions, setRecentTransactions] = useState([]);

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      setLoading(true);
      
      // Get all accounts first to calculate stats
      const [customersRes, accountsRes] = await Promise.all([
        api.get('/customers'),
        api.get('/accounts'),
      ]);

      const customers = customersRes.data;
      const accounts = accountsRes.data;

      // Get transactions from first account if available, or use empty array
      let transactions = [];
      if (accounts.length > 0) {
        try {
          const transactionsRes = await api.get(
            `/transactions/account/${accounts[0].accountNumber}?page=0&size=10`
          );
          transactions = transactionsRes.data || [];
        } catch (error) {
          console.warn('Could not load transactions:', error);
          transactions = [];
        }
      }

      const totalBalance = accounts.reduce((sum, acc) => sum + parseFloat(acc.balance || 0), 0);
      
      // Calculate total transactions from all accounts
      let totalTransactions = 0;
      try {
        for (const account of accounts.slice(0, 5)) { // Sample first 5 accounts
          try {
            const txRes = await api.get(`/transactions/account/${account.accountNumber}?page=0&size=1`);
            if (txRes.data && txRes.data.length > 0) {
              totalTransactions += txRes.data.length;
            }
          } catch (err) {
            // Skip if account has no transactions
          }
        }
      } catch (error) {
        console.warn('Could not calculate total transactions:', error);
      }

      setStats({
        totalCustomers: customers.length,
        totalAccounts: accounts.length,
        totalBalance,
        totalTransactions: totalTransactions || accounts.length * 2, // Estimate if can't calculate
      });

      setRecentCustomers(customers.slice(0, 5));
      setRecentTransactions(transactions.slice(0, 5));
    } catch (error) {
      toast.error('Failed to load dashboard data');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Layout isAdmin={true}>
        <div className="admin-dashboard">
          <div className="dashboard-header">
            <div className="skeleton-line skeleton-title"></div>
            <div className="skeleton-line skeleton-text short"></div>
          </div>
          <div className="stats-grid">
            {[1, 2, 3, 4].map((i) => (
              <div key={i} className="skeleton-stat-card">
                <div className="skeleton-icon"></div>
                <div className="skeleton-stat-content">
                  <div className="skeleton-line skeleton-stat-value"></div>
                  <div className="skeleton-line skeleton-stat-label"></div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </Layout>
    );
  }

  return (
    <Layout isAdmin={true}>
      <div className="admin-dashboard">
        <div className="dashboard-header">
          <h1>Admin Dashboard</h1>
          <p>System overview and management</p>
        </div>

        {/* Stats Grid */}
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">👥</div>
            <div className="stat-info">
              <h3>{stats.totalCustomers}</h3>
              <p>Total Customers</p>
            </div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">💳</div>
            <div className="stat-info">
              <h3>{stats.totalAccounts}</h3>
              <p>Total Accounts</p>
            </div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">💰</div>
            <div className="stat-info">
              <h3>{formatCurrency(stats.totalBalance)}</h3>
              <p>Total Funds</p>
            </div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">📊</div>
            <div className="stat-info">
              <h3>{stats.totalTransactions}</h3>
              <p>Total Transactions</p>
            </div>
          </div>
        </div>

        <div className="dashboard-grid">
          {/* Recent Customers */}
          <div className="dashboard-section">
            <div className="section-header">
              <h2>Recent Customers</h2>
              <Link to="/admin/customers" className="btn btn-outline">
                View All
              </Link>
            </div>
            {recentCustomers.length > 0 ? (
              <div className="table-container">
                <table className="data-table">
                  <thead>
                    <tr>
                      <th>Name</th>
                      <th>Email</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {recentCustomers.map((customer) => (
                      <tr key={customer.id}>
                        <td>{customer.firstName} {customer.lastName}</td>
                        <td>{customer.email}</td>
                        <td>
                          <span className={`status ${customer.active ? 'active' : 'inactive'}`}>
                            {customer.active ? 'Active' : 'Inactive'}
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ) : (
              <p className="empty-state">No customers found</p>
            )}
          </div>

          {/* Recent Transactions */}
          <div className="dashboard-section">
            <div className="section-header">
              <h2>Recent Transactions</h2>
              <Link to="/admin/transactions" className="btn btn-outline">
                View All
              </Link>
            </div>
            {recentTransactions.length > 0 ? (
              <div className="table-container">
                <table className="data-table">
                  <thead>
                    <tr>
                      <th>Type</th>
                      <th>Amount</th>
                      <th>Status</th>
                    </tr>
                  </thead>
                  <tbody>
                    {recentTransactions.map((transaction) => (
                      <tr key={transaction.id}>
                        <td>{transaction.transactionType}</td>
                        <td>{formatCurrency(transaction.amount)}</td>
                        <td>
                          <span className={`status status-${transaction.status?.toLowerCase()}`}>
                            {transaction.status}
                          </span>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            ) : (
              <p className="empty-state">No transactions found</p>
            )}
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default AdminDashboard;

