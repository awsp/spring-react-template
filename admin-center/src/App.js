import React, {Component} from 'react';
import {HashRouter as Router, Link, Route, Switch} from 'react-router-dom';
import {connect} from 'react-redux';
import {bindActionCreators} from "redux";

import PrivateRoute from "./modules/auth/PrivateRoute";
import Dashboard from "./modules/dashboard/Dashboard";
import NotFound from "./modules/common/NotFound";
import Preferences from "./modules/preferences/Preferences";
import Login from "./modules/auth/Login";
import {checkAuthStatus, logoutRequest} from "./actions/auth_action";

import './App.css';

class App extends Component {

  componentDidMount() {
    this.props.checkAuthStatus();
  }

  render() {
    return (
      <div>
        <Router>
          <h1>Admin Center</h1>

          <nav>
            <Link to="/"> / </Link> |
            <Link to="/preferences"> /Preferences </Link> |
            <Link to="/login"> /Login</Link> | {' '}
            <button onClick={this.props.logout}>Logout</button>
          </nav>

          <Switch>
            <PrivateRoute path="/" exact
                          component={Dashboard}
                          auth={this.props.authenticated}
                          ready={this.props.ready}/>
            <PrivateRoute path="/preferences"
                          component={Preferences}
                          auth={this.props.authenticated}
                          ready={this.props.ready}/>
            <Route path="/login" exact component={Login}/>
            <Route component={NotFound}/>
          </Switch>
        </Router>
      </div>
    );
  }
}

const mapStateToProps = state => ({
  authenticated: state.auth.authenticated,
  ready: state.auth.appReady
});

const mapDispatchToProps = dispatch => ({
  logout: bindActionCreators(logoutRequest, dispatch),
  checkAuthStatus: bindActionCreators(checkAuthStatus, dispatch)
});

export default connect(mapStateToProps, mapDispatchToProps)(App);
