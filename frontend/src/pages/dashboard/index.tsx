import React from 'react';
import { Box, Card, CardContent, Typography, Grid } from '@mui/material';
import {
    PeopleRounded,
    BeachAccessRounded,
    PendingActionsRounded,
    CheckCircleRounded,
} from '@mui/icons-material';

interface StatCardProps {
    title: string;
    value: string | number;
    icon: React.ReactNode;
    color: string;
    subtitle?: string;
}

const StatCard: React.FC<StatCardProps> = ({ title, value, icon, color, subtitle }) => (
    <Card sx={{ height: '100%' }}>
        <CardContent sx={{ p: 3 }}>
            <Box sx={{ display: 'flex', alignItems: 'flex-start', justifyContent: 'space-between' }}>
                <Box>
                    <Typography variant="body2" color="text.secondary" sx={{ fontWeight: 500 }}>
                        {title}
                    </Typography>
                    <Typography variant="h4" sx={{ fontWeight: 700, mt: 0.5 }}>
                        {value}
                    </Typography>
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

const Dashboard: React.FC = () => {
    const stats = [
        {
            title: 'Total Employees',
            value: '—',
            icon: <PeopleRounded />,
            color: '#6366f1',
            subtitle: 'Active collaborators',
        },
        {
            title: 'Vacations This Month',
            value: '—',
            icon: <BeachAccessRounded />,
            color: '#f59e0b',
            subtitle: 'Approved requests',
        },
        {
            title: 'Pending Requests',
            value: '—',
            icon: <PendingActionsRounded />,
            color: '#ef4444',
            subtitle: 'Awaiting approval',
        },
        {
            title: 'Approved This Year',
            value: '—',
            icon: <CheckCircleRounded />,
            color: '#10b981',
            subtitle: 'Total approvals',
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

            <Grid container spacing={3}>
                {stats.map((stat) => (
                    <Grid size={{ xs: 12, sm: 6, lg: 3 }} key={stat.title}>
                        <StatCard {...stat} />
                    </Grid>
                ))}
            </Grid>

            <Grid container spacing={3} sx={{ mt: 1 }}>
                <Grid size={{ xs: 12, md: 8 }}>
                    <Card sx={{ height: 320 }}>
                        <CardContent
                            sx={{
                                p: 3,
                                height: '100%',
                                display: 'flex',
                                flexDirection: 'column',
                                justifyContent: 'center',
                                alignItems: 'center',
                            }}
                        >
                            <BeachAccessRounded sx={{ fontSize: 48, color: 'text.disabled', mb: 1 }} />
                            <Typography color="text.secondary" sx={{ fontWeight: 500 }}>
                                Vacation Calendar
                            </Typography>
                            <Typography variant="caption" color="text.disabled">
                                Coming soon
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid size={{ xs: 12, md: 4 }}>
                    <Card sx={{ height: 320 }}>
                        <CardContent
                            sx={{
                                p: 3,
                                height: '100%',
                                display: 'flex',
                                flexDirection: 'column',
                                justifyContent: 'center',
                                alignItems: 'center',
                            }}
                        >
                            <PendingActionsRounded sx={{ fontSize: 48, color: 'text.disabled', mb: 1 }} />
                            <Typography color="text.secondary" sx={{ fontWeight: 500 }}>
                                Recent Requests
                            </Typography>
                            <Typography variant="caption" color="text.disabled">
                                Coming soon
                            </Typography>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>
        </Box>
    );
};

export default Dashboard;
