import api from './api';
import { StudySession, SessionCreateRequest, SessionUpdateRequest } from '../types/session';
import { PageResponse, PaginationParams } from '../types/common';

export const sessionService = {
  getAll: async (params?: PaginationParams): Promise<PageResponse<StudySession>> => {
    const response = await api.get<PageResponse<StudySession>>('/study-sessions', { params });
    return response.data;
  },
  getById: async (id: number): Promise<StudySession> => {
    const response = await api.get<StudySession>(`/study-sessions/${id}`);
    return response.data;
  },
  create: async (data: SessionCreateRequest): Promise<StudySession> => {
    const response = await api.post<StudySession>('/study-sessions', data);
    return response.data;
  },
  update: async (id: number, data: SessionUpdateRequest): Promise<StudySession> => {
    const response = await api.put<StudySession>(`/study-sessions/${id}`, data);
    return response.data;
  },
  delete: async (id: number): Promise<void> => {
    await api.delete(`/study-sessions/${id}`);
  },
};
export default sessionService;