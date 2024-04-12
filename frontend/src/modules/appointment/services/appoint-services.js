import api from "@/config/http-client.gateway"

const getAppointmentsBySpace = async (spaceId) => {
    try {
        const response = await api.doGet(`/appoint/space/${spaceId}`)
        return response
    } catch (error) {
        throw new Error(error)
    }
}


const saveAppointment = async (appointment) => {
    try {
        const response = await api.doPost(`/appoint/`, appointment, {
                headers: {
                    "Content-Type": "text/plain",
                },
            }
        )
        return response
    } catch (error) {
        throw new Error(error)
    }
}


const reschedule = async (appointment) => {
    try {
        const response = await api.doPost("/appoint/reschedule/", appointment, {
                headers: {
                    "Content-Type": "text/plain",
                },
        })
        return response
    } catch (error) {
        throw new Error(error)
    }
}

const cancelAppoint = async (appointment) => {
    try {
        const response = await api.doPost("/appoint/cancel/", appointment, {
                headers: {
                    "Content-Type": "text/plain",
                },
        })
        return response
    } catch (error) {
        throw new Error(error)
    }
}

const completeAppoint = async (appointment) => {
    try {
        const response = await api.doPost("/appoint/complete/", appointment, {
                headers: {
                    "Content-Type": "text/plain",
                },
        })
        return response
    } catch (error) {
        throw new Error(error)
    }
}

export default {
    getAppointmentsBySpace,
    saveAppointment,
    reschedule,
    cancelAppoint,
    completeAppoint
}