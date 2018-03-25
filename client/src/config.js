export const authConfig = {
    baseUrl: 'http://localhost:3000',
    providers: {
        google: {
            clientId: 'streamacho-199110',
            redirectUri: 'http://localhost:8080/auth/google_callback'
        },
        facebook: {
            clientId: '214269489323711',
            redirectUri: 'http://localhost:8080/auth/facebook_callback'
        }
    }
}
