import React from 'react';
import {
    Box,
    Card,
    CardContent,
    Typography,
    Grid,
    Alert,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Skeleton,
} from '@mui/material';
import {
    PeopleRounded,
    PendingActionsRounded,
    CheckCircleRounded,
    CancelRounded,
} from '@mui/icons-material';
import { useDashboard } from './hooks/useDashboard';
import VacationStatusChip from '../vacations/components/VacationStatusChip';

interface StatCardProps {
    title: string;
    value: string | number;
    icon: React.ReactNode;
    color: string;
    subtitle?: string;
    loading?: boolean;
}

const StatCard: React.FC<StatCardProps> = ({ title, value, icon, color, subtitle, loading }) => (
    <Card sx={{ height: '100%' }}>
        <CardContent sx={{ p: 3 }}>
            <Box sx={{ display: 'flex', alignItems: 'flex-start', justifyContent: 'space-between' }}>
                <Box>
                    <Typography variant="body2" color="text.secondary" sx={{ fontWeight: 500 }}>
                        {title}
                    </Typography>
                    {loading ? (
                        <Skeleton width={48} height={40} sx={{ mt: 0.5 }} />
                    ) : (
                        <Typography variant="h4" sx={{ fontWeight: 700, mt: 0.5 }}>
                            {value}
                        </Typography>
                    )}
                    {subtitle && (
                        <Typography variant="caption" color="text.secondary">
                            {subtitle}
                        </Typography>
                    )}
                </Box>
                <Box
                    sx={{
                        width: 48,
                        height: 48,
                        borderRadius: 2,
                        backgroundColor: `${color}20`,
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'center',
                        color: color,
                    }}
                >
                    {icon}
                </Box>
            </Box>
        </CardContent>
    </Card>
);

const formatDate = (date: string) =>
    new Date(date + 'T00:00:00').toLocaleDateString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
    });

const formatDateTime = (date: string) =>
    new Date(date).toLocaleString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
    });

const Dashboard: React.FC = () => {
    const { summary, recentVacations, loading, error } = useDashboard();

    const stats = [
        {
            title: 'Collaborators',
            value: summary?.totalCollaborators ?? 0,
            icon: <PeopleRounded />,
            color: '#6366f1',
            subtitle: 'With vacation requests',
        },
        {
            title: 'Pending',
            value: summary?.totalPending ?? 0,
            icon: <PendingActionsRounded />,
            color: '#f59e0b',
            subtitle: 'Awaiting approval',
        },
        {
            title: 'Approved',
            value: summary?.totalApproved ?? 0,
            icon: <CheckCircleRounded />,
            color: '#10b981',
            subtitle: 'Confirmed requests',
        },
        {
            title: 'Rejected',
            value: summary?.totalRejected ?? 0,
            icon: <CancelRounded />,
            color: '#ef4444',
            subtitle: 'Declined requests',
        },
    ];

    return (
        <Box>
            <Box sx={{ mb: 3 }}>
                <Typography variant="h5" sx={{ fontWeight: 700 }}>
                    Overview
                </Typography>
                <Typography variant="body2" color="text.secondary">
                    Welcome back! Here's what's happening.
                </Typography>
            </Box>

            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

            <Grid container spacing={3}>
                {stats.map((stat) => (
                    <Grid size={{ xs: 12, sm: 6, lg: 3 }} key={stat.title}>
                        <StatCard {...stat} loading={loading} />
                    </Grid>
                ))}
            </Grid>

            <Card sx={{ mt: 3 }}>
                <CardContent sx={{ p: 0 }}>
                    <Box sx={{ px: 3, py: 2, borderBottom: 1, borderColor: 'divider' }}>
                        <Typography variant="subtitle1" sx={{ fontWeight: 700 }}>
                            Recent Requests
                        </Typography>
                        <Typography variant="caption" color="text.secondary">
                            Last 5 vacation requests
                        </Typography>
                    </Box>

                    <TableContainer>
                        <Table size="small">
                            <TableHead>
                                <TableRow>
                                    <TableCell sx={{ fontWeight: 600 }}>Employee</TableCell>
                                    <TableCell sx={{ fontWeight: 600 }}>Period</TableCell>
                                    <TableCell sx={{ fontWeight: 600 }}>Status</TableCell>
                                    <TableCell sx={{ fontWeight: 600 }}>Requested on</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                {loading
                                    ? Array.from({ length: 5 }).map((_, i) => (
                                        <TableRow key={i}>
                                            {Array.from({ length: 4 }).map((__, j) => (
                                                <TableCell key={j}>
                                                    <Skeleton variant="text" />
                                                </TableCell>
                                            ))}
                                        </TableRow>
                                    ))
                                    : recentVacations.length === 0
                                        ? (
                                            <TableRow>
                                                <TableCell colSpan={4} align="center" sx={{ py: 4 }}>
                                                    <Typography color="text.secondary">
                                                        No vacation requests yet.
                                                    </Typography>
                                                </TableCell>
                                            </TableRow>
                                        )
                                        : recentVacations.map((v) => (
                                            <TableRow key={v.id} hover>
                                                <TableCell>{v.employeeName}</TableCell>
                                                <TableCell>
                                                    {formatDate(v.startDate)} — {formatDate(v.endDate)}
                                                </TableCell>
                                                <TableCell>
                                                    <VacationStatusChip status={v.status} />
                                                </TableCell>
                                                <TableCell>{formatDateTime(v.createdAt)}</TableCell>
                                            </TableRow>
                                        ))}
                            </TableBody>
                        </Table>
                    </TableContainer>
                </CardContent>
            </Card>
        </Box>
    );
};

export default Dashboard;
