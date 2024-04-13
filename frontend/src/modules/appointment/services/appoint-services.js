import api from "@/config/http-client.gateway"
import { decrypt } from "@/config/security"

const getAppointmentsBySpace = async (spaceId) => {
    try {
        const response = await api.doGet(`/appoint/space/${spaceId}`)
        return response
    } catch (error) {
        return error.response
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
        return error.response
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
        return error.response
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
        return error.response
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
        return error.response
    }
}


const getAppointById = async (id) => {
    try {
        const response = await api.doGet(`/appoint/${id}`)
        return response
    } catch (error) {
        console.log("error",error)
        return error.response
    }

}

export default {
    getAppointmentsBySpace,
    saveAppointment,
    reschedule,
    cancelAppoint,
    completeAppoint,
    getAppointById
}