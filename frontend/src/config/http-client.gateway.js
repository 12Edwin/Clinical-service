import router from "@/router";
import axios from "axios";
import {onError} from "@/kernel/alerts";

const SERVER_URL = process.env.VUE_APP_BASE_URL
const AxiosClient = axios.create({
    baseURL: SERVER_URL,
    timeout: 3000
})

export const getServerUrl = () => {
    return  process.env.VUE_APP_BASE_URL
}
AxiosClient.interceptors.request.use(
    function(config){
        const auth_token = localStorage.getItem('token')
        if(auth_token !== undefined && auth_token !== null && auth_token !== ""){
            if(!config.url.includes('auth')){
                config.headers.Authorization = `Bearer ${auth_token}`
            }
        }
        return config
    },
    function(error){
        return Promise.reject(error)
    }
)


AxiosClient.interceptors.response.use(
    (response) => {
        if(response.status === 200 || response.status === 201){
            return Promise.resolve(response)
        }else{
            return Promise.reject(response)
        }
    },
    async (error) => {
        if(!error.response){
            await onError('Ocurrió un error', 'El servidor no respondió')
            return Promise.reject(error)
        }
        if(error.response.status){
            switch(error.response.status){
                case 401:
                    await onError('Su sesión ha expirado', 'Por favor vuelva a iniciar sesión')
                        .then(() => {
                            localStorage.removeItem('token')
                            router.push({name: 'login'})
                        })
                    return Promise.resolve()
                case 500:
                    await onError('Error interno del servidor', 'Por favor contacte a soporte técnico')
                    break;
            }
            return Promise.reject(error)
        }
        return Promise.reject(error)
    }
)

export default {
    doGet: function (endPoint, config) {
        return AxiosClient.get(endPoint, config);
    },
    doPost: function (endPoint, object, config) {
        return AxiosClient.post(endPoint, object, config || {});
    },
    doPut: function (endPoint, object, config) {
        return AxiosClient.put(endPoint, object, config || {});
    },
    doDelete: function (endPoint) {
        return AxiosClient.delete(endPoint);
    },
};