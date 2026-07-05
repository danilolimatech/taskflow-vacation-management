import { useState, useEffect, useCallback } from 'react';
import api from '../../../api/axios';

export interface Manager {
    id: string;
    fullName: string;
}

export const useManagers = () => {
    const [managers, setManagers] = useState<Manager[]>([]);
    const [loading, setLoading] = useState(false);

    const fetchManagers = useCallback(() => {
        setLoading(true);
        api
            .get<{ content: Manager[] }>('/employees', {
                params: { size: 100, role: 'MANAGER', sort: 'fullName,asc' },
            })
            .then(({ data }) => setManagers(data.content))
            .catch(() => setManagers([]))
            .finally(() => setLoading(false));
    }, []);

    useEffect(() => {
        fetchManagers();
    }, [fetchManagers]);

    return { managers, loading, refetch: fetchManagers };
};
