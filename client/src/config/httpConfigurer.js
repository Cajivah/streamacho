import axios from 'axios';

const apiURL = 'http://localhost:8888/api';

export default axios.create({
  baseURL: apiURL,
  withCredentials: true,
  timeout: 3000
});

export const isExcluded = (url) => {
  const excludedRoutes = ['/users/login', '/users/accounts/me'];
  const route = extractRoute(url);
  return excludedRoutes.indexOf(route) > -1;
};

const extractRoute = (url) => {
  return url.replace(apiURL, '');
};