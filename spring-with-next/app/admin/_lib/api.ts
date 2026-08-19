export type PageResponse<T> = {
    content: T[];
    totalElements: number;
    totalPages: number;
    number: number;
    size: number;
};

export type Category = { id: number; name: string; description: string | null; icon: string | null };
export type Product = {
    code: string;
    name: string;
    price: number;
    qty: number;
    description: string | null;
    isAvailable: boolean;
    categoryName: string
};
export type UploadedFile = { name: string; extension: string; size: number; mediaType: string; uri: string };
export type UserProfile = {
    userId: string; email: string; firstName: string; lastName: string; gender: string | null;
    biography: string | null; profilePicture: string | null; jobTitle: string | null; salary: number | null;
    phoneNumber: string | null; githubLink: string | null; facebookLink: string | null;
};

const baseUrl = process.env.NEXT_PUBLIC_BFF_API_URL ?? "/ecommerce-api";

async function request<T>(path: string, init?: RequestInit): Promise<T> {
    const response = await fetch(`${baseUrl}${path}`, {
        ...init,
        headers: {...(init?.body instanceof FormData ? {} : {"Content-Type": "application/json"}), ...init?.headers},
        credentials: "include",
    });

    if (!response.ok) {
        const body = await response.json().catch(() => null);
        const message = body?.message ?? body?.detail ?? `Request failed (${response.status})`;
        throw new Error(message);
    }
    return response.status === 204 ? (undefined as T) : response.json() as Promise<T>;
}

export const api = {
    getCategories: () => request<PageResponse<Category>>("/api/v1/categories?pageNumber=0&pageSize=100"),
    createCategory: (data: {
        name: string;
        description?: string;
        icon?: string
    }) => request<Category>("/api/v1/categories", {method: "POST", body: JSON.stringify(data)}),
    updateCategory: (id: number, data: {
        name: string;
        description?: string;
        icon?: string
    }) => request<Category>(`/api/v1/categories/${id}`, {method: "PUT", body: JSON.stringify(data)}),
    deleteCategory: (id: number) => request<void>(`/api/v1/categories/${id}`, {method: "DELETE"}),
    getProducts: () => request<PageResponse<Product>>("/api/v1/products?pageNumber=0&pageSize=100"),
    createProduct: (data: {
        name: string;
        price: number;
        qty: number;
        description?: string;
        categoryId: number
    }) => request<Product>("/api/v1/products", {method: "POST", body: JSON.stringify(data)}),
    updateProduct: (code: string, data: {
        name: string;
        price: number;
        qty: number;
        description?: string
    }) => request<Product>(`/api/v1/products/${code}`, {method: "PUT", body: JSON.stringify(data)}),
    deleteProduct: (code: string) => request<void>(`/api/v1/products/${code}`, {method: "DELETE"}),
    getFiles: () => request<PageResponse<UploadedFile>>("/api/v1/files?pageNumber=0&pageSize=100"),
    uploadFiles: (files: File[]) => {
        const body = new FormData();
        files.forEach((file) => body.append("files", file));
        return request<UploadedFile[]>("/api/v1/files/multiple", {method: "POST", body});
    },
    getProfile: () => request<UserProfile>("/api/v1/user-profiles/me"),
    patchProfile: (data: Partial<UserProfile>) => request<UserProfile>("/api/v1/user-profiles/me", {
        method: "PATCH",
        body: JSON.stringify(data)
    }),
    register: (data: Record<string, string>) => request("/api/v1/auth/register", {
        method: "POST",
        body: JSON.stringify(data)
    }),
    getOrders: () => request<PageResponse<Order>>("/api/v1/orders?pageNumber=0&pageSize=100"),
    createOrder: (data: OrderPayload) => request<Order>("/api/v1/orders", {method: "POST", body: JSON.stringify(data)}),
    updateOrder: (orderId: string, data: OrderPayload) => request<Order>(`/api/v1/orders/${orderId}`, {
        method: "PUT",
        body: JSON.stringify(data)
    }),
    deleteOrder: (orderId: string) => request<void>(`/api/v1/orders/${orderId}`, {method: "DELETE"}),
};

export type OrderPayload = { remark?: string; orderLines: { productCode: string; qty: number; discount: number }[] };
export type Order = OrderPayload & { orderId: string; orderedAt: string; orderedBy: string; isDeleted: boolean };
