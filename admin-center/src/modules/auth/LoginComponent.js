import React, {Component} from 'react';
import {withRouter} from "react-router-dom";

class LoginComponent extends Component {
	render() {
		return (
			<form>
				<div><input type="text" placeholder="username"/></div>
				<div><input type="password" placeholder="password"/></div>
				<div><button type="submit">Login</button></div>
			</form>
		);
	}
}

export default withRouter(LoginComponent);