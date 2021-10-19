import axios from 'axios';
import React from 'react';
import Task from '../../components/task/task.component';

import { connect } from 'react-redux';
import { selectJwt } from '../../redux/auth/auth.selector'
import { createStructuredSelector } from 'reselect';

import { withRouter } from 'react-router-dom';

class TasksPage extends React.Component {
    constructor(props) {
        super(props);

        this.state = {
            currentPage: 0,
            moveToPage: 0,
            tasks: [],
            totalItems: 0,
            totalPages: 1,
            sort: 1
        }
    }

    refreshTasksList = async () => {
        if (this.state.moveToPage >= 0 && this.state.moveToPage < this.state.totalPages){
            
            const url = `http://localhost:8080/tasks/paginatedTasks?page=${this.state.moveToPage}&sort=${this.state.sort}`
            await axios.get(url, {
                headers: {
                    'Authorization': `Bearer ${this.props.jwt}` 
                }
            })
            .then( response => this.setState({ 
                    currentPage: response.data.currentPage,
                    tasks: response.data.tasks,
                    totalPages: response.data.totalPages,
                    totalItems: response.data.totalItems
                }, () => console.log(this.state))
            )
            .catch( error => {
                if( error.response.data.status === 418) { // JWT either timed out or is altered with.
                    console.log(" JWT ISSUE. Please sign in")
                } else {
                    console.log(error.response)
                }
            })
        }
    }

    componentDidMount = () => {
        this.refreshTasksList()
    }

    handleMoveLeft = () => { 
        const { currentPage } = this.state
        this.setState({
            moveToPage: currentPage-1
        }, () => this.refreshTasksList()) 
    }

    handleMoveRight = () => { 
        const { currentPage } = this.state
        this.setState({
            moveToPage: currentPage+1
        }, () => this.refreshTasksList())
    }

    handleCreate = async () => {
        await axios.get('http://localhost:8080/tasks/create', {
            headers: {
                'Authorization': `Bearer ${this.props.jwt}` 
            }
        })
        .catch( error => {console.log(error)})

        const url = 'http://localhost:8080/tasks/paginatedTasks?page=0'
        await axios.get(url, {
            headers: {
                'Authorization': `Bearer ${this.props.jwt}` 
            }
        })
        .then( response => this.setState({ 
                currentPage: response.data.currentPage,
                tasks: response.data.tasks,
                totalPages: response.data.totalPages,
                totalItems: response.data.totalItems
            }, () => this.refreshTasksList)
        )
        .catch( error => {
            if( error.response.data.status === 418) { // JWT either timed out or is altered with.
                console.log(" JWT ISSUE. Please sign in")
            } else {
                console.log(error.response)
            }
        })
    }

    handleSort = (number) => {
        this.setState({
            sort: number,
        }, () => this.refreshTasksList())
    }

    render() { 
        return (
            <div className="d-flex flex-column align-items-center">

                <div className="btn-group w-25">
                    <button type="button" className="btn btn-warning dropdown-toggle" data-bs-toggle="dropdown" aria-expanded="false">
                        Sort
                    </button>
                    <ul className="dropdown-menu">
                        <li><a className="dropdown-item" href="#" onClick={() => this.handleSort(1)}>By Created New First</a></li>
                        <li><a className="dropdown-item" href="#" onClick={() => this.handleSort(2)}>By Created Old First</a></li>
                        <li><a className="dropdown-item" href="#" onClick={() => this.handleSort(3)}>By Due Date Earlier First</a></li>
                        <li><a className="dropdown-item" href="#" onClick={() => this.handleSort(4)}>By Due Date Later First</a></li>
                    </ul>
                </div>

                <div className="tasks d-flex flex-wrap flex-row justify-content-center">
                {
                    this.state.tasks.map( task => 
                        <Task 
                            key={task.uuid}
                            refreshTasksList={this.refreshTasksList}
                            jwt={this.props.jwt}
                            {...task} 
                        />
                    )
                }
                </div>

                <div className="d-fflex flex-column justify-content-center">
                    <button type="button" className="btn btn-outline-dark" onClick={this.handleCreate}>
                        Add Task
                    </button>

                    <nav aria-label="Page navigation example">
                        <ul className="pagination">
                            <li onClick={this.handleMoveLeft} className="page-item"><a className="page-link" href="#">Previous</a></li>
                            <li onClick={this.handleMoveRight} className="page-item"><a className="page-link" href="#">Next</a></li>
                        </ul>
                    </nav>
                </div>

            </div>
        )
    }
}

const mapStateToProps = createStructuredSelector({
    jwt: selectJwt
});

export default withRouter(connect(mapStateToProps)(TasksPage));