import api from "@/config/http-client.gateway"

const saveSpeciality = async (encodedPayload) => {
    try {
        const response = await api.doPost("/speciality/", encodedPayload, {
            headers:{
                'Content-Type': 'text/plain'
            }
        })
        return response
    } catch (error) {
        return error
    }
}

const getSpecialities = async (pagination) => {
    try {
        const response = await api.doGet("/speciality/", pagination)
        return response
    } catch (error) {
        return error
    }
}

export default {
    saveSpeciality,
    getSpecialities
}