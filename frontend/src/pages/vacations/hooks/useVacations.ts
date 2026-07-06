import { useState, useEffect, useCallback } from 'react';
import api from '../../../api/axios';

export interface Vacation {
    id: string;
    employeeId: string;
    employeeName: string;
    startDate: string;
    endDate: string;
    status: 'PENDING' | 'APPROVED' | 'REJECTED';
    createdAt: string;
    updatedAt: string;
}

interface VacationPage {
    content: Vacation[];
    page: {
        size: number;
        number: number;
        totalElements: number;
        totalPages: number;
    };
}

export interface VacationFilters {
    employeeName: string;
}

export const useVacations = () => {
    const [vacations, setVacations] = useState<Vacation[]>([]);
    const [totalElements, setTotalElements] = useState(0);
    const [page, setPage] = useState(0);
    const [pageSize] = useState(20);
    const [filters, setFilters] = useState<VacationFilters>({ employeeName: '' });
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchVacations = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const params: Record<string, string | number> = {
                page,
                size: pageSize,
                sort: 'createdAt,desc',
            };
            if (filters.employeeName) params.employeeName = filters.employeeName;

            const { data } = await api.get<VacationPage>('/vacations', { params });
            setVacations(data.content);
            setTotalElements(data.page.totalElements);
        } catch {
            setError('Failed to load vacation requests.');
        } finally {
            setLoading(false);
        }
    }, [page, pageSize, filters]);

    useEffect(() => {
        fetchVacations();
    }, [fetchVacations]);

    const applyFilters = (newFilters: VacationFilters) => {
        setFilters(newFilters);
        setPage(0);
    };

    return {
        vacations,
        totalElements,
        page,
        pageSize,
        setPage,
        filters,
        applyFilters,
        loading,
        error,
        refetch: fetchVacations,
    };
};
