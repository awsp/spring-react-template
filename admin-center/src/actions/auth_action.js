import {LOGIN_REQUEST, LOGOUT_REQUEST} from "./auth_action.h";

export const loginRequest = credentials => dispatch => {
  // Just a mock, substitute API request in production environment
  let payload = {
    authenticated: credentials.username === 'admin' && credentials.password === 'password',
    username: credentials.username
  };

  dispatch({type: LOGIN_REQUEST, payload});
};

export const logoutRequest = () => dispatch => {
  dispatch({type: LOGOUT_REQUEST})
};