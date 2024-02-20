import axios from "axios"

const AUTH_REST_BASE_URL = 'http://localhost:8080/api/auth'

axios.interceptors.request.use(function (config) {
    // Do something before request is sent
    config.headers['Authorization'] = getToken();
    
    console.log('config headers start')
    console.log(config.headers)
    console.log('config headers end')

    return config;
}, function (error) {
    // Do something with request error
    return Promise.reject(error);
});

export function handleLogout(){
    sessionStorage.removeItem("authUser");
}

export function registerUser(registerObj) {
    return axios.post(AUTH_REST_BASE_URL + '/register', registerObj);
}

export function loginUser(loginObj) {
    return axios.post(AUTH_REST_BASE_URL + '/login', loginObj);
}

export function storeToken(token) {
    localStorage.setItem("token", token);
}

export function getToken() {
    return localStorage.getItem("token");
}

export function saveLoggedInUser(username) {
    sessionStorage.setItem("authUser", username);
}

export function isUserLoggedIn() {
    const username = sessionStorage.getItem("authUser");
    return username != null;
}

export function getLoggedInUser() {
    return sessionStorage.getItem("authUser");
}