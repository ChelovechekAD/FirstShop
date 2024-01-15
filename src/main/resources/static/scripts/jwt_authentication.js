let inMemoryToken;
function auth_complete(token, email, login){
    inMemoryToken={
        token: token,
        email: email,
        login: login
    }
    permission_access('/catalog', inMemoryToken)
}

function permission_access(url, token_body){
    let response = fetch(url, {
        method: "GET",
        headers: {
            Authorization: 'Bearer ' + token_body.token
        }
    })
}