import {LOGIN_REQUEST, LOGOUT_REQUEST} from "../actions/auth_action.h";

const initialState = {
  authenticated: false,
  username: '',
  authLevel: 0
};

export default (state = initialState, action) => {
  switch (action.type) {
    case LOGIN_REQUEST:
      return {
        ...state,
        authenticated: action.payload.authenticated,
        username: action.payload.username,
        authLevel: action.payload.authLevel,
      };

    case LOGOUT_REQUEST:
      return {
        ...state,
        authenticated: false,
        username: '',
        authLevel: '',
      };

    default:
      return state;
  }
}