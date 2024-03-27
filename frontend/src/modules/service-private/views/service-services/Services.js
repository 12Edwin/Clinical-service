import api from "@/config/http-client.gateway"
import utils from "@/kernel/utils"

const get_services = async () => {
    try {
        const token = utils.getToken;
        const response = await api.doGet('/api/service/',{
            headers: {
                Authorization: `Bearer ${token}` 
            }
        })
        return response
    } catch (error) {
        return error
    }
}

const save_Service = async (data) => {
    try {
        const token = localStorage.getItem('token');
        const response = await api.doPost('/api/service/', data, {
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
    get_services,
    save_Service
}