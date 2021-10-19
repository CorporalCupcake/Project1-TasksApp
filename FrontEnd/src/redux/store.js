import { createStore, applyMiddleware } from "redux";
import logger from "redux-logger";
import rootReducer from './root-reducer';


const middleware = [logger];

// All the deparrtment data
const store = createStore(
    rootReducer,
    applyMiddleware(...middleware)
);

export default store;