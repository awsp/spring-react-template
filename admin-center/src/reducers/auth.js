import {APP_NOT_READY, APP_READY, LOGIN_REQUEST, LOGOUT_REQUEST} from "../actions/auth_action.h";

const initialState = {
  authenticated: false,
  username: '',
  appReady: false
};

export default (state = initialState, action) => {
  switch (action.type) {
    case LOGIN_REQUEST:
      return {
        ...state,
        authenticated: action.payload.authenticated,
        username: action.payload.username,
        appReady: true,
      };

    case LOGOUT_REQUEST:
      return {
        ...state,
        authenticated: false,
        username: '',
        appReady: true,
      };

    case APP_NOT_READY:
      return {
        ...state,
        appReady: false
      };

    case APP_READY:
      return {
        ...state,
        appReady: true
      };

    default:
      return state;
  }
}