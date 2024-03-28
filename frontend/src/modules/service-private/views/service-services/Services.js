import api from "@/config/http-client.gateway";

const get_services = async () => {
  try {
    const response = await api.doGet("/service/");
    return response;
  } catch (error) {
    return error;
  }
};

const save_Service = async (encodedPayload) => {
  try {
    const response = await api.doPost("/service/", encodedPayload);
    return response;
  } catch (error) {
    return error;
  }
};

const get_specialities = async () => {
  try {
    const response = await api.doGet("/speciality/");
    return response;
  } catch (error) {
    return error;
  }
};

export default {
  get_services,
  save_Service,
  get_specialities,
};
