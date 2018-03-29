export default {
    createMeeting(state, payload) {
        state.meetings.push(payload.meeting);
    },
    removeMeeting(state, payload) {
        state.meetings.splice(state.meetings.indexOf(payload.meeting), 1);
    },
    initMeetings(state, payload) {
        state.meetings = payload.meetings;
    },
    isAuthenticated(state, payload) {
        state.isAuthenticated = payload.isAuthenticated;
    },
    registerUser(state, payload) { },
    loginSuccess(state, payload) {
        state.authentication.isLogged = payload.isLogged;
        state.authentication.loggedUser = payload.loggedUser;
    },
    loginFailed(state, payload) {
        state.authentication.isLogged = payload.isLogged;
    },
    registerSuccess(state, payload) {

    },
    registerFailed(state, payload) { }
}