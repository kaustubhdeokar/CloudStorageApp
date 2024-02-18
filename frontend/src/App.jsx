import './App.css'
import { Route, Routes } from 'react-router-dom'
import Login from './Login/Login'
import Register from './Register/Register'
import MainPage from './MainPage/MainPage'
import 'bootstrap/dist/css/bootstrap.min.css'

function App() {

  return (
    <Routes>
    <Route path="/" element={<Register />} />
      <Route path="/register" element={<Register />} />
      <Route path="/login" element={<Login />} />
      <Route path="/main" element={<MainPage />} />
    </Routes>
  )
}

export default App
