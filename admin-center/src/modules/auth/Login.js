import React, {Component} from 'react';
import {connect} from 'react-redux';
import {Redirect} from 'react-router-dom';
import {bindActionCreators} from "redux";
import {loginRequest} from "../../actions/auth_action";

class Login extends Component {

  state = {
    username: '',
    password: ''
  };

  onChange = (e) => {
    this.setState({
      [e.target.name]: e.target.value
    });
  };

  onSubmit = (e) => {
    e.preventDefault();
    const {username, password} = this.state;
    this.clearUserInput();
    this.props.loginRequest({username, password});
  };

  clearUserInput = () => {
    this.setState({
      username: '',
      password: ''
    });
  };

  render() {
    if (this.props.authenticated) {
      return <Redirect to="/"/>
    }

    return (
      <form onSubmit={this.onSubmit}>
        <div><input type="text" placeholder="username" onChange={this.onChange} name="username"/></div>
        <div><input type="password" placeholder="password" onChange={this.onChange} name="password"/></div>
        <div>
          <button type="submit">Login</button>
        </div>
      </form>
    );
  }
}

const mapStateToProps = state => ({
  authenticated: state.auth.authenticated
});

const mapDispatchToProps = dispatch => ({
  loginRequest: bindActionCreators(loginRequest, dispatch),
});

export default connect(mapStateToProps, mapDispatchToProps)(Login);