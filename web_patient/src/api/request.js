import axios from "axios";

const instance = axios.create({
  baseURL: "http://localhost:9999",
  timeout: 5000,
  responseType: "json",
});

// 请求拦截器
instance.interceptors.request.use(
  (config) => {
    // 在发送请求之前做什么
    // 1.添加通用请求头
    config.headers["Authorization"] = `Bearer ${localStorage.getItem("token")}`;
    // 2.格式化请求数据
    if (config.data) {
      config.data = JSON.stringify(config.data);
    }
    return config;
  },
  (error) => {
    // 请求错误作什么
    return Promise.reject(error);
  }
);

// 响应拦截器
instance.interceptors.response.use(
  (response) => {
    if (response.status === 200) {
      return response.data;
    }
  },
  (error) => {
    // 错误统一处理
    // if (error.response) {
    //   switch (error.response.status) {
    //     case 401:
    //       console.error("未授权，请重新登录");
    //       // 重定向到登录页
    //       break;
    //     case 403:
    //       console.error("禁止访问");
    //       break;
    //     case 500:
    //       console.error("服务器错误");
    //       break;
    //     default:
    //       console.error("其他错误", error.response.status);
    //   }
    // }
    // if (error.request) {
    //   // 网络错误或超时
    //   console.error("网络错误或超时", error.message);
    // }
    this.$message.error(error.response?.data?.message || '网络错误，请稍后重试')
    return Promise.reject(error);
  }
);

export default instance;
