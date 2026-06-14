import React from 'react';
import { Typography, Box } from '@mui/material';

interface HeaderProps {
  title: string;
  actions?: React.ReactNode;
}

const Header: React.FC<HeaderProps> = ({ title, actions }) => {
  return (
    <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 3 }}>
      <Typography variant="h4" component="h1">
        {title}
      </Typography>
      {actions && <Box>{actions}</Box>}
    </Box>
  );
};

export default Header;