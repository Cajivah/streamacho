import React, { Component } from 'react';
import Navigation from '../navigation/navigation';
import './landingPage.css';

export default class LandingPage extends Component {
  render() {
    return (
      <div>
        <Navigation />
        <div className='landing-page'>
          Landing Page here
        </div>
      </div>
    );
  }
}
