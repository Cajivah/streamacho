import axios from 'axios';

export const apiURL = 'http://localhost:8888/api';

export default axios.create({
  baseURL: apiURL,
  withCredentials: true,
  timeout: 3000
});

export const isExcluded = (url) => {
  const excludedRoutesNames = ['landingPage', 'login'];
  const route = extractRoute(url);
  return excludedRoutesNames.indexOf(route) > -1;
};

const extractRoute = (url) => {
  return url.replace(apiURL, '');
};