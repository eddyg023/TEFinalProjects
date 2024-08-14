import { store } from '../main';
import axios from 'axios';

const http = axios.create({ 
    baseURL: 'http://localhost:8080/api' 
});

export default {
    getCoaches() {
        return http.get('/coach');
    },
    getCoach(id) {
        return http.get(`/coach/${id}`);
    },
    
    addCoach(coach) {
        return http.post('/coach', coach, {headers: {Authorization: `Bearer ${store.state.token}` }});
    },
    updateCoach(id, coach) {
        return http.put(`/coach/${id}`, coach, {headers: {Authorization: `Bearer ${store.state.token}` }});
    },
    deleteCoach(id) {
        return http.delete(`/coach/${id}`, {headers: {Authorization: `Bearer ${store.state.token}` } });
    }
}