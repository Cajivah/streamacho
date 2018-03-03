import React, { Component } from 'react';
import LandingPage from './landing/landing';
import Navigation from './navigation/navigation';
import './app.css';

class App extends Component {
  render() {
    return (
      <div className="app">
        <Navigation />
        <LandingPage />
      </div>
    );
  }
}

export default App;
