export const authConfig = {
    baseUrl: 'http://localhost:3000',
    providers: {
        google: {
            clientId: '',
            redirectUri: 'http://localhost:8080/auth/google_callback'
        },
        facebook: {
            clientId: '',
            redirectUri: 'http://localhost:8080/auth/facebook_callback'
        }
    }
}
