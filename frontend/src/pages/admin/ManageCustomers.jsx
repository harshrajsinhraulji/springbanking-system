import { useEffect, useState } from 'react';
import Layout from '../../components/Layout';
import api from '../../services/api';
import toast from 'react-hot-toast';
import './ManageCustomers.css';

const ManageCustomers = () => {
  const [customers, setCustomers] = useState([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState('');

  useEffect(() => {
    loadCustomers();
  }, []);

  const loadCustomers = async () => {
    try {
      setLoading(true);
      const response = await api.get('/customers');
      setCustomers(response.data);
    } catch (error) {
      toast.error('Failed to load customers');
    } finally {
      setLoading(false);
    }
  };

  const handleToggleStatus = async (customerId, currentStatus) => {
    try {
      await api.put(`/customers/${customerId}`, {
        active: !currentStatus,
      });
      toast.success(`Customer ${!currentStatus ? 'activated' : 'deactivated'}`);
      loadCustomers();
    } catch (error) {
      toast.error('Failed to update customer status');
    }
  };

  const filteredCustomers = customers.filter((customer) =>
    `${customer.firstName} ${customer.lastName} ${customer.email}`
      .toLowerCase()
      .includes(searchTerm.toLowerCase())
  );

  if (loading) {
    return (
      <Layout isAdmin={true}>
        <div className="manage-customers">
          <div className="page-header">
            <div className="skeleton-line skeleton-title"></div>
            <div className="skeleton-line skeleton-text short"></div>
          </div>
          <div className="skeleton-line skeleton-input" style={{ marginBottom: '24px', width: '400px' }}></div>
          <TableSkeleton rows={8} columns={4} />
        </div>
      </Layout>
    );
  }

  return (
    <Layout isAdmin={true}>
      <div className="manage-customers">
        <div className="page-header">
          <h1>Manage Customers</h1>
          <p>View and manage all customer accounts</p>
        </div>

        <div className="controls">
          <input
            type="text"
            className="input search-input"
            placeholder="Search customers..."
            value={searchTerm}
            onChange={(e) => setSearchTerm(e.target.value)}
          />
        </div>

        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Email</th>
                <th>Phone</th>
                <th>Status</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {filteredCustomers.length > 0 ? (
                filteredCustomers.map((customer) => (
                  <tr key={customer.id}>
                    <td>{customer.id}</td>
                    <td>{customer.firstName} {customer.lastName}</td>
                    <td>{customer.email}</td>
                    <td>{customer.phoneNumber}</td>
                    <td>
                      <span className={`status ${customer.active ? 'active' : 'inactive'}`}>
                        {customer.active ? 'Active' : 'Inactive'}
                      </span>
                    </td>
                    <td>
                      <button
                        className={`btn ${customer.active ? 'btn-danger' : 'btn-success'}`}
                        onClick={() => handleToggleStatus(customer.id, customer.active)}
                        style={{ padding: '6px 12px', fontSize: '0.85rem' }}
                      >
                        {customer.active ? 'Deactivate' : 'Activate'}
                      </button>
                    </td>
                  </tr>
                ))
              ) : (
                <tr>
                  <td colSpan="6" className="empty-state">
                    No customers found
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

export default ManageCustomers;

