import React from 'react'
import { NavLink } from 'react-router-dom'
import { isUserLoggedIn, logout } from '../services/AuthService'
import { useNavigate } from 'react-router-dom'

import React from 'react'

function HeaderComponent() {

    function handleLogout(){
        console.log('handle logout');
    }

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
                                <li className='nav-item'>
                                    <NavLink to="/main" className="nav-link">Main</NavLink>
                                </li>
                            }
                        </ul>
                    </div>

                    <ul className='navbar-nav'>
                        {
                            
                            <li className='nav-item'>
                                <NavLink to="/register" className="nav-link">Register</NavLink>
                            </li>
                        }

                        {
                            
                            <li className='nav-item'>
                                <NavLink to="/login" className="nav-link">Login</NavLink>
                            </li>
                        }

                        {
                            
                            <li className='nav-item'>
                                <NavLink to="/login" className="nav-link" onClick={handleLogout}>Logout</NavLink>
                            </li>
                        }

                    </ul>

                </nav>
            </header>
        </div>
    )
}

export default HeaderComponent