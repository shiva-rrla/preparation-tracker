import api from './api';
import { Resource, ResourceCreateRequest, ResourceUpdateRequest } from '../types/resource';
import { PageResponse, SearchParams } from '../types/common';

export const resourceService = {
  getAll: async (params?: SearchParams): Promise<PageResponse<Resource>> => {
    const response = await api.get<PageResponse<Resource>>('/resources', { params });
    return response.data;
  },

  getById: async (id: number): Promise<Resource> => {
    const response = await api.get<Resource>(`/resources/${id}`);
    return response.data;
  },

  create: async (data: ResourceCreateRequest): Promise<Resource> => {
    const response = await api.post<Resource>('/resources', data);
    return response.data;
  },

  update: async (id: number, data: ResourceUpdateRequest): Promise<Resource> => {
    const response = await api.put<Resource>(`/resources/${id}`, data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/resources/${id}`);
  },

  search: async (query: string, params?: SearchParams): Promise<PageResponse<Resource>> => {
    const response = await api.get<PageResponse<Resource>>('/resources/search', {
      params: { ...params, search: query },
    });
    return response.data;
  },
};

export default resourceService;