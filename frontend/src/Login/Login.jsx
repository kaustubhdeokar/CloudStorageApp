import React, { useEffect, useState } from 'react'
import './Login.css'
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';
import { createContext, useContext } from 'react';
import { loginUser, saveLoggedInUser, storeToken } from '../services/AuthServices';

function Login() {

    const [emailOrUsername, setEmailOrUsername] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const navigator = useNavigate();

    let handleSubmit = async (e) => {
        e.preventDefault();
        const user = { emailOrUsername, password };
        console.log(user);


        loginUser(user).then((response) => {
            //convert string into base 64 text.
            console.log('btoa:' + window.btoa(emailOrUsername + ":" + password));
            //const token = 'Basic ' + window.btoa(emailOrUsername+":"+password); 
            const jwtToken = 'Bearer ' + response.data.accessToken;
            storeToken(jwtToken);

            saveLoggedInUser(emailOrUsername);
            navigator("/main");

            window.location.reload(false);
        }).catch((error) => {
            console.error(error);
        });
    };

    return (

        <div className="App">
            <form onSubmit={handleSubmit}>
                <h1>Login</h1>

                <label htmlFor="email">Email:</label>
                <div>
                    <input
                        type='text'
                        name='emailOrUsername'
                        value={emailOrUsername}
                        className='form-control'
                        placeholder='Enter username or email'
                        onChange={(e) => setEmailOrUsername(e.target.value)}
                    />
                </div>

                <div>
                    <input
                        type='password'
                        name='password'
                        value={password}
                        className='form-control'
                        placeholder='Enter password.'
                        onChange={(e) => setPassword(e.target.value)}
                    />
                </div>

                <div className='form-group mb-3'>
                    <button className='btn btn-primary' type="submit" onClick={handleSubmit}>Submit</button>
                </div>

                <div className="message">{message ? <p>{message}</p> : null}</div>
            </form>
        </div>
    );
}

export default Login;
