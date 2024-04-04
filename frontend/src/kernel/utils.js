import jwtDecode from "jwt-decode"

const getRoleNameBytoken = () => {
    const { role : {name}} = jwtDecode(localStorage.getItem("token"))
    return name
}

const getToken = () => {
    return localStorage.getItem("token")
}

const removeToken = () => {
    localStorage.removeItem("token")
}
async function encodeBase64(file) {
    return new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
            resolve(reader.result);
        };
        reader.onerror = (error) => reject("");
    });
}
export default { 
    getRoleNameBytoken,
    getToken,
    removeToken,
    encodeBase64
}