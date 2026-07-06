import React from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    Typography,
    Box,
    Alert,
} from '@mui/material';
import { WarningAmberRounded } from '@mui/icons-material';
import type { Vacation } from '../hooks/useVacations';

interface Props {
    open: boolean;
    vacation: Vacation | null;
    onClose: () => void;
    onConfirm: (vacation: Vacation) => void;
    loading?: boolean;
    error?: string | null;
}

const VacationDeleteModal: React.FC<Props> = ({
    open,
    vacation,
    onClose,
    onConfirm,
    loading = false,
    error = null,
}) => {
    if (!vacation) return null;

    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    <WarningAmberRounded color="error" />
                    Cancel Vacation Request
                </Box>
            </DialogTitle>
            <DialogContent>
                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                <Typography>
                    Are you sure you want to cancel the vacation request from{' '}
                    <strong>{vacation.startDate}</strong> to <strong>{vacation.endDate}</strong>?
                    This action cannot be undone.
                </Typography>
            </DialogContent>
            <DialogActions sx={{ px: 3, pb: 2 }}>
                <Button onClick={onClose} disabled={loading}>
                    Back
                </Button>
                <Button
                    variant="contained"
                    color="error"
                    onClick={() => onConfirm(vacation)}
                    disabled={loading}
                >
                    {loading ? 'Cancelling...' : 'Cancel Request'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default VacationDeleteModal;
