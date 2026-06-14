import api from './api';

export interface DashboardStats {
  totalResources: number;
  completedResources: number;
  totalPlans: number;
  completedPlans: number;
  totalGoals: number;
  achievedGoals: number;
  totalCertifications: number;
  completedCertifications: number;
  totalStudyHours: number;
  weeklyStudyHours: number;
  currentStreak: number;
  pendingTasks: number;
  upcomingDeadlines: Array<{ id: number; title: string; dueDate: string; type: string }>;
  recentActivity: Array<{ id: number; action: string; description: string; timestamp: string }>;
}

const dashboardService = {
  getStats: async (): Promise<DashboardStats> => {
    const response = await api.get<DashboardStats>('/dashboard');
    return response.data;
  },
};
export default dashboardService;