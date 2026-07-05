import React from 'react';
import { Box, Typography, Card, CardContent, Button } from '@mui/material';
import { BeachAccessRounded, AddRounded } from '@mui/icons-material';

const Vacations: React.FC = () => {
    return (
        <Box>
            <Box sx={{ mb: 3, display: 'flex', alignItems: 'center', justifyContent: 'space-between' }}>
                <Box>
                    <Typography variant="h5" sx={{ fontWeight: 700 }}>
                        Vacation Requests
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        View and manage vacation requests
                    </Typography>
                </Box>
                <Button
                    variant="contained"
                    startIcon={<AddRounded />}
                    sx={{
                        background: 'linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%)',
                        '&:hover': {
                            background: 'linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%)',
                        },
                    }}
                >
                    New Request
                </Button>
            </Box>

            <Card>
                <CardContent
                    sx={{
                        display: 'flex',
                        flexDirection: 'column',
                        alignItems: 'center',
                        justifyContent: 'center',
                        py: 10,
                    }}
                >
                    <BeachAccessRounded sx={{ fontSize: 64, color: 'text.disabled', mb: 2 }} />
                    <Typography variant="h6" color="text.secondary" sx={{ fontWeight: 500 }}>
                        Vacation requests
                    </Typography>
                    <Typography variant="body2" color="text.disabled">
                        Integration coming soon
                    </Typography>
                </CardContent>
            </Card>
        </Box>
    );
};

export default Vacations;
