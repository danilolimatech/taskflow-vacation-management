import React, { useState } from 'react';
import {
    Box,
    Typography,
    Card,
    Button,
    TextField,
    InputAdornment,
    Alert,
    MenuItem,
} from '@mui/material';
import { AddRounded, SearchRounded } from '@mui/icons-material';
import { useAuth } from '../../contexts/AuthContext';
import { useEmployees } from './hooks/useEmployees';
import type { Employee } from './hooks/useEmployees';
import { useManagers } from './hooks/useManagers';
import EmployeeTable from './components/EmployeeTable';
import EmployeeFormModal from './components/EmployeeFormModal';
import EmployeeDeleteModal from './components/EmployeeDeleteModal';
import api from '../../api/axios';
import { getErrorMessage } from '../../api/errorHandler';

const Employees: React.FC = () => {
    const { user } = useAuth();
    const isAdmin = user?.role === 'ADMIN';
    const { managers, refetch: refetchManagers } = useManagers();

    const {
        employees,
        totalElements,
        page,
        pageSize,
        setPage,
        filters,
        applyFilters,
        sortField,
        sortOrder,
        handleSort,
        loading,
        error,
        refetch,
    } = useEmployees();

    const [fullNameInput, setFullNameInput] = useState('');
    const [emailInput, setEmailInput] = useState('');
    const [managerIdInput, setManagerIdInput] = useState('');

    const [formOpen, setFormOpen] = useState(false);
    const [deleteOpen, setDeleteOpen] = useState(false);
    const [deleteLoading, setDeleteLoading] = useState(false);
    const [deleteError, setDeleteError] = useState<string | null>(null);
    const [selectedEmployee, setSelectedEmployee] = useState<Employee | null>(null);

    const handleSearch = () => {
        applyFilters({ fullName: fullNameInput, email: emailInput, managerId: managerIdInput });
    };

    const handleKeyDown = (e: React.KeyboardEvent) => {
        if (e.key === 'Enter') handleSearch();
    };

    const handleClear = () => {
        setFullNameInput('');
        setEmailInput('');
        setManagerIdInput('');
        applyFilters({ fullName: '', email: '', managerId: '' });
    };

    const handleEdit = (employee: Employee) => {
        setSelectedEmployee(employee);
        setFormOpen(true);
    };

    const handleCreate = () => {
        setSelectedEmployee(null);
        setFormOpen(true);
    };

    const handleDelete = (employee: Employee) => {
        setDeleteError(null);
        setSelectedEmployee(employee);
        setDeleteOpen(true);
    };

    const handleDeleteConfirm = async (employee: Employee) => {
        setDeleteLoading(true);
        setDeleteError(null);
        try {
            await api.delete(`/employees/${employee.id}`);
            setDeleteOpen(false);
            refetch();
            refetchManagers();
        } catch (err) {
            setDeleteError(getErrorMessage(err, 'Failed to delete employee. Please try again.'));
        } finally {
            setDeleteLoading(false);
        }
    };

    const hasActiveFilters = filters.fullName || filters.email || filters.managerId;

    return (
        <Box>
            <Box sx={{ mb: 3, display: 'flex', alignItems: 'center', gap: 2, flexWrap: 'wrap' }}>
                <Box sx={{ minWidth: 180 }}>
                    <Typography variant="h5" sx={{ fontWeight: 700 }}>
                        Employees
                    </Typography>
                    <Typography variant="body2" color="text.secondary">
                        {totalElements > 0 ? `${totalElements} employees found` : 'Manage your team members'}
                    </Typography>
                </Box>

                <Box sx={{ display: 'flex', gap: 1.5, alignItems: 'center', flex: 1, flexWrap: 'wrap' }}>
                    <TextField
                        size="small"
                        placeholder="Search by name..."
                        value={fullNameInput}
                        onChange={(e) => setFullNameInput(e.target.value)}
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
                        sx={{ minWidth: 200 }}
                    />
                    <TextField
                        size="small"
                        placeholder="Search by email..."
                        value={emailInput}
                        onChange={(e) => setEmailInput(e.target.value)}
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
                        sx={{ minWidth: 200 }}
                    />
                    <TextField
                        select
                        size="small"
                        label="Manager"
                        value={managerIdInput}
                        onChange={(e) => setManagerIdInput(e.target.value)}
                        sx={{ minWidth: 180 }}
                    >
                        <MenuItem value="">All managers</MenuItem>
                        {managers.map((m) => (
                            <MenuItem key={m.id} value={m.id}>
                                {m.fullName}
                            </MenuItem>
                        ))}
                    </TextField>
                    <Button variant="outlined" onClick={handleSearch} size="small">
                        Search
                    </Button>
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
                    disabled={!isAdmin}
                    sx={{
                        whiteSpace: 'nowrap',
                        background: isAdmin
                            ? 'linear-gradient(135deg, #6366f1 0%, #8b5cf6 100%)'
                            : undefined,
                        '&:hover': {
                            background: isAdmin
                                ? 'linear-gradient(135deg, #4f46e5 0%, #7c3aed 100%)'
                                : undefined,
                        },
                    }}
                >
                    Add Employee
                </Button>
            </Box>

            {error && <Alert severity="error" sx={{ mb: 2 }}>{error}</Alert>}

            <Card>
                <EmployeeTable
                    employees={employees}
                    totalElements={totalElements}
                    page={page}
                    pageSize={pageSize}
                    loading={loading}
                    sortField={sortField}
                    sortOrder={sortOrder}
                    onSort={handleSort}
                    onPageChange={setPage}
                    onEdit={handleEdit}
                    onDelete={handleDelete}
                />
            </Card>

            <EmployeeFormModal
                open={formOpen}
                employee={selectedEmployee}
                onClose={() => setFormOpen(false)}
                onSuccess={() => {
                    setFormOpen(false);
                    refetch();
                    refetchManagers();
                }}
            />

            <EmployeeDeleteModal
                open={deleteOpen}
                employee={selectedEmployee}
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

export default Employees;
