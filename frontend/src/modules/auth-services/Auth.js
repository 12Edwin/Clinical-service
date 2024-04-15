import api from "@/config/http-client.gateway";

const login = async (credentials) => {
  try {
    const response = await api.doPost("/auth/login", credentials);
    return response;
  } catch (error) {
    return error.response;
  }
};

const captcha = async (solution) => {
  try {
    const response = await api.doPost("/captcha/", solution, {
      headers: {
        "Content-Type": "text/plain",
      },
    });
    return response;
  } catch (error) {
    return error.response;
  }
};

export default {
  login,
  captcha,
};
