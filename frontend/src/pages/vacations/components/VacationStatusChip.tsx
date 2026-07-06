import React from 'react';
import { Chip } from '@mui/material';
import type { Vacation } from '../hooks/useVacations';

const statusConfig: Record<Vacation['status'], { label: string; color: 'warning' | 'success' | 'error' }> = {
    PENDING: { label: 'Pending', color: 'warning' },
    APPROVED: { label: 'Approved', color: 'success' },
    REJECTED: { label: 'Rejected', color: 'error' },
};

interface Props {
    status: Vacation['status'];
}

const VacationStatusChip: React.FC<Props> = ({ status }) => {
    const config = statusConfig[status];
    return <Chip label={config.label} color={config.color} size="small" />;
};

export default VacationStatusChip;
