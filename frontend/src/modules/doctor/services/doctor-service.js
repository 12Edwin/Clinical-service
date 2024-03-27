import api from "@/config/http-client.gateway"
import utils from "@/kernel/utils";

const get_doctors = async () => {
    try {
        const response = await api.doGet('/doctor/', {
        });
        return response;
    } catch (error) {
        return error;
    }
}

const save_doctor = async (data) => {
    try {
        const response = await api.doPost('/doctor/', data, {        
        });
        return response;
    } catch (error) {
        return error;
    }
}


export default {
    get_doctors,
    save_doctor
}