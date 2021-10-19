export const setJwt = jwt => ({
    type: 'SET_JWT',
    payload: jwt
})

export const signOut = () => ({
    type: 'SIGN_OUT'
})