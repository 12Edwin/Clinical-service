import api from "@/config/http-client.gateway";
import {encrypt} from "@/config/security";


export const getPathologies = async () => {
    try {
        return await api.doGet("/pathology/")
    } catch (error) {
        return error.response
    }
}

export const saveExpedient = async (data) => {
    try {
        const str_data = await encrypt(JSON.stringify(data))
        return await api.doPost("/expedient/", str_data, {headers: {"Content-Type": "plain/text"}})
    } catch (error) {
        return error.response
    }
}