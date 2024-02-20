import React from 'react'
import { NavLink } from 'react-router-dom'

import { isUserLoggedIn, handleLogout } from '../services/AuthServices';
import { useNavigate } from 'react-router-dom'

function HeaderComponent() {

    function logout() {
        handleLogout();
        console.log('handle logout');
    }

    const auth = isUserLoggedIn();

    return (
        <div>
            <header>
                <nav className='navbar navbar-expand-md navbar-dark bg-dark'>
                    <div>
                        <a href='' className='navbar-brand'>
                            Todo Management Application
                        </a>
                    </div>

                    <div className='collapse navbar-collapse'>
                        <ul className='navbar-nav'>
                            {
                                auth &&
                                <li className='nav-item'>
                                    <NavLink to="/main" className="nav-link">Main</NavLink>
                                </li>
                            }
                            {
                                auth &&
                                <li className='nav-item'>
                                    <NavLink to="/userdetails" className="nav-link">User Details</NavLink>
                                </li>
                            }
                        </ul>
                    </div>

                    <ul className='navbar-nav'>
                        {
                            (!auth) &&
                            <li className='nav-item'>
                                <NavLink to="/register" className="nav-link">Register</NavLink>
                            </li>
                        }

                        {
                            (!auth) &&
                            <li className='nav-item'>
                                <NavLink to="/login" className="nav-link">Login</NavLink>
                            </li>
                        }

                        {
                            auth &&
                            <li className='nav-item'>
                                <NavLink to="/login" className="nav-link" onClick={logout}>Logout</NavLink>
                            </li>
                        }

                    </ul>

                </nav>
            </header>
        </div>
    )
}

export default HeaderComponent