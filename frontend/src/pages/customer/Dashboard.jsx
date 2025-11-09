import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import Layout from '../../components/Layout';
import api from '../../services/api';
import { useAuth } from '../../context/AuthContext';
import { formatCurrency, formatDate } from '../../utils/formatters';
import { LineChart, Line, XAxis, YAxis, CartesianGrid, Tooltip, ResponsiveContainer } from 'recharts';
import toast from 'react-hot-toast';
import { CardSkeleton, TableSkeleton } from '../../components/LoadingSkeleton';
import './Dashboard.css';

const CustomerDashboard = () => {
  const { user } = useAuth();
  const [accounts, setAccounts] = useState([]);
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [stats, setStats] = useState({
    totalBalance: 0,
    totalAccounts: 0,
    recentTransactions: 0,
  });

  useEffect(() => {
    loadDashboardData();
  }, []);

  const loadDashboardData = async () => {
    try {
      setLoading(true);
      
      // Load accounts
      const accountsResponse = await api.get(`/accounts/customer/${user.id}`);
      const accountsData = accountsResponse.data;
      setAccounts(accountsData);

      // Calculate total balance
      const totalBalance = accountsData.reduce((sum, acc) => sum + parseFloat(acc.balance || 0), 0);
      
      // Load recent transactions
      let transactionsData = [];
      if (accountsData.length > 0) {
        const accountNumber = accountsData[0].accountNumber;
        const transactionsResponse = await api.get(`/transactions/account/${accountNumber}?page=0&size=5`);
        transactionsData = transactionsResponse.data || [];
        setTransactions(transactionsData);
      }

      // Get transactions count after loading
      const transactionsCount = transactionsData.length;
      
      setStats({
        totalBalance,
        totalAccounts: accountsData.length,
        recentTransactions: transactionsCount,
      });
    } catch (error) {
      toast.error('Failed to load dashboard data');
      console.error(error);
    } finally {
      setLoading(false);
    }
  };

  if (loading) {
    return (
      <Layout>
        <div className="dashboard">
          <div className="dashboard-header">
            <div className="skeleton-line skeleton-title"></div>
            <div className="skeleton-line skeleton-text short"></div>
          </div>
          <div className="stats-grid">
            {[1, 2, 3].map((i) => (
              <div key={i} className="stat-card">
                <div className="skeleton-icon"></div>
                <div className="stat-info">
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

  // Prepare chart data
  const chartData = transactions.slice(0, 7).reverse().map((t) => ({
    date: formatDate(t.createdAt, 'MMM dd'),
    balance: parseFloat(t.balanceAfterTransaction || 0),
  }));

  return (
    <Layout>
      <div className="dashboard">
        <div className="dashboard-header">
          <h1>Welcome back, {user?.firstName || 'User'}!</h1>
          <p>Here's your account overview</p>
        </div>

        {/* Stats Cards */}
        <div className="stats-grid">
          <div className="stat-card">
            <div className="stat-icon">💰</div>
            <div className="stat-info">
              <h3>{formatCurrency(stats.totalBalance)}</h3>
              <p>Total Balance</p>
            </div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">💳</div>
            <div className="stat-info">
              <h3>{stats.totalAccounts}</h3>
              <p>Active Accounts</p>
            </div>
          </div>
          <div className="stat-card">
            <div className="stat-icon">📊</div>
            <div className="stat-info">
              <h3>{transactions.length}</h3>
              <p>Recent Transactions</p>
            </div>
          </div>
        </div>

        <div className="dashboard-grid">
          {/* Accounts Section */}
          <div className="dashboard-section">
            <div className="section-header">
              <h2>Your Accounts</h2>
              <Link to="/transfer" className="btn btn-primary">
                Transfer Funds
              </Link>
            </div>
            <div className="accounts-list">
              {accounts.length > 0 ? (
                accounts.map((account) => (
                  <div key={account.id} className="account-card">
                    <div className="account-header">
                      <div>
                        <h3>{account.accountNumber}</h3>
                        <p className="account-type">{account.accountType}</p>
                      </div>
                      <div className="account-balance">
                        {formatCurrency(account.balance)}
                      </div>
                    </div>
                    <div className="account-footer">
                      <span className={`status ${account.active ? 'active' : 'inactive'}`}>
                        {account.active ? 'Active' : 'Inactive'}
                      </span>
                    </div>
                  </div>
                ))
              ) : (
                <p className="empty-state">No accounts found</p>
              )}
            </div>
          </div>

          {/* Recent Transactions */}
          <div className="dashboard-section">
            <div className="section-header">
              <h2>Recent Transactions</h2>
              <Link to="/transactions" className="btn btn-outline">
                View All
              </Link>
            </div>
            {transactions.length > 0 ? (
              <>
                <div className="transactions-list">
                  {transactions.slice(0, 5).map((transaction) => (
                    <div key={transaction.id} className="transaction-item">
                      <div className="transaction-icon">
                        {transaction.transactionType === 'DEPOSIT' ? '⬇️' : 
                         transaction.transactionType === 'WITHDRAWAL' ? '⬆️' : '↔️'}
                      </div>
                      <div className="transaction-details">
                        <h4>{transaction.transactionType}</h4>
                        <p>{formatDate(transaction.createdAt)}</p>
                      </div>
                      <div className={`transaction-amount ${transaction.transactionType === 'DEPOSIT' ? 'positive' : 'negative'}`}>
                        {transaction.transactionType === 'DEPOSIT' ? '+' : '-'}
                        {formatCurrency(transaction.amount)}
                      </div>
                    </div>
                  ))}
                </div>
                {chartData.length > 0 && (
                  <div className="chart-container">
                    <h3>Balance Trend</h3>
                    <ResponsiveContainer width="100%" height={200}>
                      <LineChart data={chartData}>
                        <CartesianGrid strokeDasharray="3 3" />
                        <XAxis dataKey="date" />
                        <YAxis />
                        <Tooltip />
                        <Line type="monotone" dataKey="balance" stroke="#2563eb" strokeWidth={2} />
                      </LineChart>
                    </ResponsiveContainer>
                  </div>
                )}
              </>
            ) : (
              <p className="empty-state">No transactions yet</p>
            )}
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default CustomerDashboard;

