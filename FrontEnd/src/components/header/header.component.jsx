import React from 'react';
import { connect } from 'react-redux';
import { createStructuredSelector } from 'reselect';
import { selectJwt } from '../../redux/auth/auth.selector';
import { selectIsUserSignedIn } from '../../redux/user/user.selector';
import { signOut } from '../../redux/auth/auth.actions';
import './header.styles.scss'
import { withRouter } from 'react-router-dom';

class Header extends React.Component {

    handleSignIn = () => {
        console.log('SIGN IN')
        this.props.history.push('/login')
    }

    handleSignOut = () => {
        console.log('SIGN OUT')
        this.props.signOut()
    }

    handleViewTasks = () => {
        console.log("VIEW TASKS")
        this.props.history.push('/tasks')
    }

    render() { 
        return (
            <div className='d-flex flex-row justify-content-between px-5 py-3 mb-5 border border-info border-top-0'>

                <div className='logo'>
                    Tasks App
                </div>

                <div className='d-flex flex-row justify-content-around'>
                {
                    !this.props.isUserSignedIn
                    ?
                        <div className='option mx-3' onClick={this.handleSignIn}>
                            SIGN IN
                        </div>
                    : 
                        <div className='option mx-3' onClick={this.handleSignOut}>
                            SIGN OUT
                        </div>
                }

                    <div className='option' onClick={this.handleViewTasks}>
                        VIEW TASKS
                    </div>

                </div>
            </div>
        )
    }

}

const mapStateToProps = createStructuredSelector({
    jwt: selectJwt,
    isUserSignedIn: selectIsUserSignedIn
})

const mapDispatchToProps = dispatch => ({
    signOut: () => dispatch(signOut())
})

export default withRouter(connect(mapStateToProps, mapDispatchToProps)(Header));