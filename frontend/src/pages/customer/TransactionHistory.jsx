import { useState, useEffect } from 'react';
import Layout from '../../components/Layout';
import api from '../../services/api';
import { useAuth } from '../../context/AuthContext';
import { formatCurrency, formatDateTime } from '../../utils/formatters';
import toast from 'react-hot-toast';
import './TransactionHistory.css';

const TransactionHistory = () => {
  const { user } = useAuth();
  const [accounts, setAccounts] = useState([]);
  const [selectedAccount, setSelectedAccount] = useState('');
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    loadAccounts();
  }, []);

  useEffect(() => {
    if (selectedAccount) {
      loadTransactions();
    }
  }, [selectedAccount, page]);

  const loadAccounts = async () => {
    try {
      if (!user || !user.id) {
        throw new Error('User not authenticated');
      }
      const response = await api.get(`/accounts/customer/${user.id}`);
      setAccounts(response.data);
      if (response.data.length > 0) {
        setSelectedAccount(response.data[0].accountNumber);
      }
    } catch (error) {
      toast.error('Failed to load accounts');
    }
  };

  const loadTransactions = async () => {
    if (!selectedAccount) return;

    setLoading(true);
    try {
      const response = await api.get(
        `/transactions/account/${selectedAccount}?page=${page}&size=20`
      );
      const newTransactions = response.data;
      
      if (page === 0) {
        setTransactions(newTransactions);
      } else {
        setTransactions(prev => [...prev, ...newTransactions]);
      }
      
      setHasMore(newTransactions.length === 20);
    } catch (error) {
      toast.error('Failed to load transactions');
    } finally {
      setLoading(false);
    }
  };

  const getTransactionIcon = (type) => {
    switch (type) {
      case 'DEPOSIT':
        return '⬇️';
      case 'WITHDRAWAL':
        return '⬆️';
      case 'TRANSFER':
        return '↔️';
      default:
        return '💰';
    }
  };

  const getTransactionColor = (type) => {
    switch (type) {
      case 'DEPOSIT':
        return 'success';
      case 'WITHDRAWAL':
        return 'danger';
      case 'TRANSFER':
        return 'info';
      default:
        return 'muted';
    }
  };

  return (
    <Layout>
      <div className="transactions-page">
        <div className="page-header">
          <h1>Transaction History</h1>
          <p>View all your account transactions</p>
        </div>

        <div className="transactions-controls">
          <select
            className="input account-select"
            value={selectedAccount}
            onChange={(e) => {
              setSelectedAccount(e.target.value);
              setPage(0);
            }}
          >
            {accounts.map((account) => (
              <option key={account.id} value={account.accountNumber}>
                {account.accountNumber} - {account.accountType}
              </option>
            ))}
          </select>
        </div>

        {loading && transactions.length === 0 ? (
          <div className="loading">
            <div className="spinner"></div>
          </div>
        ) : transactions.length > 0 ? (
          <>
            <div className="transactions-table-container">
              <table className="transactions-table">
                <thead>
                  <tr>
                    <th>Date</th>
                    <th>Transaction ID</th>
                    <th>Type</th>
                    <th>Description</th>
                    <th>Amount</th>
                    <th>Balance</th>
                    <th>Status</th>
                  </tr>
                </thead>
                <tbody>
                  {transactions.map((transaction) => (
                    <tr key={transaction.id}>
                      <td>{formatDateTime(transaction.createdAt)}</td>
                      <td className="transaction-id">{transaction.transactionId}</td>
                      <td>
                        <span className={`transaction-type ${getTransactionColor(transaction.transactionType)}`}>
                          <span className="type-icon">{getTransactionIcon(transaction.transactionType)}</span>
                          {transaction.transactionType}
                        </span>
                      </td>
                      <td>{transaction.description || '-'}</td>
                      <td className={`amount ${transaction.transactionType === 'DEPOSIT' ? 'positive' : 'negative'}`}>
                        {transaction.transactionType === 'DEPOSIT' ? '+' : '-'}
                        {formatCurrency(transaction.amount)}
                      </td>
                      <td className="balance">{formatCurrency(transaction.balanceAfterTransaction)}</td>
                      <td>
                        <span className={`status status-${transaction.status.toLowerCase()}`}>
                          {transaction.status}
                        </span>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>

            {hasMore && (
              <div className="load-more">
                <button
                  className="btn btn-outline"
                  onClick={() => setPage(prev => prev + 1)}
                  disabled={loading}
                >
                  {loading ? 'Loading...' : 'Load More'}
                </button>
              </div>
            )}
          </>
        ) : (
          <div className="empty-state">
            <p>No transactions found</p>
          </div>
        )}
      </div>
    </Layout>
  );
};

export default TransactionHistory;

