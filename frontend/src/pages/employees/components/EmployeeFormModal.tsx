import React, { useEffect, useState } from 'react';
import {
    Dialog,
    DialogTitle,
    DialogContent,
    DialogActions,
    Button,
    TextField,
    MenuItem,
    Box,
    Alert,
    CircularProgress,
} from '@mui/material';
import type { Employee } from '../hooks/useEmployees';
import { useManagers } from '../hooks/useManagers';
import api from '../../../api/axios';
import { getErrorMessage } from '../../../api/errorHandler';

interface Props {
    open: boolean;
    employee: Employee | null;
    onClose: () => void;
    onSuccess: () => void;
}

const ROLES = ['ADMIN', 'MANAGER', 'COLLABORATOR'] as const;

const emptyForm = {
    fullName: '',
    email: '',
    username: '',
    password: '',
    role: 'COLLABORATOR' as Employee['role'],
    managerId: '',
};

const EmployeeFormModal: React.FC<Props> = ({ open, employee, onClose, onSuccess }) => {
    const isEdit = !!employee;

    const [form, setForm] = useState(emptyForm);
    const { managers } = useManagers();
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    useEffect(() => {
        if (open) {
            setError(null);
            if (isEdit && employee) {
                setForm({
                    fullName: employee.fullName,
                    email: employee.email,
                    username: employee.username,
                    password: '',
                    role: employee.role,
                    managerId: employee.managerId ?? '',
                });
            } else {
                setForm(emptyForm);
            }
        }
    }, [open, employee, isEdit]);


    const handleChange = (field: keyof typeof form, value: string) => {
        setForm((prev) => {
            const updated = { ...prev, [field]: value };
            if (field === 'role' && value !== 'COLLABORATOR') {
                updated.managerId = '';
            }
            return updated;
        });
    };

    const handleSubmit = async () => {
        setLoading(true);
        setError(null);
        try {
            if (isEdit && employee) {
                const payload: Record<string, string> = {
                    fullName: form.fullName,
                    email: form.email,
                    username: form.username,
                };
                if (form.role === 'COLLABORATOR' && form.managerId) {
                    payload.managerId = form.managerId;
                }
                await api.put(`/employees/${employee.id}`, payload);
            } else {
                const payload: Record<string, string> = {
                    fullName: form.fullName,
                    email: form.email,
                    username: form.username,
                    password: form.password,
                    role: form.role,
                };
                if (form.role === 'COLLABORATOR' && form.managerId) {
                    payload.managerId = form.managerId;
                }
                await api.post('/employees', payload);
            }
            onSuccess();
        } catch (err: unknown) {
            setError(getErrorMessage(err, 'An error occurred. Please try again.'));
        } finally {
            setLoading(false);
        }
    };

    const isCollaborator = form.role === 'COLLABORATOR';

    return (
        <Dialog open={open} onClose={onClose} maxWidth="sm" fullWidth>
            <DialogTitle>{isEdit ? 'Edit Employee' : 'New Employee'}</DialogTitle>
            <DialogContent>
                <Box sx={{ display: 'flex', flexDirection: 'column', gap: 2, pt: 1 }}>
                    {error && <Alert severity="error">{error}</Alert>}

                    <TextField
                        label="Full Name"
                        value={form.fullName}
                        onChange={(e) => handleChange('fullName', e.target.value)}
                        fullWidth
                        required
                    />

                    <TextField
                        label="Email"
                        type="email"
                        value={form.email}
                        onChange={(e) => handleChange('email', e.target.value)}
                        fullWidth
                        required
                    />

                    <TextField
                        label="Username"
                        value={form.username}
                        onChange={(e) => handleChange('username', e.target.value)}
                        fullWidth
                        required
                    />

                    {!isEdit && (
                        <TextField
                            label="Password"
                            type="password"
                            value={form.password}
                            onChange={(e) => handleChange('password', e.target.value)}
                            fullWidth
                            required
                        />
                    )}

                    {!isEdit && (
                        <TextField
                            label="Role"
                            select
                            value={form.role}
                            onChange={(e) => handleChange('role', e.target.value)}
                            fullWidth
                            required
                        >
                            {ROLES.map((r) => (
                                <MenuItem key={r} value={r}>
                                    {r.charAt(0) + r.slice(1).toLowerCase()}
                                </MenuItem>
                            ))}
                        </TextField>
                    )}

                    {isCollaborator && (
                        <TextField
                            label="Manager"
                            select
                            value={form.managerId}
                            onChange={(e) => handleChange('managerId', e.target.value)}
                            fullWidth
                        >
                            <MenuItem value="">
                                <em>None</em>
                            </MenuItem>
                            {managers.map((m) => (
                                <MenuItem key={m.id} value={m.id}>
                                    {m.fullName}
                                </MenuItem>
                            ))}
                        </TextField>
                    )}
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
                    {loading ? <CircularProgress size={20} color="inherit" /> : isEdit ? 'Save' : 'Create'}
                </Button>
            </DialogActions>
        </Dialog>
    );
};

export default EmployeeFormModal;
