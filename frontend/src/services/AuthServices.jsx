import axios from "axios"

const AUTH_REST_BASE_URL = 'http://localhost:8080/api/auth'

export function registerUser(registerObj){
    return axios.post(AUTH_REST_BASE_URL+'/register', registerObj);
}

export function loginUser(loginObj){
    return axios.post(AUTH_REST_BASE_URL+'/login', loginObj);
}