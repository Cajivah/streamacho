import React, { Component } from 'react';
import { BrowserRouter as Router, Route } from 'react-router-dom';
import { Provider } from 'react-redux';
import LandingPage from './landing/landing';
import Register from './register/registerPage';
import Login from './login/login';
import { store } from '../store/store';
import './app.css';

class App extends Component {
  render() {
    return (
      <Provider store={store}>
        <div className="app">
          <Router>
            <div>
              <Route exact path="/" component={LandingPage} />
              <Route path="/register" component={Register} />
              <Route path="/login" component={Login} />
            </div>
          </Router>
        </div>
      </Provider>
    );
  }
}

export default App;