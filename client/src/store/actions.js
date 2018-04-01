export default {
    createRoom(context, payload) {
        Vue.http.post('/api/meetings/rooms', payload)
            .then(response => {
                context.commit({
                    type: 'createMeeting',
                    meeting: payload
                });
            }).catch(error => error);
    },
    removeMeeting(context, payload) {
        Vue.http.delete('/api/meetings/rooms').then(response => {
            context.commit({
                type: 'removeMeeting',
                meeting: payload
            });
        }).catch(error => error);
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
    login(context, payload) {
        this.$http.post('/users/login', payload, {
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
            .then(response => {
                context.commit({
                    type: 'login',
                    authentication: {
                        isLogged: true,
                        loggedUser: response
                    }
                });
            })
            .catch(error => error);
    },
    register(context, payload) {
        this.$http.post('/users/register', payload, {
            headers: {
                'Content-type': 'application/x-www-form-urlencoded'
            }
        })
            .then(response => {
                context.commit({
                    type: 'register'
                });
            })
            .catch(error => error);
    }
}