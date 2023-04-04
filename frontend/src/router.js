import { createWebHistory, createRouter } from "vue-router";
import HomeItem from "./components/HomeItem.vue";
import LoginItem from "./components/LoginItem.vue";
import RegisterItem from "./components/RegisterItem.vue";
// lazy-loaded
const ProfileItem = () => import("./components/ProfileItem.vue")
const BoardAdmin = () => import("./components/BoardAdmin.vue")
const BoardModerator = () => import("./components/BoardModerator.vue")
const BoardUser = () => import("./components/BoardUser.vue")

const routes = [
    {
        path: "/",
        name: "HomeItem",
        component: HomeItem,
    },
    {
        path: "/home",
        component: HomeItem,
    },
    {
        path: "/login",
        component: LoginItem,
    },
    {
        path: "/register",
        component: RegisterItem,
    },
    {
        path: "/profile",
        name: "ProfileItem",
        // lazy-loaded
        component: ProfileItem,
    },
    {
        path: "/admin",
        name: "AdminItem",
        // lazy-loaded
        component: BoardAdmin,
    },
    {
        path: "/mod",
        name: "ModeratorItem",
        // lazy-loaded
        component: BoardModerator,
    },
    {
        path: "/user",
        name: "UserItem",
        // lazy-loaded
        component: BoardUser,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const publicPages = ['/login', '/register', '/home'];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');

    // trying to access a restricted page + not logged in
    // redirect to login page
    if (authRequired && !loggedIn) {
        next('/login');
    } else {
        next();
    }
});

export default router;