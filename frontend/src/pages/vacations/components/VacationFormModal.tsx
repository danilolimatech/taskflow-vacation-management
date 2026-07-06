import React, { useEffect, useState } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    Box,
    Alert,
    CircularProgress,
} from '@mui/material';
import type { Vacation } from '../hooks/useVacations';
import api from '../../../api/axios';
import { getErrorMessage } from '../../../api/errorHandler';

interface Props {
    open: boolean;
    vacation: Vacation | null;
    onClose: () => void;
    onSuccess: () => void;
}

const emptyForm = { startDate: '', endDate: '' };

const VacationFormModal: React.FC<Props> = ({ open, vacation, onClose, onSuccess }) => {
    const isEdit = !!vacation;
    const [form, setForm] = useState(emptyForm);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (open) {
            setError(null);
            setForm(
                isEdit && vacation
                    ? { startDate: vacation.startDate, endDate: vacation.endDate }
                    : emptyForm
            );
        }
    }, [open, vacation, isEdit]);

    const handleSubmit = async () => {
        if (!form.startDate || !form.endDate) {
            setError('Both start and end dates are required.');
            return;
        }
        if (form.endDate < form.startDate) {
            setError('End date must be after start date.');
            return;
        }
        setLoading(true);
        setError(null);
        try {
            if (isEdit && vacation) {
                await api.put(`/vacations/${vacation.id}`, form);
            } else {
                await api.post('/vacations', form);
            }
            onSuccess();
        } catch (err) {
            setError(getErrorMessage(err, 'An error occurred. Please try again.'));
        } finally {
            setLoading(false);
        }
    };

    return (
        <Dialog open={open} onClose={onClose} maxWidth="xs" fullWidth>
            <DialogTitle>{isEdit ? 'Edit Vacation Request' : 'New Vacation Request'}</DialogTitle>
            <DialogContent>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 1 }}>
                    {error && <Alert severity="error">{error}</Alert>}
                    <TextField
                        label="Start Date"
                        type="date"
                        value={form.startDate}
                        onChange={(e) => setForm((p) => ({ ...p, startDate: e.target.value }))}
                        fullWidth
                        required
                        slotProps={{ inputLabel: { shrink: true } }}
                    />
                    <TextField
                        label="End Date"
                        type="date"
                        value={form.endDate}
                        onChange={(e) => setForm((p) => ({ ...p, endDate: e.target.value }))}
                        fullWidth
                        required
                        slotProps={{ inputLabel: { shrink: true } }}
                    />
                </Box>
            </DialogContent>
            <DialogActions sx={{ px: 3, pb: 2 }}>
                <Button onClick={onClose} disabled={loading}>
                    Cancel
                </Button>
                <Button
                    variant="contained"
                    onClick={handleSubmit}
                    disabled={loading}
                    sx={{
                        background: 'linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%)',
                        '&:hover': { background: 'linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%)' },
                    }}
                >
                    {loading ? <CircularProgress size={20} color="inherit" /> : isEdit ? 'Save' : 'Submit'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default VacationFormModal;
