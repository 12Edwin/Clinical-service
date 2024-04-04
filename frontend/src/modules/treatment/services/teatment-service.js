import api from "@/config/http-client.gateway";
import {encrypt} from "@/config/security";


export const getExpedient = async ( id) => {
    try {
        const encrypted = await encrypt(id)
        return await api.doGet("/expedient/" + encrypted)
    } catch (error) {
        return error
    }
}

export const getTreatments = async ( id) => {
    try {
        const encrypted = await encrypt(id)
        return await api.doGet("/treatment/findByExp/" + encrypted)
    } catch (error) {
        return error
    }
}