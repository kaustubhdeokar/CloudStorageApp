import React, { useEffect, useState } from 'react'
import './Register.css'
import { BrowserRouter as Router, Routes, Route, useNavigate } from 'react-router-dom';

function Register() {

    const [firstName, setFName] = useState('');
    const [lastName, setLName] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');
    const [loggedIn, setLoggedIn] = useState(false);
    const navigate = useNavigate();


    let handleSubmit = async (e) => {
        e.preventDefault();
        const user = { firstName, lastName, email, password };
        try {
            let res = await fetch("http://localhost:8080/register", {
                method: "POST",
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(user),
            });
            if (res.status === 200) {
                setMessage("User created successfully");
                setLoggedIn(true);
            } else {
                setMessage("Some error occured");
            }
            console.log(res);
        } catch (err) {
            console.log(err);
        }
    };

    useEffect(() => {
        if (loggedIn) {
            navigate('/login');
        }
    }, [loggedIn]);

    return (
        <div className="App">
            <div>
                <form onSubmit={handleSubmit}>
                    <h1>User Registeration</h1>

                    <input
                        type="text"
                        name="firstName"
                        value={firstName}
                        placeholder='first name'
                        onChange={(e) => setFName(e.target.value)}
                    />
                    <br />
                    <input
                        type="text"
                        name="lastName"
                        value={lastName}
                        placeholder='last name'
                        onChange={(e) => setLName(e.target.value)}
                    />
                    <br />
                    <input
                        type="text"
                        name="email"
                        value={email}
                        placeholder='email'
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <br />
                    <input
                        type="password"
                        name="password"
                        value={password}
                        placeholder='Password'
                        onChange={(e) => setPassword(e.target.value)}
                    />
                    <br />
                    <button type="submit" onClick={handleSubmit}>Submit</button>
                    <br />
                    <div className="message">{message ? <p>{message}</p> : null}</div>
                </form>
            </div>
        </div >

    );
}

export default Register;
