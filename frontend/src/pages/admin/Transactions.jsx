import { useEffect, useState } from 'react';
import Layout from '../../components/Layout';
import api from '../../services/api';
import { formatCurrency, formatDateTime } from '../../utils/formatters';
import toast from 'react-hot-toast';
import '../customer/TransactionHistory.css';

const AdminTransactions = () => {
  const [transactions, setTransactions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [page, setPage] = useState(0);
  const [hasMore, setHasMore] = useState(true);

  useEffect(() => {
    loadTransactions();
  }, [page]);

  const loadTransactions = async () => {
    setLoading(true);
    try {
      // Get all accounts first, then get transactions for each
      const accountsRes = await api.get('/accounts');
      const accounts = accountsRes.data;
      
      if (accounts.length === 0) {
        setTransactions([]);
        return;
      }

      // Get transactions from first account as sample (in production, you'd have a dedicated endpoint)
      const accountNumber = accounts[0].accountNumber;
      const response = await api.get(
        `/transactions/account/${accountNumber}?page=${page}&size=50`
      );
      const newTransactions = response.data;
      
      if (page === 0) {
        setTransactions(newTransactions);
      } else {
        setTransactions(prev => [...prev, ...newTransactions]);
      }
      
      setHasMore(newTransactions.length === 50);
    } catch (error) {
      toast.error('Failed to load transactions');
    } finally {
      setLoading(false);
    }
  };

  return (
    <Layout isAdmin={true}>
      <div className="transactions-page">
        <div className="page-header">
          <h1>All Transactions</h1>
          <p>View system-wide transaction logs</p>
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
                    <th>Account</th>
                    <th>Type</th>
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
                      <td>{transaction.accountNumber}</td>
                      <td>
                        <span className={`transaction-type ${transaction.transactionType.toLowerCase()}`}>
                          {transaction.transactionType}
                        </span>
                      </td>
                      <td className={`amount ${transaction.transactionType === 'DEPOSIT' ? 'positive' : 'negative'}`}>
                        {transaction.transactionType === 'DEPOSIT' ? '+' : '-'}
                        {formatCurrency(transaction.amount)}
                      </td>
                      <td className="balance">{formatCurrency(transaction.balanceAfterTransaction)}</td>
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

export default AdminTransactions;

