import api from './api';
import { Revision, RevisionCreateRequest, RevisionUpdateRequest } from '../types/revision';
import { PageResponse, SearchParams } from '../types/common';

const revisionService = {
  getAll: async (params?: SearchParams): Promise<PageResponse<Revision>> => {
    const response = await api.get<PageResponse<Revision>>('/revisions', { params });
    return response.data;
  },
  getById: async (id: number): Promise<Revision> => {
    const response = await api.get<Revision>(`/revisions/${id}`);
    return response.data;
  },
  create: async (data: RevisionCreateRequest): Promise<Revision> => {
    const response = await api.post<Revision>('/revisions', data);
    return response.data;
  },
  update: async (id: number, data: RevisionUpdateRequest): Promise<Revision> => {
    const response = await api.put<Revision>(`/revisions/${id}`, data);
    return response.data;
  },
  delete: async (id: number): Promise<void> => {
    await api.delete(`/revisions/${id}`);
  },
};
export default revisionService;