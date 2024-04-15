import api from "@/config/http-client.gateway"


const getValorations = async () => {
    try {
        const response = await api.doGet("/ratting/")
        return response
    } catch (error) {
        return error.response
    }
}

const saveValoration = async (valoration) => {
    try {
        const response = await api.doPost("/ratting/", valoration, {
            headers: {
                'Content-Type': 'text/plain'
            }
        })
        return response
    } catch (error) {
        return error.response
    }
}

export default{
    getValorations,
    saveValoration
}