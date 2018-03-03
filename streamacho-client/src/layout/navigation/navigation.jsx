import React from 'react';
import { Link } from 'react-router-dom';
import './navigation.css';

const Navigation = () => (
  <nav id='navbar' className='navbar'>
    <div className='navbar-list'>
      <div>
        <Link className='navbar-link' to="/">Streamacho</Link>
        <Link className='navbar-link' to='/about'>About</Link>
      </div>
      <div className='navbar-element-right'>
        <Link className='navbar-link' to='/register'>Register</Link>
        <Link className='navbar-link' to='/login'>Login</Link>
      </div>
    </div>
  </nav>
);

export default Navigation;
