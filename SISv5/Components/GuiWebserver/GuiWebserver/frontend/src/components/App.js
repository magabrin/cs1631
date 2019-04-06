import React from 'react';
import ReactDOM from 'react-dom';
import LandingPage from './LandingPage';

const App = () => (
  <div id="test">
    <LandingPage />
  </div>
);

const wrapper = document.getElementById('app');

wrapper ? ReactDOM.render(<App />, wrapper) : null;
