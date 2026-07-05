import React from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TablePagination,
    TableSortLabel,
    Chip,
    IconButton,
    Tooltip,
    Box,
    Typography,
    Skeleton,
} from '@mui/material';
import { EditRounded, DeleteRounded } from '@mui/icons-material';
import type { Employee, SortField, SortOrder } from '../hooks/useEmployees';
import { useAuth } from '../../../contexts/AuthContext';

interface Props {
    employees: Employee[];
    totalElements: number;
    page: number;
    pageSize: number;
    loading: boolean;
    sortField: SortField;
    sortOrder: SortOrder;
    onSort: (field: SortField) => void;
    onPageChange: (newPage: number) => void;
    onEdit: (employee: Employee) => void;
    onDelete: (employee: Employee) => void;
}

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


const EmployeeTable: React.FC<Props> = ({
    employees,
    totalElements,
    page,
    pageSize,
    loading,
    sortField,
    sortOrder,
    onSort,
    onPageChange,
    onEdit,
    onDelete,
}) => {
    const { user } = useAuth();
    const isAdmin = user?.role === 'ADMIN';

    return (
        <Box>
            <TableContainer>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            <TableCell sx={{ fontWeight: 600 }}>
                                <TableSortLabel
                                    active={sortField === 'fullName'}
                                    direction={sortField === 'fullName' ? sortOrder : 'asc'}
                                    onClick={() => onSort('fullName')}
                                >
                                    Full Name
                                </TableSortLabel>
                            </TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>
                                <TableSortLabel
                                    active={sortField === 'email'}
                                    direction={sortField === 'email' ? sortOrder : 'asc'}
                                    onClick={() => onSort('email')}
                                >
                                    Email
                                </TableSortLabel>
                            </TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>Username</TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>
                                <TableSortLabel
                                    active={sortField === 'role'}
                                    direction={sortField === 'role' ? sortOrder : 'asc'}
                                    onClick={() => onSort('role')}
                                >
                                    Role
                                </TableSortLabel>
                            </TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>Manager</TableCell>
                            <TableCell sx={{ fontWeight: 600 }} align="right">
                                Actions
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {loading
                            ? Array.from({ length: pageSize }).map((_, i) => (
                                <TableRow key={i}>
                                    {Array.from({ length: 6 }).map((__, j) => (
                                        <TableCell key={j}>
                                            <Skeleton variant="text" />
                                        </TableCell>
                                    ))}
                                </TableRow>
                            ))
                            : employees.length === 0
                                ? (
                                    <TableRow>
                                        <TableCell colSpan={6} align="center" sx={{ py: 6 }}>
                                            <Typography color="text.secondary">No employees found.</Typography>
                                        </TableCell>
                                    </TableRow>
                                )
                                : employees.map((emp) => (
                                    <TableRow key={emp.id} hover>
                                        <TableCell>{emp.fullName}</TableCell>
                                        <TableCell>{emp.email}</TableCell>
                                        <TableCell>
                                            <Typography variant="body2" sx={{ fontFamily: 'monospace' }}>
                                                {emp.username}
                                            </Typography>
                                        </TableCell>
                                        <TableCell>
                                            <Chip
                                                label={roleLabels[emp.role] ?? emp.role}
                                                color={roleColors[emp.role] ?? 'default'}
                                                size="small"
                                            />
                                        </TableCell>
                                        <TableCell>
                                            {emp.managerName ?? (
                                                <Typography variant="body2" color="text.disabled">
                                                    —
                                                </Typography>
                                            )}
                                        </TableCell>
                                        <TableCell align="right">
                                            <Box sx={{ display: 'flex', justifyContent: 'flex-end', gap: 0.5 }}>
                                                <Tooltip title={isAdmin ? 'Edit' : 'Only admins can edit'}>
                                                    <span>
                                                        <IconButton
                                                            size="small"
                                                            onClick={() => onEdit(emp)}
                                                            disabled={!isAdmin}
                                                        >
                                                            <EditRounded fontSize="small" />
                                                        </IconButton>
                                                    </span>
                                                </Tooltip>
                                                <Tooltip title={isAdmin ? 'Delete' : 'Only admins can delete'}>
                                                    <span>
                                                        <IconButton
                                                            size="small"
                                                            color="error"
                                                            onClick={() => onDelete(emp)}
                                                            disabled={!isAdmin}
                                                        >
                                                            <DeleteRounded fontSize="small" />
                                                        </IconButton>
                                                    </span>
                                                </Tooltip>
                                            </Box>
                                        </TableCell>
                                    </TableRow>
                                ))}
                    </TableBody>
                </Table>
            </TableContainer>

            <TablePagination
                component="div"
                count={totalElements}
                page={page}
                rowsPerPage={pageSize}
                rowsPerPageOptions={[pageSize]}
                onPageChange={(_, newPage) => onPageChange(newPage)}
            />
        </Box>
    );
};

export default EmployeeTable;
