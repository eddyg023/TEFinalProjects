import { store } from '../main';
import axios from 'axios';

const http = axios.create({ baseURL: 'http://localhost:8080/api' });

export default {
    getPlayers() {
        return http.get('/player');
    },
    getPlayer(id) {
        return http.get(`/player/${id}`);
    },
    getPlayersForTeam(teamId) {
        return http.get(`/player/team/${teamId}`);
    },
    addPlayer(player) {
        return http.post('/player', player, {headers: {Authorization: `Bearer ${store.state.token}` } });
    },
    updatePlayer(id, player) {
        return http.put(`/player/${id}`, player, {headers: {Authorization: `Bearer ${store.state.token}` } });
    },
    deletePlayer(id) {
        return http.delete(`/player/${id}`, {headers: {Authorization: `Bearer ${store.state.token}` } });
    }
}