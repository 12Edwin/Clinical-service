import api from "@/config/http-client.gateway";

const get_pathology = async (pagination) => {
  try {
    const response = await api.doGet("/pathology/", {params: pagination});
    return response;
  } catch (error) {
    return error.response;
  }
};

const save_pathology = async (encodedPayload) => {
  try {
    const response = await api.doPost("/pathology/", encodedPayload, {
      headers: {
        "Content-Type": "text/plain",
      },
    });
    return response;
  } catch (error) {
    return error.response;
  }
};

const update_pathology = async (updatedPathology) => {
  try {
    const response = await api.doPut("/pathology/", updatedPathology, {
      headers: {
        "Content-Type": "text/plain",
      },
    });
    return response;
  } catch (error) {
    return error.response;
  }
};

const delete_Pathology = async (pathologyId) => {
  try {
    const response = await api.doDelete(`/pathology/${pathologyId}`);
    return response;
  } catch (error) {
    return error.response;
  }
};

export default {
  get_pathology,
  save_pathology,
  update_pathology,
  delete_Pathology,
};
