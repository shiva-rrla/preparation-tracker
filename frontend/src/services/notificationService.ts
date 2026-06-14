import api from './api';
import { Notification } from '../types/notification';
import { PageResponse } from '../types/common';

const notificationService = {
  getAll: async (params?: { page?: number; size?: number }): Promise<PageResponse<Notification>> => {
    const response = await api.get<PageResponse<Notification>>('/notifications', { params });
    return response.data;
  },
  getUnread: async (): Promise<Notification[]> => {
    const response = await api.get<Notification[]>('/notifications/unread');
    return response.data;
  },
  markAsRead: async (id: number): Promise<void> => {
    await api.put(`/notifications/${id}/read`);
  },
  markAllAsRead: async (): Promise<void> => {
    await api.put('/notifications/read-all');
  },
  delete: async (id: number): Promise<void> => {
    await api.delete(`/notifications/${id}`);
  },
};
export default notificationService;