import './LoadingSkeleton.css';

export const CardSkeleton = () => (
  <div className="skeleton-card">
    <div className="skeleton-line skeleton-title"></div>
    <div className="skeleton-line skeleton-text"></div>
    <div className="skeleton-line skeleton-text short"></div>
  </div>
);

export const TableSkeleton = ({ rows = 5, columns = 4 }) => (
  <div className="skeleton-table">
    <div className="skeleton-table-header">
      {Array.from({ length: columns }).map((_, i) => (
        <div key={i} className="skeleton-line skeleton-header"></div>
      ))}
    </div>
    {Array.from({ length: rows }).map((_, i) => (
      <div key={i} className="skeleton-table-row">
        {Array.from({ length: columns }).map((_, j) => (
          <div key={j} className="skeleton-line skeleton-cell"></div>
        ))}
      </div>
    ))}
  </div>
);

export const StatCardSkeleton = () => (
  <div className="skeleton-stat-card">
    <div className="skeleton-icon"></div>
    <div className="skeleton-stat-content">
      <div className="skeleton-line skeleton-stat-value"></div>
      <div className="skeleton-line skeleton-stat-label"></div>
    </div>
  </div>
);

export const FormSkeleton = () => (
  <div className="skeleton-form">
    {Array.from({ length: 4 }).map((_, i) => (
      <div key={i} className="skeleton-form-group">
        <div className="skeleton-line skeleton-label"></div>
        <div className="skeleton-line skeleton-input"></div>
      </div>
    ))}
    <div className="skeleton-line skeleton-button"></div>
  </div>
);

