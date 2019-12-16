import {APP_NOT_READY, APP_READY, LOGIN_REQUEST, LOGOUT_REQUEST, LS_KEY} from "./auth_action.h";

export const loginRequest = credentials => dispatch => {
  // Just a mock, substitute API request in production environment
  let payload = {
    authenticated: credentials.username === 'admin' && credentials.password === 'password',
    username: credentials.username
  };

  localStorage.setItem(LS_KEY, JSON.stringify(payload));
  dispatch({type: LOGIN_REQUEST, payload});
};

export const logoutRequest = () => dispatch => {
  localStorage.removeItem(LS_KEY);
  dispatch({type: LOGOUT_REQUEST})
};

export const checkAuthStatus = () => dispatch => {
  dispatch({type: APP_NOT_READY});

  // Mock
  let auth = localStorage.getItem(LS_KEY);
  if (auth) {
    let payload = JSON.parse(auth);
    setTimeout(() => {
      if (payload.authenticated) {
        dispatch({type: LOGIN_REQUEST, payload});
      }
    }, 500);
  } else {
    dispatch({type: APP_READY});
  }
};