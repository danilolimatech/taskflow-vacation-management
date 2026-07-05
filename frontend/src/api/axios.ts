import axios from 'axios';

const api = axios.create({
    baseURL: '/v1',
    headers: {
        'Content-Type': 'application/json',
    },
});

// Injeta o token em todas as requisições automaticamente
api.interceptors.request.use((config) => {
    const stored = localStorage.getItem('auth');
    if (stored) {
        const { token } = JSON.parse(stored);
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
    }
    return config;
});

// Redireciona para login se token expirar
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if (error.response?.status === 401) {
            localStorage.removeItem('auth');
            window.location.href = '/login';
        }
        return Promise.reject(error);
    }
);

export default api;
