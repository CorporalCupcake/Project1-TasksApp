const INITIAL_STATE = {
    isUserSignedIn: false
};

// Here the store can also be called userSubStore as the store here is the state that is 

const userReducer = (store = INITIAL_STATE, action) => {
    switch(action.type) {
        case 'SET_IS_USER_SIGNED_IN':
            return {
                ...store,
                isUserSignedIn: action.payload
            }
            
        default: 
            return store
    }
}

export default userReducer;