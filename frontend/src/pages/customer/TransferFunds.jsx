import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import Layout from '../../components/Layout';
import api from '../../services/api';
import { useAuth } from '../../context/AuthContext';
import toast from 'react-hot-toast';
import './TransferFunds.css';

const TransferFunds = () => {
  const { user } = useAuth();
  const navigate = useNavigate();
  const [accounts, setAccounts] = useState([]);
  const [formData, setFormData] = useState({
    fromAccountNumber: '',
    toAccountNumber: '',
    amount: '',
    description: '',
  });
  const [loading, setLoading] = useState(false);
  const [loadingAccounts, setLoadingAccounts] = useState(true);

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    try {
      if (!user || !user.id) {
        throw new Error('User not authenticated');
      }
      const response = await api.get(`/accounts/customer/${user.id}`);
      setAccounts(response.data);
      if (response.data.length > 0) {
        setFormData(prev => ({
          ...prev,
          fromAccountNumber: response.data[0].accountNumber,
        }));
      }
    } catch (error) {
      toast.error('Failed to load accounts');
    } finally {
      setLoadingAccounts(false);
    }
  };

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    if (formData.fromAccountNumber === formData.toAccountNumber) {
      toast.error('Cannot transfer to the same account');
      return;
    }

    if (parseFloat(formData.amount) <= 0) {
      toast.error('Amount must be greater than 0');
      return;
    }

    setLoading(true);

    try {
      const transferData = {
        fromAccountNumber: formData.fromAccountNumber,
        toAccountNumber: formData.toAccountNumber,
        amount: parseFloat(formData.amount),
        description: formData.description || 'Fund transfer',
      };

      const response = await api.post('/transactions/transfer', transferData);
      
      toast.success(`Transfer of $${formData.amount} completed successfully!`);
      navigate('/dashboard');
    } catch (error) {
      const message = error.response?.data?.message || 'Transfer failed. Please try again.';
      toast.error(message);
    } finally {
      setLoading(false);
    }
  };

  const selectedAccount = accounts.find(acc => acc.accountNumber === formData.fromAccountNumber);
  const availableBalance = selectedAccount ? parseFloat(selectedAccount.balance || 0) : 0;

  if (loadingAccounts) {
    return (
      <Layout>
        <div className="loading">
          <div className="spinner"></div>
        </div>
      </Layout>
    );
  }

  return (
    <Layout>
      <div className="transfer-page">
        <div className="page-header">
          <h1>Transfer Funds</h1>
          <p>Send money to another account</p>
        </div>

        <div className="transfer-container">
          <div className="transfer-card">
            {selectedAccount && (
              <div className="balance-info">
                <p>Available Balance</p>
                <h2>${availableBalance.toFixed(2)}</h2>
              </div>
            )}

            <form onSubmit={handleSubmit} className="transfer-form">
              <div className="form-group">
                <label htmlFor="fromAccountNumber">From Account</label>
                <select
                  id="fromAccountNumber"
                  name="fromAccountNumber"
                  className="input"
                  value={formData.fromAccountNumber}
                  onChange={handleChange}
                  required
                >
                  <option value="">Select Account</option>
                  {accounts
                    .filter(acc => acc.active)
                    .map((account) => (
                      <option key={account.id} value={account.accountNumber}>
                        {account.accountNumber} - {account.accountType} (${parseFloat(account.balance).toFixed(2)})
                      </option>
                    ))}
                </select>
              </div>

              <div className="form-group">
                <label htmlFor="toAccountNumber">To Account Number</label>
                <input
                  type="text"
                  id="toAccountNumber"
                  name="toAccountNumber"
                  className="input"
                  placeholder="Enter recipient account number"
                  value={formData.toAccountNumber}
                  onChange={handleChange}
                  required
                />
              </div>

              <div className="form-group">
                <label htmlFor="amount">Amount</label>
                <input
                  type="number"
                  id="amount"
                  name="amount"
                  className="input"
                  placeholder="0.00"
                  step="0.01"
                  min="0.01"
                  max={availableBalance}
                  value={formData.amount}
                  onChange={handleChange}
                  required
                />
                {formData.amount && parseFloat(formData.amount) > availableBalance && (
                  <p className="error-text">Insufficient balance</p>
                )}
              </div>

              <div className="form-group">
                <label htmlFor="description">Description (Optional)</label>
                <input
                  type="text"
                  id="description"
                  name="description"
                  className="input"
                  placeholder="Add a note for this transfer"
                  value={formData.description}
                  onChange={handleChange}
                />
              </div>

              <button
                type="submit"
                className="btn btn-primary transfer-btn"
                disabled={loading || !formData.fromAccountNumber || parseFloat(formData.amount) > availableBalance}
              >
                {loading ? 'Processing...' : 'Transfer Funds'}
              </button>
            </form>
          </div>
        </div>
      </div>
    </Layout>
  );
};

export default TransferFunds;

