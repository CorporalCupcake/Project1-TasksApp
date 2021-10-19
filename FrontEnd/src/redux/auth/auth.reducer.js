const INITIAL_STATE = {
    jwt: ''
};

// Here the store can also be called userSubStore as the store here is the state that is 

const authReducer = (store = INITIAL_STATE, action) => {
    switch(action.type) {
        case 'SET_JWT': 
            return {
                ...store,
                jwt: action.payload
            }

        case 'SIGN_OUT':
            return {
                ...store,
                jwt: ''
            }
            
        default: 
            return store
    }
}

export default authReducer;