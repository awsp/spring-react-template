import React, {Component} from 'react';
import {Redirect} from 'react-router-dom';

class Preferences extends Component {

	state = {
		redirect: ''
	};

	backToHome = () => {
		this.setState({redirect: '/'});
	};

	render() {
		if (this.state.redirect !== '') {
			return <Redirect to={this.state.redirect}/>;
		}
		return (
			<div>
				Preferences. <button onClick={this.backToHome}>Back to home</button>
			</div>
		);
	}
}

export default Preferences;