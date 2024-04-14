import api from "@/config/http-client.gateway"

const send_number = async (data) => {
    try {
        const response = await api.doPost('/user/recovery/', data, {
            headers:{
                'Content-Type': 'text/plain'
            }
        })
        return response;
    } catch (error) {
        return error;
    }
}

const new_pass = async (data) => {
    try {
        const response = await api.doPost('/user/verify/', data, {
            headers:{
                'Content-Type': 'text/plain'
            }
        })
        return response;
    } catch (error) {
        return error;
    }
}

const exist_user = async () => {
    return !!localStorage.getItem('token')
}

export default {
    send_number,
    new_pass,
    exist_user
}