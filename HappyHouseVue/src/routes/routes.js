import MainLayout from "@/views/Layout/MainLayout.vue";

import NotFound from "@/views/NotFoundPage.vue";

import store from "../store/store";

const requireAuth = () => (to, from, next) => {
  // /me, /,
  const nextRoute = to.path;

  if (store.getters.getAccessToken) {
    return next();
  } else next("/login" + nextRoute);
};

const routes = [
  {
    path: "/",
    redirect: "main",
    component: MainLayout,
    children: [
      {
        path: "/",
        name: "main",
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Main.vue")
      },
      {
        path: "/main",
        name: "실거래가",
        // route level code-splitting
        // this generates a separate chunk (about.[hash].js) for this route
        // which is lazy-loaded when the route is visited.
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Main.vue")
      },
      {
        path: "/profile",
        name: "내 정보보기",
        component: () =>
          import(
            /* webpackChunkName: "demo" */ "../views/Pages/UserProfile.vue"
          )
      },
      {
        path: "/tables",
        name: "정보게시판",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/RegularTables.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/login",
        name: "로그인",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Login.vue")
      },
      {
        path: "/login/:nextRoute",
        name: "로그인",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Login.vue")
      },
      {
        path: "/register",
        name: "회원가입",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Register.vue")
      },
      {
        path: "/editprofile",
        name: "회원정보수정",
        component: () =>
          import(
            /* webpackChunkName: "demo" */ "../views/Pages/EditProfile.vue"
          ),
        beforeEnter: requireAuth()
      },
      {
        path: "/leave",
        name: "회원탈퇴",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Pages/Leave.vue"),
        beforeEnter: requireAuth()
      },
      {
        path: "/interest",
        name: "관심지역",
        component: () =>
          import(/* webpackChunkName: "demo" */ "../views/Interest.vue"),
        beforeEnter: requireAuth()
      },
      { path: "*", component: NotFound }
    ]
  }
  // {
  //   path: '/',
  //   redirect: 'login',
  //   component: AuthLayout,
  //   children: [
  //     {
  //       path: '/login',
  //       name: 'login',
  //       component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Login.vue')
  //     },
  //     {
  //       path: '/register',
  //       name: 'register',
  //       component: () => import(/* webpackChunkName: "demo" */ '../views/Pages/Register.vue')
  //     },
  //     { path: '*', component: NotFound }
  //   ]
  // }
];

export default routes;
