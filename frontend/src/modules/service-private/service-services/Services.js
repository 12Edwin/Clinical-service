import api from "@/config/http-client.gateway";

const get_services = async (pagination) => {
  try {
    const response = await api.doGet("/service/all/", { params: pagination });
    return response;
  } catch (error) {
    return error.response;
  }
};

const getPublicServices = async (pagination) => {
  try {
    const response = await api.doGet("/service/", { params: pagination });
    return response;
  } catch (error) {
    return error.response;
  }
};

const save_Service = async (encodedPayload) => {
  try {
    const response = await api.doPost("/service/", encodedPayload, {
      headers: {
        "Content-Type": "text/plain",
      },
    });
    return response;
  } catch (error) {
    return error.response;
  }
};

const update_service = async (updatedService) => {
  try {
    const response = await api.doPut("/service/", updatedService, {
      headers: {
        "Content-Type": "text/plain",
      },
    });
    return response;
  } catch (error) {
    return error.response;
  }
};

const delete_service = async (specialityId) => {
  try {
    const response = await api.doDelete(`/service/${specialityId}`);
    return response;
  } catch (error) {
    return error.response;
  }
};

const get_specialities = async () => {
  try {
    const response = await api.doGet("/speciality/");
    return response;
  } catch (error) {
    return error.response;
  }
};

export default {
  get_services,
  save_Service,
  update_service,
  delete_service,
  get_specialities,
  getPublicServices
};
