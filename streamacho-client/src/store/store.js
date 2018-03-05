import { createStore } from 'redux';
import { reducers } from './reducers';

const configureStore = () => createStore(reducers, window.INITIAL_STATE);

export const store = configureStore();