export const authConfig = {
    baseUrl: 'http://localhost:3000',
    providers: {
        google: {
            clientId: '77624847563-iufcd6dluq2scf9nv2bb80tidp7fnvld.apps.googleusercontent.com',
            redirectUri: 'http://localhost:8080'
        },
        facebook: {
            clientId: '214269489323711',
            redirectUri: 'http://localhost:8080'
        }
    }
}
