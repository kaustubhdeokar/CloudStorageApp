import React, { useEffect, useState } from 'react'
import './Register.css'
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import { registerUser } from '../services/AuthServices.jsx';

function Register() {

    const [name, setName] = useState('');
    const [username, setUsername] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    
    let handleSubmit = async (e) => {
        e.preventDefault();
        const user = { name, username, email, password };
        console.log(user);
        registerUser(user).then((response) => {
            console.log(response.data);
            setMessage(response.data);
        }).catch((error) => {
            setMessage(error.response.data.message);
            console.error(error);
        });
    };


    return (
        <div className="App">
            <div>
                <form onSubmit={handleSubmit}>
                    <h1>User Registeration</h1>

                    <div>
                        <input
                            type='text'
                            name='name'
                            value={name}
                            className='form-control'
                            placeholder='Enter name'
                            onChange={(e) => setName(e.target.value)}
                        />
                    </div>

                    <div>
                        <input
                            type='text'
                            name='username'
                            value={username}
                            placeholder='Enter username'
                            onChange={(e) => setUsername(e.target.value)}
                        />
                    </div>

                    <div>
                        <input
                            type="text"
                            name="email"
                            value={email}
                            placeholder='Enter email address'
                            onChange={(e) => setEmail(e.target.value)}
                        />
                    </div>

                    <div>
                        <input
                            type="password"
                            name="password"
                            value={password}
                            placeholder='Enter password'
                            onChange={(e) => setPassword(e.target.value)}
                        />
                    </div>

                    <div className='form-group mb-3'>
                        <button className='btn btn-primary' type="submit" onClick={handleSubmit}>Submit</button>
                    </div>


                    <div className="message">{message ? <p>{message}</p> : null}</div>
                </form>
            </div>
        </div >

    );
}

export default Register;
