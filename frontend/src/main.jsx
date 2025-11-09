import React from 'react';
import ReactDOM from 'react-dom/client';
import { BrowserRouter } from 'react-router-dom';
import { Toaster } from 'react-hot-toast';
import App from './App';
import { AuthProvider } from './context/AuthContext';
import ErrorBoundary from './components/ErrorBoundary';
import './index.css';

ReactDOM.createRoot(document.getElementById('root')).render(
  <React.StrictMode>
    <ErrorBoundary>
      <BrowserRouter>
        <AuthProvider>
          <App />
          <Toaster 
          position="top-right"
          toastOptions={{
            duration: 4000,
            style: {
              background: 'rgba(255, 255, 255, 0.95)',
              backdropFilter: 'blur(10px)',
              color: 'var(--text)',
              border: '1px solid var(--border-light)',
              borderRadius: '12px',
              boxShadow: 'var(--shadow-lg)',
              padding: '16px',
              fontSize: '0.9rem',
            },
            success: {
              duration: 3000,
              iconTheme: {
                primary: '#10b981',
                secondary: '#fff',
              },
              style: {
                background: 'rgba(255, 255, 255, 0.95)',
                color: 'var(--text)',
                border: '1px solid var(--success-subtle)',
              },
            },
            error: {
              duration: 4000,
              iconTheme: {
                primary: '#ef4444',
                secondary: '#fff',
              },
              style: {
                background: 'rgba(255, 255, 255, 0.95)',
                color: 'var(--text)',
                border: '1px solid var(--danger-subtle)',
              },
            },
          }}
        />
        </AuthProvider>
      </BrowserRouter>
    </ErrorBoundary>
  </React.StrictMode>
);

