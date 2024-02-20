import './App.css'
import { Route, Routes, BrowserRouter } from 'react-router-dom'
import Login from './Login/Login'
import Register from './Register/Register'
import MainPage from './MainPage/MainPage'
import 'bootstrap/dist/css/bootstrap.min.css'
import { isUserLoggedIn } from './services/AuthServices'
import HeaderComponent from './HeaderComponent/HeaderComponent'
import { Navigate } from 'react-router-dom'
import UserDetails from './UserDetails/UserDetails'

function App() {

  function VerifyAuthentication({ children }) {
    if (isUserLoggedIn()) {
      return children;
    }
    else{
      alert('login bitch')
    }
    return <Navigate to="/" />
  }

  return (
    <>


      <HeaderComponent />
      <Routes>
        <Route path="/" element={<Register />} />
        <Route path="/register" element={<Register />} />
        <Route path="/login" element={<Login />} />
        <Route path="/main" element={
          <VerifyAuthentication>
            <MainPage />
          </VerifyAuthentication>
        }></Route>
        <Route path="/userdetails" element={
          <VerifyAuthentication>
            <UserDetails />
          </VerifyAuthentication>
        }></Route>
      </Routes>

    </>
  )
}

export default App
