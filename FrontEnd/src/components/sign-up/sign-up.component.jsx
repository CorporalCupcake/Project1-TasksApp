/**
 * Sign Up Component
 * 
 * This component stores the following parameter(s) in local state:
 *      - email: The user's email | String
 *      - password: The user's password | String
 *      - firstName: The user's first name | String
 *      - lastName: The user's last name | String
 *      - retypedPassword: The user's re-typed md5-hashed password | String
 *      - isError: A boolean value that indicates whether an there is an error
 *      - errorMessage: The error message | String
 * 
 * If no exceptions are thrown, the component makes an axios POST request to the backend on the URI '/user/signin'. 
 * The following parameter(s) are contained in the body of the POST request:
 *      - email: The user's email | String
 *      - password: The user's md5-hashed password | String
 *      - firstName: The user's first name | String
 *      - lastName: The user's last name | String
 *      - retypedPassword: The user's re-typed md5-hashed password | String
 * 
 * The class makes sure the user fills all the fields, enter a valid email and strong password. It also check the two password fields match each other.
 * 
 * This component expects the following case(s) returned by the backened:
 *      1. Code 201: Created | The sign up was successful
 *      2. Code 400: Bad Request | The user entered an invalid email, a weak passwordm both passwords do not match or invalid or empty first and last names.
 *      3. Code 403: Forbidden | The user already created an account.
 * Error responses are sent by the backend with their corresponding error message.
 * 
 * If user sign up is successful (Code 201 Created), the backend returns a JWT containing the user email.
 * This JWT is stored in the auth reducer.
 *
 */

import axios from 'axios';
import React from 'react';
import { MD5 } from 'crypto-js';
import { isStrongPassword, isEmpty, isEmail } from 'validator'
import { connect } from 'react-redux';
import { setJwt } from '../../redux/auth/auth.actions';

import FormInput from '../form-input/form-input.component';


class SignUp extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            firstName: '',
            lastName: '',
            email: '',
            password: '',
            retypedPassword: '',
            isError: false,
            errorMessage: ''
        }
    }

    handleChange = (fieldName, event) => {
        event.preventDefault();
        this.setState({
            [fieldName]: event.target.value,
            isError: false
        })
    }


    handleSubmit = async () => {
        const { firstName, lastName, email, password, retypedPassword } = this.state;

        // if(!isEmail(email)){
        //     this.setState({ 
        //         isError: true,
        //         errorMessage: 'Please enter a valid email.'
        //     })

        // } else if(!isStrongPassword(password, { minSymbols: 0})){
        //     this.setState({ 
        //         isError: true,
        //         errorMessage: 'Please enter a strong password.'
        //     })

        // } else if (isEmpty(firstName) || isEmpty(lastName) || isEmpty(email) || isEmpty(password) || isEmpty(retypedPassword)){ 
        //     this.setState({ 
        //         isError: true ,
        //         errorMessage: 'Please fill all the fields.'
        //     })
            
        // } else if(password !== retypedPassword){
        //     this.setState({ 
        //         isError: true,
        //         errorMessage: 'Your passwords do not match.'
        //     })

        // } else {
            await axios.post('http://localhost:8080/users/signup',{
                firstName,
                lastName,
                email,
                password: MD5(password).toString(),
                retypedPassword: MD5(retypedPassword).toString()
            }) 

            .then( response => this.props.setJwt(response.data) )

            .catch( error => {   //catches any non 200 code
                console.log(error)
                if (error.response) {
                    // The request was made and the server responded with a status code
                    // that falls out of the range of 2xx
                    this.setState({ 
                        isError: true,
                        errorMessage: error.response.data
                    })

                } else if (error.request) {
                    // The request was made but no response was received
                    console.log(error.request);
                    this.setState({ 
                        isError: true,
                        errorMessage: 'F We are unable to communicate with our servers. Please try again later'
                    })

                } else {
                    // Something happened in setting up the request that triggered an Error
                    console.log('Error', error.message);
                    this.setState({
                        isError: true,
                        errorMessage: 'F We are experiencing an error. Please try again later'
                    })
                }
            })
        // }
    }

    render() { return (
        <form className='px-5 py-3 border border-success rounded-3 border-3 d-flex flex-column'>

            {
                this.state.isError
                ?   <div className="alert alert-danger" role="alert">
                        {this.state.errorMessage}
                    </div>
                : null 
            }


            <div className='d-flex flex-row justify-content-evenly'>
                <FormInput
                    type='text'
                    name='First Name'
                    value={this.state.firstName}
                    placeholder='First Name'
                    onChange={event => this.handleChange('firstName', event)}
                />

                <div className='mx-3'/>

                <FormInput
                    type='text'
                    name='Last Name'
                    value={this.state.lastName}
                    placeholder='Last Name'
                    onChange={event => this.handleChange('lastName', event)}
                />  

            </div>
            

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

            <FormInput
                type='password'
                name='Retype Password'
                value={this.state.retypedPassword}
                placeholder='Retpe Password'
                onChange={event => this.handleChange('retypedPassword', event)}
            />          

            <button 
                type="button" 
                onClick={this.handleSubmit}
                className='btn btn-outline-success w-75 my-3 position-relative bottom-0 start-50 translate-middle-x'
            >
                Sign Up
            </button>
        </form>
    )}
}

const mapDispatchToProps = dispatch => ({
    setJwt: jwt => dispatch(setJwt(jwt))
})

export default connect(null, mapDispatchToProps)(SignUp);