import React, {Component} from 'react';
import './App.css';
import {HashRouter as Router, Link, Route, Switch} from 'react-router-dom';

import DashboardComponent from "../dashboard/DashboardComponent";
import NotFound from "./NotFound";
import PreferencesComponent from "../preferences/PreferencesComponent";
import LoginComponent from "../auth/LoginComponent";

class App extends Component {
	render() {
		return (
			<div>
				<Router>
					Admin Center
					<nav>
						<Link to="/"> / </Link> |
						<Link to="/preferences"> /Preferences </Link> |
						<Link to="/login"> /Login</Link>
					</nav>

					<Switch>
						<Route path="/" exact component={DashboardComponent} />
						<Route path="/preferences" component={PreferencesComponent} />
						<Route path="/login" exact component={LoginComponent}/>
						<Route component={NotFound} />
					</Switch>
				</Router>
			</div>
		);
	}
}

export default App;
