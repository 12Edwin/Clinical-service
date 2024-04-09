import router from "@/router";
import axios from "axios";

const SERVER_URL = "http://locahost:8080/api"
const AxiosClient = axios.create({
    baseURL: SERVER_URL,
    timeout: 3000
})
AxiosClient.interceptors.request.use(
    function(config){
        const auth_token = localStorage.getItem('token')
        if(auth_token !== undefined){
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
            alert('El servidor no respondió')
            localStorage.removeItem('token')

            return Promise.reject(error)
        }
        if(error.response.status){
            switch(error.response.status){
                case 400:
                    console.log('Error 400')
                    console.log(error.response.data)
                    break;
                case 401:
                    console.log('Error 401')
                    console.log(error.response.data)
                    break;
                case 403: 
                    console.log('Error 403')
                    console.log(error.response.data)
                    break;
                case 404:
                    console.log('Error 404')
                    console.log(error.response.data)
                    break;
                case 500:
                    console.log('Error 500')
                    console.log(error.response.data)
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