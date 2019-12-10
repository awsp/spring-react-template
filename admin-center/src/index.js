import React from 'react';
import ReactDOM from 'react-dom';
import * as serviceWorker from './serviceWorker';
import {applyMiddleware, compose, createStore} from 'redux';
import {Provider} from 'react-redux';
import thunk from 'redux-thunk';
import {createStateSyncMiddleware} from "redux-state-sync";

import rootReducer from './reducers';
import App from './App';
import './index.css';


// ----------------------- Redux Declaration -----------------------
const composeEnhancers = typeof window === 'object' && window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ ?
	window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__({
		// Specify extension’s options like name, actionsBlacklist, actionsCreators, serialize...
	}) : compose;

const middleware = [
	thunk,
	createStateSyncMiddleware({
		// empty config
	})
];

const enhancer = composeEnhancers(
	applyMiddleware(...middleware)
);

const store = createStore(rootReducer, enhancer);
// ----------------------- Redux Declaration -----------------------


ReactDOM.render(
	<Provider store={store}>
		<App/>
	</Provider>,
	document.getElementById('root'));

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
