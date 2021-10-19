import './App.css';

import { Route, Switch } from 'react-router-dom';

import Header from './components/header/header.component.jsx'
import LoginPage from './pages/login-page/login-page.component';
import HomePage from './pages/home-page/home-page.component'
import ErrorPage from './pages/error-page/error-page.component'
import TasksPage from './pages/tasks-page/tasks-page.component.jsx'

import AuthenticatedRoute from './components/authenticated-route/authenticated-route.component.jsx'


function App() {
  return (
    <div>
      <Header/>

      <Switch>

        <Route exact path='/' component={HomePage} />
        <Route exact path='/login' component={LoginPage} />
        <AuthenticatedRoute exact={true} path='/tasks' component={TasksPage}/>
        <Route path='' component={ErrorPage} /> 

      </Switch>

    </div>
  );
}

export default (App);

// If none of the paths are matched, the default path='' is mathced and is shown an error page. 