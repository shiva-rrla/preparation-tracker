import api from './api';
import { PreparationPlan, PlanCreateRequest, PlanUpdateRequest } from '../types/plan';
import { PageResponse, SearchParams } from '../types/common';

export const planService = {
  getAll: async (params?: SearchParams): Promise<PageResponse<PreparationPlan>> => {
    const response = await api.get<PageResponse<PreparationPlan>>('/preparation-plans', { params });
    return response.data;
  },

  getById: async (id: number): Promise<PreparationPlan> => {
    const response = await api.get<PreparationPlan>(`/preparation-plans/${id}`);
    return response.data;
  },

  create: async (data: PlanCreateRequest): Promise<PreparationPlan> => {
    const response = await api.post<PreparationPlan>('/preparation-plans', data);
    return response.data;
  },

  update: async (id: number, data: PlanUpdateRequest): Promise<PreparationPlan> => {
    const response = await api.put<PreparationPlan>(`/preparation-plans/${id}`, data);
    return response.data;
  },

  delete: async (id: number): Promise<void> => {
    await api.delete(`/preparation-plans/${id}`);
  },

  getByStatus: async (status: string, params?: SearchParams): Promise<PageResponse<PreparationPlan>> => {
    const response = await api.get<PageResponse<PreparationPlan>>('/preparation-plans', {
      params: { ...params, status },
    });
    return response.data;
  },
};

export default planService;