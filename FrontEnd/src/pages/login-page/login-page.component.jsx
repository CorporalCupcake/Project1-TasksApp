import React from 'react';

import SignIn from '../../components/sign-in/sign-in.component';
import SignUp from '../../components/sign-up/sign-up.component';

class LoginPage extends React.Component {

    constructor(props){
        super(props);

        this.state = {
            showSignIn: false,
            showSignUp: false
        }
    }

    showSignInView = () => {
        this.setState({
            showSignIn: true,
            showSignUp: false
        })
    }

    showSignUpView = () => {
        this.setState({
            showSignIn: false,
            showSignUp: true
        })
    }


    render () {
        return (
            <div className='w-50 h-75 py-3
                d-flex flex-column 
                position-absolute top-50 start-50 translate-middle'
            >
                <div className='btn-group' role="group" aria-label="Basic radio toggle button group">
                    <input type="radio" className="btn-check" name="btnradio" id="signin" autoComplete="off" onClick={this.showSignInView} />
                    <label className='btn btn-outline-primary' htmlFor="signin">Sign In</label>

                    <input type="radio" className="btn-check" name="btnradio" id="signup" autoComplete="off" onClick={this.showSignUpView}/>
                    <label className='btn btn-outline-success' htmlFor="signup">Sign Up </label>
                </div>

                <div className=''>
                    {this.state.showSignIn ? <SignIn/> : null}
                    {this.state.showSignUp ? <SignUp/> : null}
                </div>
            </div>
            
        )
    }
}

export default LoginPage;