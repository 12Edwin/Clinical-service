import api from "@/config/http-client.gateway";
import {encrypt} from "@/config/security";

export const save = async (data) =>{
    try{
        const encrypted =  await encrypt(JSON.stringify(data))
        return await api.doPost("/treatment/", encrypted, {headers: {'Content-Type': 'text/plain'}})
    }catch (error){
        return error.response
    }
}

export const update = async (data) =>{
    try{
        const encrypted =  await encrypt(JSON.stringify(data))
        return await api.doPut("/treatment/", encrypted, {headers: {'Content-Type': 'text/plain'}})
    }catch (error){
        return error.response
    }
}

export const getExpedient = async ( id) => {
    try {
        const encrypted = await encrypt(id)
        return await api.doGet("/expedient/" + encrypted)
    } catch (error) {
        return error.response
    }
}

export const getServices = async () => {
    try {
        return await api.doGet("/service/findByUser/")
    } catch (error) {
        return error.response
    }
}

export const getTreatments = async ( id) => {
    try {
        const encrypted = await encrypt(id)
        return await api.doGet("/treatment/findByExp/" + encrypted)
    } catch (error) {
        return error.response
    }
}