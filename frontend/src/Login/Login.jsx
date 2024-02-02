import React, { useState } from 'react'
import './Login.css'

function Login() {

    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [message, setMessage] = useState('');

    let handleSubmit = async (e) => {
        e.preventDefault();
        const user = { email, password };
        try {
            let res = await fetch("http://localhost:8080/login", {
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

    return (

        <div className="App">
            <form onSubmit={handleSubmit}>
                <h1>Login</h1>

                <label htmlFor="email">Email:</label>
                <input
                    type="text"
                    id="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />

                <label htmlFor="password">Password:</label>
                <input
                    type="password"
                    id="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                />
                <br />
                <button type="submit" onClick={handleSubmit}>Submit</button>
                <br />
                <div className="message">{message ? <p>{message}</p> : null}</div>
            </form>
        </div>
    );
}

export default Login;
