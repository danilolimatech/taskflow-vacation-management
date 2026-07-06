import { useState, useEffect, useCallback } from 'react';
import api from '../../../api/axios';
import type { Vacation } from '../../vacations/hooks/useVacations';

export interface VacationSummary {
    totalCollaborators: number;
    totalPending: number;
    totalApproved: number;
    totalRejected: number;
}

interface VacationPage {
    content: Vacation[];
}

export const useDashboard = () => {
    const [summary, setSummary] = useState<VacationSummary | null>(null);
    const [recentVacations, setRecentVacations] = useState<Vacation[]>([]);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);

    const fetchDashboard = useCallback(async () => {
        setLoading(true);
        setError(null);
        try {
            const [summaryRes, recentRes] = await Promise.all([
                api.get<VacationSummary>('/vacations/summary'),
                api.get<VacationPage>('/vacations', {
                    params: { page: 0, size: 5, sort: 'createdAt,desc' },
                }),
            ]);
            setSummary(summaryRes.data);
            setRecentVacations(recentRes.data.content);
        } catch {
            setError('Failed to load dashboard data.');
        } finally {
            setLoading(false);
        }
    }, []);

    useEffect(() => {
        fetchDashboard();
    }, [fetchDashboard]);

    return { summary, recentVacations, loading, error, refetch: fetchDashboard };
};
