import api from "@/config/http-client.gateway"

const services = async () => {
    try {
        const response = await api.doPost('/auth/service')
        return response
    } catch (error) {
        return error
    }
}


export default {
    services
}