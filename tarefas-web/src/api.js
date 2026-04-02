import axios from 'axios';

const api = axios.create({
  baseURL: 'http://localhost:8080' // O link que você acabou de testar!
});

export default api;