import api from "@/config/http-client.gateway";

const getAllSpaces = async (pagination) => {
  try {
    const response = await api.doGet("/space/", {params: pagination});
    return response;
  } catch (error) {
    return error.response
  }
};

const save_space = async (encodedPayload) => {
  try {
    const response = await api.doPost("/space/", encodedPayload, {
      headers: {
        "Content-Type": "text/plain",
      },
    });
    return response;
  } catch (error) {
    return error.response;
  }
};

const update_space = async (updatedSpace) => {
  try {
    const response = await api.doPut("/space/", updatedSpace, {
      headers: {
        "Content-Type": "text/plain",
      },
    });
    return response;
  } catch (error) {
    return error.response;
  }
};

const delete_space = async (spaceId) => {
  try {
    const response = await api.doDelete(`/space/${spaceId}`);
    return response;
  } catch (error) {
    return error.response;
  }
};
export default {
  getAllSpaces,
  save_space,
  update_space,
  delete_space,
};
