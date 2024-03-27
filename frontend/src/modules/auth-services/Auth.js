import api from "@/config/http-client.gateway"

const login = async (credentials) => {
    try {
        const response = await api.doPost('/auth/login', credentials)
        return response
    } catch (error) {
        return error
    }
}


export default {
    login
}