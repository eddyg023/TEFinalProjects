import { createRouter, createWebHistory } from 'vue-router';
import Home from '../views/Home.vue';
import Team from '../views/Team.vue';
import TeamDetail from '../views/TeamDetail.vue';
import Coach from '../views/Coach.vue';
import CoachDetail from '../views/CoachDetail.vue';
import Player from '../views/Player.vue';
import PlayerDetail from '../views/PlayerDetail.vue';
import Login from '../views/Login.vue';
import Register from '../views/Register.vue';
import { store } from '../main';

export function createAppRouter() {
    const router = createRouter({
        history: createWebHistory(),
        routes: [
            {
                path: '/',
                name: 'Home',
                component: Home,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/team',
                name: 'Team',
                component: Team,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/coach',
                name: 'Coach',
                component: Coach,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/player',
                name: 'Player',
                component: Player,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/login',
                name: 'Login',
                component: Login,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/register',
                name: 'Register',
                component: Register,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/coach/:id',
                name: 'CoachDetail',
                component: CoachDetail,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/team/:id',
                name: 'TeamDetail',
                component: TeamDetail,
                meta: {
                    requireAuth: false,
                }
            },
            {
                path: '/player/:id',
                name: 'PlayerDetail',
                component: PlayerDetail,
                meta: {
                    requireAuth: false,
                }
            }
        ]
    });

    router.beforeEach((to, from, next) => {
        if (to.meta.requireAuth  && store.state.token === null) {
            next('/login');
        } else {
            next();
        }
    });
    
    return router;
}