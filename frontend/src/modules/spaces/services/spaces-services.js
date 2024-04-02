import api from "@/config/http-client.gateway"


const getAllSpaces = async () => {
    try {
        const response = await api.doGet('/space/')
        return response 
    } catch (error) {
        throw new Error(error)
    }
}

export default{
    getAllSpaces
}