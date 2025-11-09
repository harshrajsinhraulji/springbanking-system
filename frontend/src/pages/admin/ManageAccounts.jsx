import { useEffect, useState } from 'react';
import Layout from '../../components/Layout';
import api from '../../services/api';
import { formatCurrency } from '../../utils/formatters';
import toast from 'react-hot-toast';
import './ManageAccounts.css';

const ManageAccounts = () => {
  const [accounts, setAccounts] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    try {
      setLoading(true);
      const response = await api.get('/accounts');
      setAccounts(response.data);
    } catch (error) {
      toast.error('Failed to load accounts');
    } finally {
      setLoading(false);
    }
  };

  const handleToggleStatus = async (accountId, currentStatus) => {
    try {
      await api.put(`/accounts/${accountId}`, {
        active: !currentStatus,
      });
      toast.success(`Account ${!currentStatus ? 'activated' : 'deactivated'}`);
      loadAccounts();
    } catch (error) {
      toast.error('Failed to update account status');
    }
  };

  const filteredAccounts = accounts.filter((account) =>
    `${account.accountNumber} ${account.customerName}`
      .toLowerCase()
      .includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return (
      <Layout isAdmin={true}>
        <div className="manage-accounts">
          <div className="page-header">
            <div className="skeleton-line skeleton-title"></div>
            <div className="skeleton-line skeleton-text short"></div>
          </div>
          <div className="skeleton-line skeleton-input" style={{ marginBottom: '24px', width: '400px' }}></div>
          <TableSkeleton rows={8} columns={5} />
        </div>
      </Layout>
    );
  }

  return (
    <Layout isAdmin={true}>
      <div className="manage-accounts">
        <div className="page-header">
          <h1>Manage Accounts</h1>
          <p>View and manage all bank accounts</p>
        </div>

        <div className="controls">
          <input
            type="text"
            className="input search-input"
            placeholder="Search accounts..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th>Account Number</th>
                <th>Customer</th>
                <th>Type</th>
                <th>Balance</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredAccounts.length > 0 ? (
                filteredAccounts.map((account) => (
                  <tr key={account.id}>
                    <td className="account-number">{account.accountNumber}</td>
                    <td>{account.customerName}</td>
                    <td>{account.accountType}</td>
                    <td className="balance">{formatCurrency(account.balance)}</td>
                    <td>
                      <span className={`status ${account.active ? 'active' : 'inactive'}`}>
                        {account.active ? 'Active' : 'Inactive'}
                      </span>
                    </td>
                    <td>
                      <button
                        className={`btn ${account.active ? 'btn-danger' : 'btn-success'}`}
                        onClick={() => handleToggleStatus(account.id, account.active)}
                        style={{ padding: '6px 12px', fontSize: '0.85rem' }}
                      >
                        {account.active ? 'Freeze' : 'Unfreeze'}
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" className="empty-state">
                    No accounts found
                  </td>
                </tr>
              )}
            </tbody>
          </table>
        </div>
      </div>
    </Layout>
  );
};

export default ManageAccounts;

