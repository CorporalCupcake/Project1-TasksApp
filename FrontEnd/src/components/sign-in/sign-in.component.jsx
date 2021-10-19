/**
 * Sign In Component
 * 
 * This component stores the following parameter(s) in local state:
 *      - email: The user's email | String
 *      - password: The user's password | String
 * 
 * 
 * If no exceptions are thrown, the component makes an axios POST request to the backend on the URI '/user/signin'. 
 * The following parameter(s) are contained in the body of the POST request:
 *      - email: The user's email | String
 *      - password: The user's md5-hashed password | String
 * 
 * 
 * This component expects the following case(s) returned by the backened:
 *      1. Code 200: Ok | The sign in was successful
 *      2. Code 401: Unauthorized | The user entered the incorrect email and/or password
 *      3. Code 404: Not Found | The user never created an account
 * Error responses are sent by the backend with their corresponding error message.
 * 
 * If user sign in is successful (Code 200 Ok), the backend returns a JWT containing the user email.
 * This JWT is stored in the auth reducer.
 *
 */

import axios from 'axios';
import React from 'react';
import { isEmpty } from 'validator'
import { MD5 } from 'crypto-js';
import { connect } from 'react-redux';
import { setJwt } from '../../redux/auth/auth.actions';
import { setIsUserSignedIn } from '../../redux/user/user.actions';
import { withRouter } from 'react-router-dom';

import FormInput from '../form-input/form-input.component';

class SignIn extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            email: '',
            password: '',
            isError: false,
            errorMessage: ''
        }
    }

    handleChange = (fieldName, event) => {
        event.preventDefault();
        this.setState({
            [fieldName]: event.target.value,
            isEmail: false
        })
    }

    handleSubmit = async () => {
        const { email, password } = this.state;

        if (isEmpty(email) || isEmpty(password)) {
            this.setState({ 
                isError: true ,
                errorMessage: 'Please fill all the fields.'
            })
            
        } else {
            await axios.post('http://localhost:8080/users/signin', 
                { email, password: MD5(password).toString() }, { timeout:6000 }
            )
            .then( response => {
                this.props.setJwt(response.data)
                this.props.setIsUserSignedIn(true)
                this.props.history.push('/')
            })
            .catch( error => {   // Catches any non 2XX code
                if (error.response) {
                    // The request was made and the server responded with a status code that falls out of the range of 2xx
                    this.setState({ 
                        isError: true,
                        errorMessage: error.response.data
                    })

                } else if (error.request) {
                    // The request was made but no response was received
                    console.log(error.request);
                    this.setState({ 
                        isError: true,
                        errorMessage: 'We are unable to communicate with our servers. Please try again later'
                    })

                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                    this.setState({
                        isError: true,
                        errorMessage: 'We are experiencing an error. Please try again later'
                    })
                }
            })
        }
    }

    render() { return (
        <form className='px-5 py-5 border border-primary rounded-3 border-3'>

            {
                this.state.isError
                ?   <div className="alert alert-danger" role="alert">
                       {this.state.errorMessage}
                    </div>
                : null 
            }

            <FormInput
                type='email'
                name='Email'
                value={this.state.email}
                placeholder='name@example.com'
                subText='We will never share your email with others'
                onChange={event => this.handleChange('email', event)}
            />

            <FormInput
                type='password'
                name='Password'
                value={this.state.password}
                placeholder='Password'
                subText='Your password must be 8-20 characters long, contain at least one number and one uppercase letter, and must not contain spaces.'
                onChange={event => this.handleChange('password', event)}
            />

            <button 
                type="button" onClick={this.handleSubmit} 
                className='btn btn-outline-primary w-75 my-3 position-relative bottom-0 start-50 translate-middle-x'
            >
                    Sign In
            </button>
        </form>
    )}
}

const mapDispatchToProps = dispatch => ({
    setJwt: jwt => dispatch(setJwt(jwt)),
    setIsUserSignedIn: (signInStatus) => dispatch(setIsUserSignedIn(signInStatus))
})

export default withRouter(connect(null, mapDispatchToProps)(SignIn));