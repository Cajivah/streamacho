export default {
    createMeeting(context, payload) {
        context.commit({
            type: 'createMeeting',
            meeting: payload
        });
    },
    removeMeeting(context, payload) {
        context.commit({
            type: 'removeMeeting',
            meeting: payload
        });
    },
    initMeetings(context, payload) {
        context.commit({
            type: 'initMeetings',
            meetings: payload
        });
    },
    loginOAuth(context, payload) {
        vueAuth.login(payload.user, payload.requestOprions)
            .then((response) => {
                context.commit('isAuthenticated', {
                    isAuthenticated: vueAuth.isAuthenticated()
                });
            });
    },
    loginSuccess(context, payload) {
        context.commit({
            type: 'loginSuccess',
            authentication: {
                isLogged: payload.isLogged,
                loggedUser: payload.loggedUser
            }
        });
    },
    loginFailed(context, payload) {
        context.commit({
            type: 'loginFailed',
            authentication: {
                isLogged: payload.isLogged
            }
        });
    },
    registerSuccess(context, payload) {
        context.commit({
            type: 'registerSuccess'
        });
    },
    registerFailed(context, payload) {
        context.commit({
            type: 'registerFailed'
        });
    }
}