import React, {Component} from 'react';
import {Redirect, Route} from 'react-router-dom';
import PropTypes from 'prop-types';

class PrivateRoute extends Component {
  render() {
    return (
      this.props.auth ? <Route {...this.props} /> : <Redirect to="/login"/>
    );
  }
}

PrivateRoute.propTypes = {
  auth: PropTypes.bool.isRequired
};

export default PrivateRoute;

