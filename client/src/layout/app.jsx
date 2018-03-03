import React, { Component } from 'react';
import { Switch, Route } from 'react-router';
import LandingPage from './landing/landing';
import Fallback from './fallback/fallback';
import Register from './register/register';
import Login from './login/login';
import './app.css';

class App extends Component {
  render() {
    return (
      <div className="app">
        <Switch>
          <Route exact path="/" component={LandingPage} />
          <Route path="/register" component={Register} />
          <Route path="/login" component={Login} />
          <Route component={Fallback} />
        </Switch>
      </div>
    );
  }
}

export default App;
