import { store } from '../main';
import axios from 'axios';

const http = axios.create({ baseURL: 'http://localhost:8080/api' });

export default {
    getTeams() {
        return http.get('/team');
    },
    getTeam(id) {
        return http.get(`/team/${id}`);
    },
    addTeam(team) {
        return http.post('/team', team, {headers: {Authorization: `Bearer ${store.state.token}` } });
    },
    updateTeam(id, team) {
        return http.put(`/team/${id}`, team, {headers: {Authorization: `Bearer ${store.state.token}` } });
    },
    deleteTeam(id) {
        return http.delete(`/team/${id}`, {headers: {Authorization: `Bearer ${store.state.token}` } });
    }
}