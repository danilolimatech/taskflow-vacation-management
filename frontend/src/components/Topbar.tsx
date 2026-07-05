import React from 'react';
import {
    AppBar,
    Toolbar,
    IconButton,
    Tooltip,
    Box,
    Typography,
    Button,
} from '@mui/material';
import { LightModeRounded, DarkModeRounded, LogoutRounded } from '@mui/icons-material';
import { useThemeMode } from '../contexts/ThemeContext';
import { useAuth } from '../contexts/AuthContext';
import { useNavigate, useLocation } from 'react-router-dom';

const pageTitles: Record<string, string> = {
    '/dashboard': 'Dashboard',
    '/employees': 'Employees',
    '/vacations': 'Vacations',
};

const Topbar: React.FC = () => {
    const { mode, toggleTheme } = useThemeMode();
    const { logout } = useAuth();
    const navigate = useNavigate();
    const location = useLocation();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    const title = pageTitles[location.pathname] ?? 'TaskFlow';

    return (
        <AppBar
            position="fixed"
            elevation={0}
            sx={{
                ml: '260px',
                width: 'calc(100% - 260px)',
                backgroundColor: 'background.paper',
                borderBottom: '1px solid',
                borderColor: 'divider',
                color: 'text.primary',
            }}
        >
            <Toolbar sx={{ justifyContent: 'space-between' }}>
                <Typography variant="h6" sx={{ fontWeight: 700 }}>
                    {title}
                </Typography>

                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    <Tooltip title={`Switch to ${mode === 'light' ? 'dark' : 'light'} mode`}>
                        <IconButton onClick={toggleTheme} color="inherit" size="small">
                            {mode === 'light' ? <DarkModeRounded /> : <LightModeRounded />}
                        </IconButton>
                    </Tooltip>

                    <Button
                        onClick={handleLogout}
                        startIcon={<LogoutRounded />}
                        size="small"
                        color="inherit"
                        sx={{ fontWeight: 500 }}
                    >
                        Logout
                    </Button>
                </Box>
            </Toolbar>
        </AppBar>
    );
};

export default Topbar;
