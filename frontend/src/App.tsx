import React from 'react';
import { Routes, Route } from 'react-router-dom';
import ProtectedRoute from './components/ProtectedRoute';
import Layout from './components/Layout';
import Login from './pages/Login';
import Register from './pages/Register';
import Dashboard from './pages/Dashboard';
import Resources from './pages/Resources';
import Plans from './pages/Plans';
import StudySessions from './pages/StudySessions';
import Certifications from './pages/Certifications';
import Goals from './pages/Goals';
import Roadmaps from './pages/Roadmaps';
import Notes from './pages/Notes';
import Revisions from './pages/Revisions';
import InterviewTopics from './pages/InterviewTopics';
import Analytics from './pages/Analytics';
import Profile from './pages/Profile';
import NotFound from './pages/NotFound';

const App: React.FC = () => {
  return (
    <Routes>
      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route
        path="/"
        element={
          <ProtectedRoute>
            <Layout />
          </ProtectedRoute>
        }
      >
        <Route index element={<Dashboard />} />
        <Route path="resources" element={<Resources />} />
        <Route path="plans" element={<Plans />} />
        <Route path="study-sessions" element={<StudySessions />} />
        <Route path="certifications" element={<Certifications />} />
        <Route path="goals" element={<Goals />} />
        <Route path="roadmaps" element={<Roadmaps />} />
        <Route path="notes" element={<Notes />} />
        <Route path="revisions" element={<Revisions />} />
        <Route path="interview-topics" element={<InterviewTopics />} />
        <Route path="analytics" element={<Analytics />} />
        <Route path="profile" element={<Profile />} />
        <Route path="*" element={<NotFound />} />
      </Route>
    </Routes>
  );
};

export default App;