import React from 'react';
import {
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TablePagination,
    IconButton,
    Tooltip,
    Box,
    Typography,
    Skeleton,
} from '@mui/material';
import {
    EditRounded,
    DeleteRounded,
    CheckCircleRounded,
    CancelRounded,
} from '@mui/icons-material';
import type { Vacation } from '../hooks/useVacations';
import VacationStatusChip from './VacationStatusChip';
import { useAuth } from '../../../contexts/AuthContext';

interface Props {
    vacations: Vacation[];
    totalElements: number;
    page: number;
    pageSize: number;
    loading: boolean;
    onPageChange: (newPage: number) => void;
    onEdit: (vacation: Vacation) => void;
    onDelete: (vacation: Vacation) => void;
    onApprove: (vacation: Vacation) => void;
    onReject: (vacation: Vacation) => void;
}

const formatDate = (date: string) =>
    new Date(date + 'T00:00:00').toLocaleDateString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
    });

const formatDateTime = (date: string) =>
    new Date(date).toLocaleString('en-GB', {
        day: '2-digit',
        month: 'short',
        year: 'numeric',
        hour: '2-digit',
        minute: '2-digit',
    });

const VacationTable: React.FC<Props> = ({
    vacations,
    totalElements,
    page,
    pageSize,
    loading,
    onPageChange,
    onEdit,
    onDelete,
    onApprove,
    onReject,
}) => {
    const { user } = useAuth();
    const isAdmin = user?.role === 'ADMIN';
    const isAdminOrManager = isAdmin || user?.role === 'MANAGER';
    const isCollaborator = user?.role === 'COLLABORATOR';
    const columnCount = isAdminOrManager ? 7 : 6;

    return (
        <Box>
            <TableContainer>
                <Table size="small">
                    <TableHead>
                        <TableRow>
                            {isAdminOrManager && (
                                <TableCell sx={{ fontWeight: 600 }}>Employee</TableCell>
                            )}
                            <TableCell sx={{ fontWeight: 600 }}>Start Date</TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>End Date</TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>Days</TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>Status</TableCell>
                            <TableCell sx={{ fontWeight: 600 }}>Requested on</TableCell>
                            <TableCell sx={{ fontWeight: 600 }} align="right">
                                Actions
                            </TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {loading
                            ? Array.from({ length: 5 }).map((_, i) => (
                                <TableRow key={i}>
                                    {Array.from({ length: columnCount }).map((__, j) => (
                                        <TableCell key={j}>
                                            <Skeleton variant="text" />
                                        </TableCell>
                                    ))}
                                </TableRow>
                            ))
                            : vacations.length === 0
                                ? (
                                    <TableRow>
                                        <TableCell colSpan={columnCount} align="center" sx={{ py: 6 }}>
                                            <Typography color="text.secondary">No vacation requests found.</Typography>
                                        </TableCell>
                                    </TableRow>
                                )
                                : vacations.map((v) => {
                                    const days =
                                        Math.round(
                                            (new Date(v.endDate).getTime() - new Date(v.startDate).getTime()) /
                                            (1000 * 60 * 60 * 24)
                                        ) + 1;
                                    const isPending = v.status === 'PENDING';
                                    const canEditDelete = isCollaborator && isPending;
                                    const canAdminDelete = isAdmin && isPending;
                                    const canApproveReject = isAdminOrManager && isPending;

                                    return (
                                        <TableRow key={v.id} hover>
                                            {isAdminOrManager && <TableCell>{v.employeeName}</TableCell>}
                                            <TableCell>{formatDate(v.startDate)}</TableCell>
                                            <TableCell>{formatDate(v.endDate)}</TableCell>
                                            <TableCell>{days}d</TableCell>
                                            <TableCell>
                                                <VacationStatusChip status={v.status} />
                                            </TableCell>
                                            <TableCell>{formatDateTime(v.createdAt)}</TableCell>
                                            <TableCell align="right">
                                                <Box sx={{ display: 'flex', justifyContent: 'flex-end', gap: 0.5 }}>
                                                    {isAdminOrManager && (
                                                        <>
                                                            <Tooltip title={canApproveReject ? 'Approve' : 'Already processed'}>
                                                                <span>
                                                                    <IconButton
                                                                        size="small"
                                                                        color="success"
                                                                        onClick={() => onApprove(v)}
                                                                        disabled={!canApproveReject}
                                                                    >
                                                                        <CheckCircleRounded fontSize="small" />
                                                                    </IconButton>
                                                                </span>
                                                            </Tooltip>
                                                            <Tooltip title={canApproveReject ? 'Reject' : 'Already processed'}>
                                                                <span>
                                                                    <IconButton
                                                                        size="small"
                                                                        color="error"
                                                                        onClick={() => onReject(v)}
                                                                        disabled={!canApproveReject}
                                                                    >
                                                                        <CancelRounded fontSize="small" />
                                                                    </IconButton>
                                                                </span>
                                                            </Tooltip>
                                                            {isAdmin && (
                                                                <Tooltip title={canAdminDelete ? 'Cancel request' : 'Only pending requests can be cancelled'}>
                                                                    <span>
                                                                        <IconButton
                                                                            size="small"
                                                                            color="error"
                                                                            onClick={() => onDelete(v)}
                                                                            disabled={!canAdminDelete}
                                                                        >
                                                                            <DeleteRounded fontSize="small" />
                                                                        </IconButton>
                                                                    </span>
                                                                </Tooltip>
                                                            )}
                                                        </>
                                                    )}
                                                    {isCollaborator && (
                                                        <>
                                                            <Tooltip title={canEditDelete ? 'Edit' : 'Only pending requests can be edited'}>
                                                                <span>
                                                                    <IconButton
                                                                        size="small"
                                                                        onClick={() => onEdit(v)}
                                                                        disabled={!canEditDelete}
                                                                    >
                                                                        <EditRounded fontSize="small" />
                                                                    </IconButton>
                                                                </span>
                                                            </Tooltip>
                                                            <Tooltip title={canEditDelete ? 'Delete' : 'Only pending requests can be deleted'}>
                                                                <span>
                                                                    <IconButton
                                                                        size="small"
                                                                        color="error"
                                                                        onClick={() => onDelete(v)}
                                                                        disabled={!canEditDelete}
                                                                    >
                                                                        <DeleteRounded fontSize="small" />
                                                                    </IconButton>
                                                                </span>
                                                            </Tooltip>
                                                        </>
                                                    )}
                                                </Box>
                                            </TableCell>
                                        </TableRow>
                                    );
                                })}
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

export default VacationTable;
