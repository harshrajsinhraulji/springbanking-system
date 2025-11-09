import { format, parseISO } from 'date-fns';

export const formatCurrency = (amount) => {
  if (amount === null || amount === undefined) return '$0.00';
  return new Intl.NumberFormat('en-US', {
    style: 'currency',
    currency: 'USD',
  }).format(amount);
};

export const formatDate = (dateString, formatStr = 'MMM dd, yyyy') => {
  if (!dateString) return '';
  try {
    const date = typeof dateString === 'string' ? parseISO(dateString) : dateString;
    return format(date, formatStr);
  } catch (error) {
    return dateString;
  }
};

export const formatDateTime = (dateString) => {
  return formatDate(dateString, 'MMM dd, yyyy HH:mm');
};

export const formatAccountNumber = (accountNumber) => {
  if (!accountNumber) return '';
  // Format: ACC 1234 5678 9012
  return accountNumber.replace(/(.{3})(.{4})(.{4})(.{4})/, '$1 $2 $3 $4');
};

