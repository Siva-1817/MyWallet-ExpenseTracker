import axios from "axios";
import API_BASE_URL from "./auth.config";

const register_req = async (username, email, password) => {
  return await axios.post(API_BASE_URL + '/auth/signup', {
      userName: username, 
      email: email, 
      password: password
  })
}

const login_req = async (email, password) => {
  const response = await axios.post(API_BASE_URL + '/auth/signin', {email, password})

  if (response.data.token) {
      console.log(response.data)
      localStorage.setItem("user", JSON.stringify(response.data));
      window.location.reload()
  }

  return response;
}

const getCurrentUser = () => {
  return JSON.parse(localStorage.getItem("user"));
};

const logout_req = () => {
  localStorage.removeItem("user");
  localStorage.removeItem("message");
  sessionStorage.removeItem("user");
  sessionStorage.removeItem("token");
}

const resetPassword = async (email, password, confirmation) => {
  return await axios.post(API_BASE_URL + '/auth/forgotPassword/resetPassword', {
      email,
      newPassword: password,
      confirmPassword: confirmation
  })
}

const authHeader = () => {
  const user = getCurrentUser();
  if (user && user.token) {
    return { Authorization: 'Bearer ' + user.token };
  } else {
    return {};
  }
}

const AuthService = {
  register_req,
  login_req,
  getCurrentUser,
  logout_req,
  resetPassword,
  authHeader
}

export default AuthService;