import api from './api';
import { InterviewTopic, InterviewTopicCreateRequest, InterviewTopicUpdateRequest } from '../types/interview';
import { PageResponse, SearchParams } from '../types/common';

const interviewService = {
  getAll: async (params?: SearchParams): Promise<PageResponse<InterviewTopic>> => {
    const response = await api.get<PageResponse<InterviewTopic>>('/interview-topics', { params });
    return response.data;
  },
  getById: async (id: number): Promise<InterviewTopic> => {
    const response = await api.get<InterviewTopic>(`/interview-topics/${id}`);
    return response.data;
  },
  create: async (data: InterviewTopicCreateRequest): Promise<InterviewTopic> => {
    const response = await api.post<InterviewTopic>('/interview-topics', data);
    return response.data;
  },
  update: async (id: number, data: InterviewTopicUpdateRequest): Promise<InterviewTopic> => {
    const response = await api.put<InterviewTopic>(`/interview-topics/${id}`, data);
    return response.data;
  },
  delete: async (id: number): Promise<void> => {
    await api.delete(`/interview-topics/${id}`);
  },
};
export default interviewService;