import React from 'react';
import { connect } from 'react-redux';
import { Route, Redirect } from 'react-router-dom';
import { createStructuredSelector } from 'reselect';
import { selectJwt } from '../../redux/auth/auth.selector';

const AuthenticatedRoute = ({ jwt, exact, path, component }) => (
    <div>
        {
            jwt !== '' 
            ?
                <Route exact={exact} path={path} component={component}/>
            : 
                <Redirect exact to='/login'/>
        }
    </div>
)

const mapStateToProps = createStructuredSelector({
    jwt: selectJwt
});

export default connect(mapStateToProps)(AuthenticatedRoute);