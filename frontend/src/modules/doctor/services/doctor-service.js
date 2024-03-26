import api from "@/config/http-client.gateway"
import utils from "@/kernel/utils";

const get_doctors = async () => {
    try {
        const token = utils.getToken;
        const response = await api.doGet('/api/doctor/', {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
        return response;
    } catch (error) {
        return error;
    }
}

const save_doctor = async (data) => {
    try {
        const token = utils.getToken;
        const response = await api.doPost('/api/doctor/', data, {
            headers: {
                Authorization: `Bearer ${token}` 
            }
        });
        return response;
    } catch (error) {
        return error;
    }
}


export default {
    get_doctors
}