import jwtDecode from "jwt-decode"

const getRoleNameBytoken = () => {
    const { role : {name}} = jwtDecode(localStorage.getItem("token"))
    return name
}

const getToken = () => {
    return localStorage.getItem("token")
}


export default { 
    getRoleNameBytoken,
    getToken
}