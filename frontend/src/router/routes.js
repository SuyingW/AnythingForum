
const routes = [
  {
    path: '/',
    component: () => import('layouts/MainLayout.vue'),
    children: [
      { name: 'index', path: '', component: () => import('pages/IndexPage.vue') },
      { name: 'register', path: 'register', component: () => import('pages/RegistrationPage.vue') },
      { name: 'post', path: 'post/:id', component: () => import('pages/PostPage.vue') },
      {
        path: 'admin',
        children: [
          { name: 'users', path: 'users', component: () => import('pages/admin/UsersPage.vue') },
          { name: 'posts', path: 'posts', component: () => import('pages/admin/PostsPage.vue') },
          { name: 'user', path: 'user/:id', component: () => import('pages/admin/AliasPage.vue')}
        ],
      }
    ]
  },

  // Always leave this as last one,
  // but you can also remove it
  {
    path: '/:catchAll(.*)*',
    component: () => import('pages/ErrorNotFound.vue')
  }
]

export default routes
