import React, { createContext, useContext, useState, useCallback } from 'react';

export type Role = 'ADMIN' | 'MANAGER' | 'COLLABORATOR';

export interface AuthUser {
    token: string;
    userId: string;
    username: string;
    role: Role;
}

interface AuthContextType {
    user: AuthUser | null;
    login: (user: AuthUser) => void;
    logout: () => void;
    isAuthenticated: boolean;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
    const [user, setUser] = useState<AuthUser | null>(() => {
        const stored = localStorage.getItem('auth');
        return stored ? JSON.parse(stored) : null;
    });

    const login = useCallback((userData: AuthUser) => {
        localStorage.setItem('auth', JSON.stringify(userData));
        setUser(userData);
    }, []);

    const logout = useCallback(() => {
        localStorage.removeItem('auth');
        setUser(null);
    }, []);

    return (
        <AuthContext.Provider value={{ user, login, logout, isAuthenticated: !!user }}>
            {children}
        </AuthContext.Provider>
    );
};

export const useAuth = (): AuthContextType => {
    const ctx = useContext(AuthContext);
    if (!ctx) throw new Error('useAuth must be used within AuthProvider');
    return ctx;
};
