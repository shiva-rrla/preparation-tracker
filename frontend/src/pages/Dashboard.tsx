import React, { useEffect } from 'react';
import { Container, Grid, Typography, Paper, List, ListItem, ListItemText, Box, Chip } from '@mui/material';
import AccessTimeIcon from '@mui/icons-material/AccessTime';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import SchoolIcon from '@mui/icons-material/School';
import WhatshotIcon from '@mui/icons-material/Whatshot';
import StatsCard from '../components/StatsCard';
import { useAppDispatch, useAppSelector } from '../store';
import { fetchDashboardStats } from '../store/dashboardSlice';

const Dashboard: React.FC = () => {
  const dispatch = useAppDispatch();
  const { stats, loading } = useAppSelector((state) => state.dashboard);

  useEffect(() => {
    dispatch(fetchDashboardStats());
  }, [dispatch]);

  const mockStats = {
    totalStudyHours: 42,
    activePlans: 5,
    upcomingCerts: 3,
    currentStreak: 7,
  };

  const mockRecentActivity = [
    { id: 1, action: 'Study Session', description: 'Completed React Advanced Patterns', timestamp: new Date().toISOString() },
    { id: 2, action: 'Goal Achieved', description: 'Completed AWS Solutions Architect prep', timestamp: new Date(Date.now() - 86400000).toISOString() },
    { id: 3, action: 'Plan Created', description: 'New plan: Kubernetes Certification', timestamp: new Date(Date.now() - 172800000).toISOString() },
  ];

  const mockUpcomingDeadlines = [
    { id: 1, title: 'AWS Solutions Architect Exam', dueDate: new Date(Date.now() + 604800000).toISOString(), type: 'certification' },
    { id: 2, title: 'Complete Docker Course', dueDate: new Date(Date.now() + 1209600000).toISOString(), type: 'goal' },
  ];

  const displayStats = stats || mockStats;

  return (
    <Container maxWidth="lg">
      <Typography variant="h4" gutterBottom>Dashboard</Typography>
      <Grid container spacing={3} sx={{ mb: 4 }}>
        <Grid item xs={12} sm={6} md={3}>
          <StatsCard
            title="Study Hours"
            value={displayStats.totalStudyHours}
            icon={<AccessTimeIcon fontSize="large" />}
            color="primary.main"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatsCard
            title="Active Plans"
            value={stats?.activePlans || mockStats.activePlans}
            icon={<CheckCircleIcon fontSize="large" />}
            color="success.main"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatsCard
            title="Upcoming Certs"
            value={stats?.upcomingCerts || mockStats.upcomingCerts}
            icon={<SchoolIcon fontSize="large" />}
            color="info.main"
          />
        </Grid>
        <Grid item xs={12} sm={6} md={3}>
          <StatsCard
            title="Day Streak"
            value={stats?.currentStreak || mockStats.currentStreak}
            icon={<WhatshotIcon fontSize="large" />}
            color="error.main"
          />
        </Grid>
      </Grid>

      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>Recent Activity</Typography>
            <List>
              {(stats?.recentActivity?.length ? stats.recentActivity : mockRecentActivity).map((activity) => (
                <ListItem key={activity.id} divider>
                  <ListItemText
                    primary={activity.action}
                    secondary={activity.description}
                  />
                </ListItem>
              ))}
            </List>
          </Paper>
        </Grid>
        <Grid item xs={12} md={6}>
          <Paper sx={{ p: 3 }}>
            <Typography variant="h6" gutterBottom>Upcoming Deadlines</Typography>
            <List>
              {(stats?.upcomingDeadlines?.length ? stats.upcomingDeadlines : mockUpcomingDeadlines).map((deadline) => (
                <ListItem key={deadline.id} divider>
                  <ListItemText
                    primary={deadline.title}
                    secondary={new Date(deadline.dueDate).toLocaleDateString()}
                  />
                  <Chip label={deadline.type} size="small" />
                </ListItem>
              ))}
            </List>
          </Paper>
        </Grid>
      </Grid>
    </Container>
  );
};

export default Dashboard;