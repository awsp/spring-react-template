import React, {Component} from 'react';
import {Redirect, Route} from 'react-router-dom';
import PropTypes from 'prop-types';

class PrivateRoute extends Component {
  render() {
    let content;
    if (this.props.ready) {
      content = this.props.auth ? <Route {...this.props} /> : <Redirect to="/login"/>
    } else {
      return <></>;
    }

    return content;
  }
}

PrivateRoute.propTypes = {
  auth: PropTypes.bool.isRequired,
  ready: PropTypes.bool.isRequired,
};

export default PrivateRoute;

