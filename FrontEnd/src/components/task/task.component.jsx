import React from 'react';

import FormInput from '../form-input/form-input.component'
import TextAreaInput from '../textarea-input/textarea-input.component'
import axios from 'axios'

class Task extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            ...props
        }
    }

    handleChange = (fieldName, event) => {
        event.preventDefault();
        console.log(this.state)
        this.setState({
            [fieldName]: event.target.value
        })
    }

    handleDelete = () => {
        axios.get(`http://localhost:8080/tasks/deleteTask?uuid=${this.state.uuid}`,
            {
                headers: {
                    'Authorization': `Bearer ${this.props.jwt}` 
                }
            }
        ) 
        .then ( () => this.state.refreshTasksList())
        .catch(error => console.log(error))
    }

    handleComplete = () => {
        this.setState({ 
            completed: !this.state.completed 
        }, () => this.handleSave())
    }

    handleSave = () => {
        axios.get(`http://localhost:8080/tasks/updateTask?`+
            `uuid=${this.state.uuid}&`+
            `title=${this.state.title}&`+
            `description=${this.state.description}&`+
            `isCompleted=${this.state.completed}&`+
            `priority=${this.state.priority}&`+
            `createdByUuid=${this.state.createdByUuid}&`+
            `createdAtTimestamp=${this.state.createdAtTimestamp}&`+
            `dueByTimestamp=${this.state.dueByTimestamp}`, 
            {
                headers: {
                    'Authorization': `Bearer ${this.props.jwt}` 
                }
            }
        )
        .then(response => this.state.refreshTasksList())
        .catch(error => console.log(error))
    }

    render() { 
        // console.log(this.state)
        const { title, dueByTimestamp, priority, completed, description, uuid } = this.state
        // console.log(isCompleted)

        return (
            <div className="card border border-primary border-4 my-5 mx-5 rounded-3">
                <div className="task pt-3 pb-3 ">
                    <FormInput
                        type='text'
                        name='Title'
                        value={title}
                        placeholder='Title of the Task...'
                        onChange={event => this.handleChange('title', event)}
                        forKey='Task Title'
                    />

                    <TextAreaInput
                        type='text'
                        name='Description'
                        value={description}
                        placeholder='Description of the Task...'
                        onChange={event => this.handleChange('description', event)}
                        forKey='Task Description'
                    />

                    <div className='d-flex flex-row'>
                        <FormInput
                            type='text'
                            name='Priority'
                            value={priority}
                            placeholder='Priority of the Task (1-3)...'
                            onChange={event => this.handleChange('priority', event)}
                            forKey='Task Priority'
                        />

                        <button 
                            type="button" 
                            className="btn btn-info w-50"
                            onClick={this.handleSave}
                        >
                            Save Changes
                        </button> 

                        <FormInput
                            type='date'
                            name='Due By Date'
                            value={dueByTimestamp}
                            onChange={event => this.handleChange('dueByTimestamp', event)}
                            forKey='Task Due Date'
                        />   

                    </div> 


                    <div className='d-flex flex-column'>
                    {
                        completed
                            ? 
                            <button 
                                type="button" 
                                className="btn btn-success w-50 mt-5 position-relative bottom-0 start-50 translate-middle-x"
                                onClick={this.handleComplete} 
                            >
                                Un-Complete Task
                            </button>
                            :                     
                            <button 
                                type="button" 
                                className="btn btn-outline-success w-50 mt-5 position-relative bottom-0 start-50 translate-middle-x"
                                onClick={this.handleComplete}
                            >
                                Complete Task
                            </button>
                    }

                    <button 
                        type="button" 
                        className="btn btn-danger w-25 mt-5 position-relative bottom-0 start-50 translate-middle-x"
                        onClick={this.handleDelete}
                    >
                        Delete Task
                    </button>   
                    </div>
                </div>
            </div>
        )
    }
}

export default Task;