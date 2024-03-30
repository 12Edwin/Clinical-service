import api from "@/config/http-client.gateway"

const get_doctors = async (pagination) => {
    try {
        const response = await api.doGet('/doctor/', {params: pagination});
        return response;
    } catch (error) {
        return error;
    }
}

const save_doctor = async (data) => {
    try {
        const response = await api.doPost('/doctor/', data, {
            headers:{
                'Content-Type': 'text/plain'
            }
        })
        return response;
    } catch (error) {
        return error;
    }
}


const deleteDoctor = async (doctorId) => {
    try {
        const response = await api.doDelete(`/doctor/${doctorId}`)
        return response
    } catch (error) {
        return error
    }
}

const updateDoctor = async (updatedDoctor) => {
    try {
        const response = await api.doPut("/doctor/", updatedDoctor, {
            headers:{
                'Content-Type': 'text/plain'
            }
        })
        return response
    } catch (error) {
        return error
    }
}


export default {
    get_doctors,
    save_doctor,
    updateDoctor,
    deleteDoctor,
}