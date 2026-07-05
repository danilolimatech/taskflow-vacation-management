interface ApiError {
    response?: {
        data?: {
            message?: string;
        };
    };
}

export const getErrorMessage = (err: unknown, fallback = 'An unexpected error occurred.'): string => {
    const apiErr = err as ApiError;
    return apiErr?.response?.data?.message ?? fallback;
};
