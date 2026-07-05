import { useState, useEffect, useCallback } from 'react';
import api from '../../../api/axios';

export interface Employee {
    id: string;
    fullName: string;
    email: string;
    managerId: string | null;
    managerName: string | null;
    username: string;
    role: 'ADMIN' | 'MANAGER' | 'COLLABORATOR';
    createdAt: string;
    updatedAt: string;
}

interface EmployeePage {
    content: Employee[];
    page: {
        size: number;
        number: number;
        totalElements: number;
        totalPages: number;
    };
}

export interface Filters {
    fullName: string;
    email: string;
    managerId: string;
}

export type SortField = 'fullName' | 'email' | 'role';
export type SortOrder = 'asc' | 'desc';

export const useEmployees = () => {
    const [employees, setEmployees] = useState<Employee[]>([]);
    const [totalElements, setTotalElements] = useState(0);
    const [totalPages, setTotalPages] = useState(0);
    const [page, setPage] = useState(0);
    const [pageSize] = useState(10);
    const [filters, setFilters] = useState<Filters>({ fullName: '', email: '', managerId: '' });
    const [sortField, setSortField] = useState<SortField>('fullName');
    const [sortOrder, setSortOrder] = useState<SortOrder>('asc');
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchEmployees = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const params: Record<string, string | number> = {
                page,
                size: pageSize,
                sort: `${sortField},${sortOrder}`,
            };
            if (filters.fullName) params.fullName = filters.fullName;
            if (filters.email) params.email = filters.email;
            if (filters.managerId) params.managerId = filters.managerId;

            const { data } = await api.get<EmployeePage>('/employees', { params });
            setEmployees(data.content);
            setTotalElements(data.page.totalElements);
            setTotalPages(data.page.totalPages);
        } catch {
            setError('Failed to load employees.');
        } finally {
            setLoading(false);
        }
    }, [page, pageSize, filters, sortField, sortOrder]);

    useEffect(() => {
        fetchEmployees();
    }, [fetchEmployees]);

    const applyFilters = (newFilters: Filters) => {
        setFilters(newFilters);
        setPage(0);
    };

    const handleSort = (field: SortField) => {
        if (field === sortField) {
            setSortOrder((prev) => (prev === 'asc' ? 'desc' : 'asc'));
        } else {
            setSortField(field);
            setSortOrder('asc');
        }
        setPage(0);
    };

    return {
        employees,
        totalElements,
        totalPages,
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
        refetch: fetchEmployees,
    };
};
