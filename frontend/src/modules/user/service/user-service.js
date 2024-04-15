import {encrypt} from "@/config/security";
import api from "@/config/http-client.gateway";


export const getProfile = async ( id) => {
    try {
        const encrypted = await encrypt(id)
        return await api.doGet("/user/profile/" + encrypted)
    } catch (error) {
        return error.response
    }
}

export const getImage = async ( url) => {
    try {
        return await api.doGet(url)
    } catch (error) {
        return error.response
    }
}

export const updateProfile = async (data) => {
    try {
        const encrypted = await encrypt( JSON.stringify( data ) )
        return await api.doPut('/user/profile/', encrypted, {headers: {'Content-Type': 'text/plain'}})
    } catch (error) {
        return error.response
    }
}