import React from 'react';
import {
    Drawer,
    List,
    ListItem,
    ListItemButton,
    ListItemIcon,
    ListItemText,
    Box,
    Typography,
    Divider,
    Avatar,
    Chip,
} from '@mui/material';
import {
    DashboardRounded,
    PeopleRounded,
    BeachAccessRounded,
} from '@mui/icons-material';
import { useNavigate, useLocation } from 'react-router-dom';
import { useAuth } from '../contexts/AuthContext';

const DRAWER_WIDTH = 260;

const roleColors: Record<string, 'error' | 'warning' | 'info'> = {
    ADMIN: 'error',
    MANAGER: 'warning',
    COLLABORATOR: 'info',
};

const roleLabels: Record<string, string> = {
    ADMIN: 'Admin',
    MANAGER: 'Manager',
    COLLABORATOR: 'Collaborator',
};

const Sidebar: React.FC = () => {
    const navigate = useNavigate();
    const location = useLocation();
    const { user } = useAuth();

    const navItems = [
        {
            label: 'Dashboard',
            icon: <DashboardRounded />,
            path: '/dashboard',
            roles: ['ADMIN', 'MANAGER'],
        },
        {
            label: 'Employees',
            icon: <PeopleRounded />,
            path: '/employees',
            roles: ['ADMIN', 'MANAGER'],
        },
        {
            label: 'Vacations',
            icon: <BeachAccessRounded />,
            path: '/vacations',
            roles: ['ADMIN', 'MANAGER', 'COLLABORATOR'],
        },
    ];

    const visibleItems = navItems.filter(
        (item) => user && item.roles.includes(user.role)
    );

    return (
        <Drawer
            variant="permanent"
            sx={{
                width: DRAWER_WIDTH,
                flexShrink: 0,
                '& .MuiDrawer-paper': {
                    width: DRAWER_WIDTH,
                    boxSizing: 'border-box',
                    display: 'flex',
                    flexDirection: 'column',
                },
            }}
        >
            {/* Logo */}
            <Box sx={{ px: 3, py: 3 }}>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1.5 }}>
                    <Box
                        sx={{
                            width: 36,
                            height: 36,
                            borderRadius: 2,
                            background: 'linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%)',
                            display: 'flex',
                            alignItems: 'center',
                            justifyContent: 'center',
                        }}
                    >
                        <BeachAccessRounded sx={{ color: 'white', fontSize: 20 }} />
                    </Box>
                    <Box>
                        <Typography variant="subtitle1" sx={{ fontWeight: 700, lineHeight: 1.2 }}>
                            TaskFlow
                        </Typography>
                        <Typography variant="caption" color="text.secondary">
                            Vacation Manager
                        </Typography>
                    </Box>
                </Box>
            </Box>

            <Divider />

            {/* Navigation */}
            <List sx={{ px: 1.5, py: 2, flex: 1 }}>
                {visibleItems.map((item) => {
                    const isActive = location.pathname === item.path;
                    return (
                        <ListItem key={item.path} disablePadding sx={{ mb: 0.5 }}>
                            <ListItemButton
                                onClick={() => navigate(item.path)}
                                selected={isActive}
                                sx={{
                                    borderRadius: 2,
                                    '&.Mui-selected': {
                                        backgroundColor: 'primary.main',
                                        color: 'white',
                                        '& .MuiListItemIcon-root': { color: 'white' },
                                        '&:hover': { backgroundColor: 'primary.dark' },
                                    },
                                }}
                            >
                                <ListItemIcon sx={{ minWidth: 40 }}>{item.icon}</ListItemIcon>
                                <ListItemText
                                    primary={item.label}
                                    slotProps={{
                                        primary: { style: { fontWeight: isActive ? 600 : 400 } },
                                    }}
                                />
                            </ListItemButton>
                        </ListItem>
                    );
                })}
            </List>

            <Divider />

            {/* User info */}
            <Box sx={{ px: 2, py: 2 }}>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1.5 }}>
                    <Avatar sx={{ bgcolor: 'primary.main', width: 36, height: 36, fontSize: 14 }}>
                        {user?.username?.charAt(0).toUpperCase()}
                    </Avatar>
                    <Box sx={{ flex: 1, minWidth: 0 }}>
                        <Typography variant="body2" sx={{ fontWeight: 600 }} noWrap>
                            {user?.username}
                        </Typography>
                        <Chip
                            label={roleLabels[user?.role ?? ''] ?? user?.role}
                            color={roleColors[user?.role ?? ''] ?? 'default'}
                            size="small"
                            sx={{ height: 18, fontSize: 10, mt: 0.3 }}
                        />
                    </Box>
                </Box>
            </Box>
        </Drawer>
    );
};

export default Sidebar;
