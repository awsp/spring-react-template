import React, {Component} from 'react';
import {withRouter} from "react-router";

class PreferencesComponent extends Component {

	backToHome = () => {
		this.props.history.push(`/`);
	};

	render() {
		return (
			<div>
				Preferences. <button onClick={this.backToHome}>Back to home</button>
			</div>
		);
	}
}

export default withRouter(PreferencesComponent);