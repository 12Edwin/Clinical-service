import api from "@/config/http-client.gateway"

const saveSpeciality = async (encodedPayload) => {
    try {
        const response = await api.doPost("/speciality/", encodedPayload)
        return response
    } catch (error) {
        return error
    }
}

const getSpecialities = async () => {
    try {
        const response = await api.doGet("/speciality/")
        return response
    } catch (error) {
        return error
    }
}

export default {
    saveSpeciality,
    getSpecialities
}