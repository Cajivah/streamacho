import React from 'react';
import './registerForm.css';

const registerForm = (props) => (
    <div className='register-form-container'>
        <div className='register-form'>
        <h4>Please register</h4>
        <div className='common-input'>
            <input type='text' placeholder='name' />
        </div>
        <div className='common-input'>
            <input type='text' placeholder='pass' />
        </div>
        </div>
    </div>
);

export default registerForm;