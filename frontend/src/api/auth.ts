import api from './axios';
import type { AuthUser } from '../contexts/AuthContext';

export interface LoginPayload {
    username: string;
    password: string;
}

export const login = async (payload: LoginPayload): Promise<AuthUser> => {
    const { data } = await api.post<AuthUser>('/auth/login', payload);
    return data;
};
