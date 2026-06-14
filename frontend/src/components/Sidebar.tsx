import React from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import {
  List,
  ListItem,
  ListItemButton,
  ListItemIcon,
  ListItemText,
  Toolbar,
  Box,
} from '@mui/material';
import DashboardIcon from '@mui/icons-material/Dashboard';
import LibraryBooksIcon from '@mui/icons-material/LibraryBooks';
import AssignmentIcon from '@mui/icons-material/Assignment';
import SchoolIcon from '@mui/icons-material/School';
import VerifiedUserIcon from '@mui/icons-material/VerifiedUser';
import FlagIcon from '@mui/icons-material/Flag';
import MapIcon from '@mui/icons-material/Map';
import NoteIcon from '@mui/icons-material/Note';
import RefreshIcon from '@mui/icons-material/Refresh';
import PsychologyIcon from '@mui/icons-material/Psychology';
import BarChartIcon from '@mui/icons-material/BarChart';
import PersonIcon from '@mui/icons-material/Person';

const iconMap: Record<string, React.ReactElement> = {
  Dashboard: <DashboardIcon />,
  LibraryBooks: <LibraryBooksIcon />,
  Assignment: <AssignmentIcon />,
  School: <SchoolIcon />,
  VerifiedUser: <VerifiedUserIcon />,
  Flag: <FlagIcon />,
  Map: <MapIcon />,
  Note: <NoteIcon />,
  Refresh: <RefreshIcon />,
  Psychology: <PsychologyIcon />,
  BarChart: <BarChartIcon />,
  Person: <PersonIcon />,
};

const NAV_ITEMS = [
  { label: 'Dashboard', path: '/', icon: 'Dashboard' },
  { label: 'Resources', path: '/resources', icon: 'LibraryBooks' },
  { label: 'Plans', path: '/plans', icon: 'Assignment' },
  { label: 'Study Sessions', path: '/sessions', icon: 'School' },
  { label: 'Certifications', path: '/certifications', icon: 'VerifiedUser' },
  { label: 'Goals', path: '/goals', icon: 'Flag' },
  { label: 'Roadmaps', path: '/roadmaps', icon: 'Map' },
  { label: 'Notes', path: '/notes', icon: 'Note' },
  { label: 'Revisions', path: '/revisions', icon: 'Refresh' },
  { label: 'Interview Prep', path: '/interview-topics', icon: 'Psychology' },
  { label: 'Analytics', path: '/analytics', icon: 'BarChart' },
  { label: 'Profile', path: '/profile', icon: 'Person' },
];

const Sidebar: React.FC = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const isActive = (path: string) => {
    if (path === '/') return location.pathname === '/';
    return location.pathname.startsWith(path);
  };

  return (
    <Box sx={{ overflow: 'auto' }}>
      <Toolbar />
      <List>
        {NAV_ITEMS.map((item) => (
          <ListItem key={item.path} disablePadding>
            <ListItemButton
              selected={isActive(item.path)}
              onClick={() => navigate(item.path)}
              sx={{
                '&.Mui-selected': {
                  backgroundColor: 'primary.main',
                  color: 'white',
                  '& .MuiListItemIcon-root': { color: 'white' },
                },
              }}
            >
              <ListItemIcon sx={{ minWidth: 40 }}>
                {iconMap[item.icon]}
              </ListItemIcon>
              <ListItemText primary={item.label} />
            </ListItemButton>
          </ListItem>
        ))}
      </List>
    </Box>
  );
};

export default Sidebar;