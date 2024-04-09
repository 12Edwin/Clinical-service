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

export const updateExpedient = async (data) => {
    try {
        const str_data = await encrypt(JSON.stringify(data))
        return await api.doPut("/expedient/", str_data, {headers: {"Content-Type": "plain/text"}})
    } catch (error) {
        return error.response
    }

}

export const getExpedients = async (pageable) => {
    try {
        return await api.doGet("/expedient/", {params: pageable})
    } catch (error) {
        return error.response
    }
}

export const getExpedientsByFolio = async (pageable, folio) => {
    try {
        const encrypted = await encrypt( JSON.stringify({folio}) )
        return await api.doPost(`/expedient/findByFolio/`, encrypted,{params: pageable, headers: {"Content-Type": "plain/text"}})
    } catch (error) {
        return error.response
    }
}

export const getExpedientsByEmail = async (pageable, email) => {
    try {
        const encrypted = await encrypt( JSON.stringify({email}) )
        return await api.doPost(`/expedient/findByEmail/`, encrypted ,{params: pageable, headers: {"Content-Type": "plain/text"}})
    } catch (error) {
        return error.response
    }
}

export const getExpedientById = async (id) => {
    try{
        const encrypted = await encrypt( JSON.stringify({id}) )
        return await api.doGet(`/expedient/`+ encrypted, {headers: {"Content-Type": "plain/text"}})
    }catch (error){
        return error.response
    }
}