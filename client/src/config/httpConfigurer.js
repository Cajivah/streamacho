import axios from 'axios';

export default axios.create({
  baseURL: 'http://localhost:8888/api',
  withCredentials: true,
  timeout: 3000
});
