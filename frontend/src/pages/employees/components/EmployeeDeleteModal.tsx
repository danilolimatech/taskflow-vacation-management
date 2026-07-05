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
import type { Employee } from '../hooks/useEmployees';

interface Props {
    open: boolean;
    employee: Employee | null;
    onClose: () => void;
    onConfirm: (employee: Employee) => void;
    loading?: boolean;
    error?: string | null;
}

const EmployeeDeleteModal: React.FC<Props> = ({
    open,
    employee,
    onClose,
    onConfirm,
    loading = false,
    error = null,
}) => {
    if (!employee) return null;

    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    <WarningAmberRounded color="error" />
                    Delete Employee
                </Box>
            </DialogTitle>
            <DialogContent>
                {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
                <Typography>
                    Are you sure you want to delete{' '}
                    <strong>{employee.fullName}</strong>? This action cannot be undone.
                </Typography>
            </DialogContent>
            <DialogActions sx={{ px: 3, pb: 2 }}>
                <Button onClick={onClose} disabled={loading}>
                    Cancel
                </Button>
                <Button
                    variant="contained"
                    color="error"
                    onClick={() => onConfirm(employee)}
                    disabled={loading}
                >
                    {loading ? 'Deleting...' : 'Delete'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default EmployeeDeleteModal;
