import React, { Component } from 'react';
import LandingPage from './landing/landing';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import Fallback from './fallback/fallback';
import Register from './register/register';
import Login from './login/login';
import './app.css';

class App extends Component {
  render() {
    return (
      <div className="app">
        <Router>
          <div>
            <Route exact path="/" component={LandingPage} />
            <Route path="/register" component={Register} />
            <Route path="/login" component={Login} />
            <Route component={Fallback} />
          </div>
        </Router>
      </div>
    );
  }
}

export default App;
