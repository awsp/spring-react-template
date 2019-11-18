import React, {Component} from 'react';
import {withRouter} from "react-router";

class DashboardComponent extends Component {
	render() {
		return (
			<div>
				Dashboard
			</div>
		);
	}
}

export default withRouter(DashboardComponent);