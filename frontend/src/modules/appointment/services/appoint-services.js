import api from "@/config/http-client.gateway"

const getAppointmentsBySpace = async (spaceId) => {
    try {
        const response = await api.doGet(`/appoint/space/${spaceId}`)
        return response
    } catch (error) {
        throw new Error(error)
    }
}

export default {
    getAppointmentsBySpace
}