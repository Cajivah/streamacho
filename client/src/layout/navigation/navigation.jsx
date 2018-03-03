import React from 'react';
import './navigation.css';

const Navigation = () => (
  <nav id='navbar' className='navbar'>
    <div className='navbar-list'>
      <div>
        <a className='navbar-link' href='#streamacho'>Streamacho</a>
        <a className='navbar-link' href='#about'>About</a>
      </div>
      <div className='navbar-element-right'>
        <a className='navbar-link' href='#register'>Register</a>
        <a className='navbar-link' href='#login'>Login</a>
      </div>
    </div>
  </nav>
);

export default Navigation;
