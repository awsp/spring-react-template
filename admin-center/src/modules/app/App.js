import React, {Component} from 'react';
import './App.css';
import {HashRouter as Router, Route, Link, Switch} from 'react-router-dom';

import DashboardComponent from "../dashboard/DashboardComponent";
import NotFound from "./NotFound";
import PreferencesComponent from "../preferences/PreferencesComponent";

class App extends Component {
	render() {
		return (
			<div>
				<Router>
					Admin Center
					<nav>
						<Link to="/"> / </Link>
						<Link to="/preferences"> /Preferences </Link>
					</nav>

					<Switch>
						<Route path="/" exact component={DashboardComponent} />
						<Route path="/preferences" component={PreferencesComponent} />
						<Route component={NotFound} />
					</Switch>
				</Router>
			</div>
		);
	}
}

export default App;
