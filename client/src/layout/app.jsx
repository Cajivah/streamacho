import React, { Component } from 'react';
import Bogdan from './bogdan.png';
import LandingPage from './landing/landing';
import './app.css';

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <img src={Bogdan} className="App-logo" alt="logo" />
          <h1 className="App-title">Hello, I'm Bogdan!</h1>
        </header>
        <p className="App-intro">
          I'm pro developer
        </p>
        <LandingPage />
      </div>
    );
  }
}

export default App;
