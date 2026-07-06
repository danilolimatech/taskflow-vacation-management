import React, { useState } from 'react';
import {
    Box,
    Typography,
    Card,
    Button,
    TextField,
    InputAdornment,
    Alert,
} from '@mui/material';
import { AddRounded, SearchRounded } from '@mui/icons-material';
import { useAuth } from '../../contexts/AuthContext';
import { useVacations } from './hooks/useVacations';
import type { Vacation } from './hooks/useVacations';
import VacationTable from './components/VacationTable';
import VacationFormModal from './components/VacationFormModal';
import VacationDeleteModal from './components/VacationDeleteModal';
import api from '../../api/axios';
import { getErrorMessage } from '../../api/errorHandler';

const Vacations: React.FC = () => {
    const { user } = useAuth();
    const isCollaborator = user?.role === 'COLLABORATOR';

    const {
        vacations,
        totalElements,
        page,
        pageSize,
        setPage,
        filters,
        applyFilters,
        loading,
        error,
        refetch,
    } = useVacations();

    const [employeeNameInput, setEmployeeNameInput] = useState('');

    const [formOpen, setFormOpen] = useState(false);
    const [deleteOpen, setDeleteOpen] = useState(false);
    const [deleteLoading, setDeleteLoading] = useState(false);
    const [deleteError, setDeleteError] = useState<string | null>(null);
    const [actionError, setActionError] = useState<string | null>(null);
    const [selectedVacation, setSelectedVacation] = useState<Vacation | null>(null);

    const handleSearch = () => applyFilters({ employeeName: employeeNameInput });

    const handleKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') handleSearch();
    };

    const handleClear = () => {
        setEmployeeNameInput('');
        applyFilters({ employeeName: '' });
    };

    const handleEdit = (vacation: Vacation) => {
        setSelectedVacation(vacation);
        setFormOpen(true);
    };

    const handleCreate = () => {
        setSelectedVacation(null);
        setFormOpen(true);
    };

    const handleDelete = (vacation: Vacation) => {
        setDeleteError(null);
        setSelectedVacation(vacation);
        setDeleteOpen(true);
    };

    const handleDeleteConfirm = async (vacation: Vacation) => {
        setDeleteLoading(true);
        setDeleteError(null);
        try {
            await api.delete(`/vacations/${vacation.id}`);
            setDeleteOpen(false);
            refetch();
        } catch (err) {
            setDeleteError(getErrorMessage(err, 'Failed to cancel vacation request.'));
        } finally {
            setDeleteLoading(false);
        }
    };

    const handleApprove = async (vacation: Vacation) => {
        setActionError(null);
        try {
            await api.patch(`/vacations/${vacation.id}/approve`);
            refetch();
        } catch (err) {
            setActionError(getErrorMessage(err, 'Failed to approve vacation request.'));
        }
    };

    const handleReject = async (vacation: Vacation) => {
        setActionError(null);
        try {
            await api.patch(`/vacations/${vacation.id}/reject`);
            refetch();
        } catch (err) {
            setActionError(getErrorMessage(err, 'Failed to reject vacation request.'));
        }
    };

    const hasActiveFilters = !!filters.employeeName;

    return (
        <Box>
            <Box sx={{ mb: 3, display: 'flex', alignItems: 'center', gap: 2, flexWrap: 'wrap' }}>
                <Box sx={{ minWidth: 180 }}>
                    <Typography variant="h5" sx={{ fontWeight: 700 }}>
                        Vacation Requests
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {totalElements > 0 ? `${totalElements} requests found` : 'Manage vacation requests'}
                    </Typography>
                </Box>

                <Box sx={{ display: 'flex', gap: 1.5, alignItems: 'center', flex: 1, flexWrap: 'wrap' }}>
                    {!isCollaborator && (
                        <TextField
                            size="small"
                            placeholder="Search by employee name..."
                            value={employeeNameInput}
                            onChange={(e) => setEmployeeNameInput(e.target.value)}
                            onKeyDown={handleKeyDown}
                            slotProps={{
                                input: {
                                    startAdornment: (
                                        <InputAdornment position="start">
                                            <SearchRounded fontSize="small" />
                                        </InputAdornment>
                                    ),
                                },
                            }}
                            sx={{ minWidth: 240 }}
                        />
                    )}
                    {!isCollaborator && (
                        <Button variant="outlined" onClick={handleSearch} size="small">
                            Search
                        </Button>
                    )}
                    {hasActiveFilters && (
                        <Button variant="text" onClick={handleClear} size="small" color="inherit">
                            Clear
                        </Button>
                    )}
                </Box>

                <Button
                    variant="contained"
                    startIcon={<AddRounded />}
                    onClick={handleCreate}
                    disabled={!isCollaborator}
                    sx={{
                        whiteSpace: 'nowrap',
                        background: isCollaborator
                            ? 'linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%)'
                            : undefined,
                        '&:hover': {
                            background: isCollaborator
                                ? 'linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%)'
                                : undefined,
                        },
                    }}
                >
                    New Request
                </Button>
            </Box>

            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}
            {actionError && (
                <Alert severity="error" sx={{ mb: 2 }} onClose={() => setActionError(null)}>
                    {actionError}
                </Alert>
            )}

            <Card>
                <VacationTable
                    vacations={vacations}
                    totalElements={totalElements}
                    page={page}
                    pageSize={pageSize}
                    loading={loading}
                    onPageChange={setPage}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                    onApprove={handleApprove}
                    onReject={handleReject}
                />
            </Card>

            <VacationFormModal
                open={formOpen}
                vacation={selectedVacation}
                onClose={() => setFormOpen(false)}
                onSuccess={() => {
                    setFormOpen(false);
                    refetch();
                }}
            />

            <VacationDeleteModal
                open={deleteOpen}
                vacation={selectedVacation}
                onClose={() => {
                    setDeleteOpen(false);
                    setDeleteError(null);
                }}
                onConfirm={handleDeleteConfirm}
                loading={deleteLoading}
                error={deleteError}
            />
        </Box>
    );
};

export default Vacations;
